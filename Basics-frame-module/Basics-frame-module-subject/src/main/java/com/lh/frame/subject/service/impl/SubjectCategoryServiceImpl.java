package com.lh.frame.subject.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lh.frame.common.constant.basic.SystemConstants;
import com.lh.frame.common.exception.SystemException;
import com.lh.frame.subject.constant.SubjectConstant;
import com.lh.frame.subject.convert.SubjectCategoryEntityConverter;
import com.lh.frame.subject.dao.SubjectCategoryDao;
import com.lh.frame.subject.dao.SubjectLabelDao;
import com.lh.frame.subject.domain.vo.request.SubjectCategoryReq;
import com.lh.frame.subject.entity.SubjectCategory;
import com.lh.frame.subject.entity.SubjectLabel;
import com.lh.frame.subject.domain.vo.response.SubjectCategoryResp;
import com.lh.frame.subject.domain.vo.response.SubjectLabelResp;
import com.lh.frame.subject.service.SubjectCategoryService;
import com.lh.frame.subject.service.SubjectLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 题目分类(FrameSubjectCategory)表服务实现类
 *
 * @author makejava
 * @since 2024-04-09 16:53:19
 */
@Slf4j
@Service
public class SubjectCategoryServiceImpl implements SubjectCategoryService {

    @Resource
    private SubjectCategoryDao subjectCategoryDao;

    @Resource
    private SubjectLabelDao subjectLabelDao;
    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private ThreadPoolExecutor labelThreadPool;

    @Override
    public SubjectCategoryReq queryById(Long id) {
        try {
            SubjectCategory subjectCategory = subjectCategoryDao.selectById(id);
            return SubjectCategoryEntityConverter.INSTANCE.convertEntityToReq(subjectCategory);
        } catch (Exception e) {
            throw new RuntimeException("查询题目分类失败");
        }
    }

    /**
     * 添加一个题目分类。
     *
     * @param subjectCategoryReq 包含要添加的题目分类信息的请求对象。
     *                           该对象由外部传入，用来构建题目分类实体。
     * @throws RuntimeException 如果添加题目分类过程中发生异常，则抛出运行时异常。
     */
    @Override
    public void addCategory(SubjectCategoryReq subjectCategoryReq) {
        // 将请求对象转换为数据库实体
        SubjectCategory subjectCategory = SubjectCategoryEntityConverter.INSTANCE.convertReqToEntity(subjectCategoryReq);
        try {
            // 尝试将题目分类实体插入数据库
            subjectCategoryDao.insert(subjectCategory);
        } catch (Exception e) {
            // 如果插入过程中有异常发生，抛出运行时异常
            throw new RuntimeException("添加题目分类失败");
        }

    }

    /**
     * 删除题目分类
     *
     * @param categoryId 分类ID，用于指定要删除的题目分类
     * @throws RuntimeException 如果删除过程中发生异常，则抛出运行时异常
     */
    @Override
    public void deleteCategory(Long categoryId) {
        log.info("SubjectCategoryServiceImpl.deleteCategory.categoryId:{}", categoryId);
        if (this.checkCategory(categoryId) || this.checkLabel(categoryId)) {
            throw new SystemException("该分类下有子类目，不能删除");
        }
        try {
            // 通过categoryId更新subjectCategory表中的记录，设置为已删除状态
            subjectCategoryDao.updateById(new SubjectCategory().
                    setCategoryId(categoryId).setIsDeleted(SystemConstants.IS_DELETE));
        } catch (Exception e) {
            // 若删除过程中出现异常，则抛出运行时异常，并附带错误信息
            throw new RuntimeException("删除题目分类失败");
        }
    }

    /**
     * 校验分类下是否是子类
     */
    public boolean checkCategory(Long categoryId) {
        LambdaUpdateWrapper<SubjectCategory> eq = new LambdaUpdateWrapper<SubjectCategory>().eq(SubjectCategory::getParentId, categoryId);
        return subjectCategoryDao.selectCount(eq) > 0;
    }

    /**
     * 校验分类下是否有子标签
     */
    public boolean checkLabel(Long categoryId) {
        return subjectLabelDao.selectCount(
                new LambdaQueryWrapper<SubjectLabel>().eq(SubjectLabel::getCategoryId, categoryId)) > 0;
    }


