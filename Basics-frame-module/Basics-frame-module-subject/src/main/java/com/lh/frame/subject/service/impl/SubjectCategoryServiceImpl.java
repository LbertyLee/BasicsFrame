package com.lh.frame.subject.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lh.frame.common.constant.basic.SystemConstants;
import com.lh.frame.common.exception.SystemException;
import com.lh.frame.subject.constant.SubjectConstant;
import com.lh.frame.subject.convert.SubjectCategoryEntityConverter;
import com.lh.frame.subject.convert.SubjectLabelEntityConverter;
import com.lh.frame.subject.dao.SubjectCategoryDao;
import com.lh.frame.subject.dao.SubjectLabelDao;
import com.lh.frame.subject.domain.vo.request.SubjectCategoryReq;
import com.lh.frame.subject.domain.vo.request.SubjectLabelReq;
import com.lh.frame.subject.domain.vo.entity.SubjectCategory;
import com.lh.frame.subject.domain.vo.entity.SubjectLabel;
import com.lh.frame.subject.domain.vo.response.SubjectCategoryResp;
import com.lh.frame.subject.domain.vo.response.SubjectLabelResp;
import com.lh.frame.subject.service.SubjectCategoryService;
import com.lh.frame.subject.service.SubjectLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
     *                          该对象由外部传入，用来构建题目分类实体。
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
        if (this.checkCategory(categoryId)||this.checkLabel(categoryId)) {
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
                new LambdaQueryWrapper<SubjectLabel>().eq(SubjectLabel::getCategoryId, categoryId))>0;
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
     * 查询指定父类别的子类别列表。
     *
     * @param categoryId 父类别的ID，不能为空。
     * @return 返回符合条件的子类别列表。如果查询结果为空，将返回空列表，而非抛出异常。
     * @throws IllegalArgumentException 如果传入的parentId为null，将抛出此异常。
     * @throws RuntimeException         如果查询过程中发生异常，将抛出此运行时异常。
     */
    @Override
    @Cacheable(value =SubjectConstant.CATEGORY,key = "#categoryId.toString()+'-'")
    public List<SubjectCategoryResp> queryCategoryList(Long categoryId) {
        log.debug("SubjectCategoryServiceImpl.CategoryList. id = {}", categoryId);
        try {
            if (StringUtils.isEmpty(categoryId)) {
                throw new IllegalArgumentException("parentId cannot be null");
            }
            List<SubjectCategory> subjectCategories = subjectCategoryDao.selectList(
                    new LambdaQueryWrapper<SubjectCategory>()
                            .eq(SubjectCategory::getParentId, categoryId)
                            .eq(SubjectCategory::getIsDeleted, SystemConstants.NO_DELETE));
            if (subjectCategories.isEmpty()) {
                return Collections.emptyList();
            }
            List<SubjectCategoryResp> subjectCategoryRespList =
                    SubjectCategoryEntityConverter.INSTANCE.convertEntityListToRespList(subjectCategories);
            return this.querySubjectCategoryReqs(subjectCategoryRespList);
//            return this.querySubjectCategoryReqsSerial(subjectCategoryReqs);
        } catch (Exception e) {
            log.error("查询题目分类小类失败, parentId: {}", categoryId, e);
            throw new RuntimeException("查询题目分类小类失败", e);
        }
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
     * 串行查询主题类别请求信息
     *
     * @param subjectCategoryReqs 主题类别请求信息列表，每个请求包含一个主题类别的ID。
     * @return 返回处理后的主题类别请求信息列表，每个请求额外包含了属于该类别的标签信息列表。
     */
    private List<SubjectCategoryResp> querySubjectCategoryReqsSerial(List<SubjectCategoryResp> subjectCategoryReqs) {
        // 使用Stream API处理每个主题类别请求，为其添加相应的标签信息
        return subjectCategoryReqs.stream().peek(subjectCategoryResp -> {
            List<SubjectLabelResp> labelList = subjectLabelService.getLabelList(subjectCategoryResp.getCategoryId());
            subjectCategoryResp.setSubjectLabelRespList(labelList);
        }).collect(Collectors.toList());
    }


    /**
     * 并行查询主题类别请求对应的标签列表。
     * 该方法首先根据可用处理器数量和主题类别请求的数量来设定线程池的大小，以充分利用系统资源。
     * 然后，为每个主题类别请求创建一个异步查询任务，并等待所有任务完成。
     * 最后，将查询到的标签列表设置到相应的主题类别请求对象中，并返回处理后的主题类别请求列表。
     *
     * @param subjectCategoryRespList 主题类别请求列表，每个请求包含一个主题类别的ID。
     * @return 处理后的主题类别请求列表，每个请求对象包含相应的标签列表。
     */
    private List<SubjectCategoryResp> querySubjectCategoryReqs(List<SubjectCategoryResp> subjectCategoryRespList) {
        // 设定线程池大小，基于可用处理器数量和主题类别请求数量
        int threadCount = Runtime.getRuntime().availableProcessors() * subjectCategoryRespList.size();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        try {
            // 使用并行方式处理任务
            Map<SubjectCategoryResp, CompletableFuture<List<SubjectLabelResp>>> futureMap = new HashMap<>();
            for (SubjectCategoryResp subjectCategoryReqs : subjectCategoryRespList) {
                // 为每个主题类别请求创建异步查询任务
                CompletableFuture<List<SubjectLabelResp>> future = CompletableFuture.supplyAsync(
                        () -> subjectLabelService.getLabelList(subjectCategoryReqs.getCategoryId()), executor
                );
                futureMap.put(subjectCategoryReqs, future);
            }
            // 等待所有异步任务完成
            CompletableFuture.allOf(futureMap.values().toArray(new CompletableFuture<?>[0])).join();
            // 统一获取并设置标签列表
            futureMap.forEach((req, future) -> req.setSubjectLabelRespList(future.join()));
            return subjectCategoryRespList;
        }catch (Exception e){
            // 异常处理
            throw new RuntimeException("查询主题类别标签信息失败", e);
        }finally {
            // 关闭线程池
            executor.shutdown();
        }
    }



}