    /**
     * 查询题目分类的父类目。该方法不接受任何参数，返回一个分类父类目的列表。
     *
     * @return List<SubjectCategoryReq> 分类父类目的请求对象列表。
     */
    @Override
    public List<SubjectCategoryResp> queryParent() {
        try {
            // 从数据库中查询符合条件的题目分类信息
            List<SubjectCategory> subjectCategories = subjectCategoryDao.selectList(
                    new LambdaQueryWrapper<SubjectCategory>()
                            .eq(SubjectCategory::getCategoryType, SubjectConstant.SUBJECT_POSITION_ID)
                            .eq(SubjectCategory::getIsDeleted, SystemConstants.NO_DELETE));
            // 将查询到的实体类列表转换为请求对象列表
            return SubjectCategoryEntityConverter.INSTANCE.convertEntityListToRespList(subjectCategories);
        } catch (Exception e) {
            // 若查询过程中出现异常，则抛出运行时异常
            throw new RuntimeException("查询题目分类大类失败");
        }
    }


    /**
     * 查询指定分类下的子分类列表。
     * 使用缓存机制，缓存键为categoryId的字符串形式加上固定前缀。
     *
     * @param categoryId 分类的ID，如果为空则抛出IllegalArgumentException异常。
     * @return 返回查询到的子分类响应列表，如果查询结果为空，则返回空列表。
     */
    @Override
    @Cacheable(value = SubjectConstant.CATEGORY, key = "#categoryId.toString()+'-'")
    public List<SubjectCategoryResp> queryCategoryList(Long categoryId) {
        // 如果日志级别允许，输出调试信息
        if (log.isInfoEnabled()) {
            log.debug("SubjectCategoryServiceImpl.CategoryList. id = {}", categoryId);
        }
        try {
            // 检查categoryId是否为空
            if (StringUtils.isEmpty(categoryId)) {
                throw new IllegalArgumentException("parentId cannot be null");
            }
            // 查询子分类信息
            List<SubjectCategory> subjectCategories = subjectCategoryDao.selectList(
                    new LambdaQueryWrapper<SubjectCategory>()
                            .eq(SubjectCategory::getParentId, categoryId)
                            .eq(SubjectCategory::getIsDeleted, SystemConstants.NO_DELETE));
            // 如果查询结果为空，直接返回空列表
            if (subjectCategories.isEmpty()) {
                return Collections.emptyList();
            }
            // 将查询到的分类实体转换为响应对象
            List<SubjectCategoryResp> subjectCategoryRespList =
                    SubjectCategoryEntityConverter.INSTANCE.convertEntityListToRespList(subjectCategories);
            // 初始化用于存放标签响应对象的映射
            Map<Long, List<SubjectLabelResp>> map = new HashMap<>();
            // 创建异步任务列表，用于查询每个分类对应的标签列表
            List<CompletableFuture<Map<Long, List<SubjectLabelResp>>>> completableFutureList =
                    subjectCategoryRespList.stream().map(category -> CompletableFuture.supplyAsync(
                            () -> this.getSubjectLabelList(category), labelThreadPool)).collect(Collectors.toList());
            // 等待所有异步任务完成，并将结果合并到映射中
            completableFutureList.forEach(future -> {
                try {
                    Map<Long, List<SubjectLabelResp>> resultMap = future.get();
                    map.putAll(resultMap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // 将标签列表设置到每个分类响应对象中
            subjectCategoryRespList.forEach(subjectCategoryResp -> {
                subjectCategoryResp.setSubjectLabelRespList(map.get(subjectCategoryResp.getId()));
            });
            return subjectCategoryRespList;
        } catch (Exception e) {
            // 记录查询失败的日志
            log.error("查询题目分类小类失败, parentId: {}", categoryId, e);
            throw new RuntimeException("查询题目分类小类失败", e);
        }
    }

    /**
     * 获取指定学科类别的标签列表。
     *
     * @param subjectCategoryResp 学科类别响应对象，包含学科类别的ID。
     * @return 返回一个映射，其中键是学科类别的ID，值是该类别下的标签列表。
     */
    private Map<Long, List<SubjectLabelResp>> getSubjectLabelList(SubjectCategoryResp subjectCategoryResp) {
        // 如果日志级别允许，记录信息日志
        if (log.isInfoEnabled()) {
            log.info("SubjectCategoryServiceImpl.getSubjectLabelList.subjectCategoryResp{}", subjectCategoryResp);
        }
        // 创建一个映射，用于存储标签列表
        Map<Long, List<SubjectLabelResp>> labelMap = new HashMap<>();
        // 根据传入的学科类别ID，获取该类别的标签列表
        List<SubjectLabelResp> labelList = subjectLabelService.getLabelList(subjectCategoryResp.getCategoryId());
        // 将获取到的标签列表放入映射中
        labelMap.put(subjectCategoryResp.getCategoryId(), labelList);
        return labelMap;
    }

    /**
     * 更新题目分类信息。
     *
     * @param subjectCategoryReq 包含需要更新的题目分类信息请求对象。
     *                           该对象包含了要更新的分类的详细信息。
     * @throws RuntimeException 如果更新过程中发生异常，则抛出运行时异常。
     */
    @Override
    public void updateCategory(SubjectCategoryReq subjectCategoryReq) {
        log.debug("SubjectCategoryServiceImpl.updateCategory. subjectCategoryReq = {}", subjectCategoryReq);
        try {
            // 将请求对象转换为实体对象
            SubjectCategory subjectCategory = SubjectCategoryEntityConverter.INSTANCE.convertReqToEntity(subjectCategoryReq);
            // 根据实体对象更新数据库中的分类信息
            subjectCategoryDao.updateById(subjectCategory);
        } catch (Exception e) {
            // 记录更新失败的错误日志
            log.error("更新题目分类失败, subjectCategoryReq: {}", subjectCategoryReq, e);
            // 抛出运行时异常，以通知调用者更新失败
            throw new RuntimeException("更新题目分类失败", e);
        }
    }

    /**
     * 查询主题分类树结构。
     * 该方法首先从数据库中查询所有未被删除的主题分类，然后将这些分类转换为对应的响应对象列表。
     * 接着，通过遍历响应对象列表，为每个根节点收集其子节点信息，最终返回一个树状结构的响应对象列表。
     *
     * @return List<SubjectCategoryResp> 返回主题分类的树状结构响应对象列表。
     */
    @Override
    public List<SubjectCategoryResp> queryTree() {
        // 从数据库中查询未被删除的主题分类
        List<SubjectCategory> subjectCategories = subjectCategoryDao.selectList(new LambdaQueryWrapper<SubjectCategory>()
                .eq(SubjectCategory::getIsDeleted, SystemConstants.NO_DELETE)
        );

        // 将主题分类实体列表转换为响应对象列表
        List<SubjectCategoryResp> subjectCategoryRespList =
                SubjectCategoryEntityConverter.INSTANCE.convertEntityListToRespList(subjectCategories);
        // 为每个根节点收集并设置子节点信息
        List<SubjectCategoryResp> subjectCategoryRespTree = subjectCategoryRespList.stream()
                .filter(subjectCategoryResp -> subjectCategoryResp.getParentId() == 0).collect(Collectors.toList());
        subjectCategoryRespTree.forEach
                (subjectCategoryResp -> subjectCategoryResp.setChildList(this.getChildList(subjectCategoryResp, subjectCategoryRespList)));
        return subjectCategoryRespTree;
    }

    /**
     * 获取指定父节点的子节点列表。
     * 该方法通过遍历所有响应对象，筛选出父节点ID匹配指定ID的子节点，并收集到一个列表中返回。
     *
     * @param subjectCategoryResp     父节点的响应对象
     * @param subjectCategoryRespList 所有响应对象的列表
     * @return List<SubjectCategoryResp> 返回指定父节点的子节点列表。
     */
    private List<SubjectCategoryResp> getChildList(SubjectCategoryResp subjectCategoryResp, List<SubjectCategoryResp> subjectCategoryRespList) {
        if (subjectCategoryRespList.isEmpty()) {
            return new ArrayList<>();
        }
        // 筛选出父节点ID匹配指定ID的子节点并收集到一个列表中
        return subjectCategoryRespList.stream()
                .filter(categoryResp -> categoryResp.getParentId().equals(subjectCategoryResp.getCategoryId()))
                .collect(Collectors.toList());
    }


}

