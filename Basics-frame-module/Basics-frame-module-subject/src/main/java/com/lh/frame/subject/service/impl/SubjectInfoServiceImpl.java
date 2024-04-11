package com.lh.frame.subject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.frame.common.constant.basic.SystemConstants;
import com.lh.frame.common.entity.PageResult;
import com.lh.frame.common.exception.SystemException;
import com.lh.frame.subject.convert.SubjectInfoEntityConverter;
import com.lh.frame.subject.dao.SubjectInfoDao;
import com.lh.frame.subject.domain.dto.SubjectMappingDTO;
import com.lh.frame.subject.domain.dto.SubjectOptionDTO;
import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.domain.vo.response.SubjectInfoResp;
import com.lh.frame.subject.entity.SubjectCategory;
import com.lh.frame.subject.entity.SubjectInfo;
import com.lh.frame.subject.entity.SubjectLabel;
import com.lh.frame.subject.entity.SubjectMapping;
import com.lh.frame.subject.handler.SubjectTypeHandler;
import com.lh.frame.subject.handler.SubjectTypeHandlerFactory;
import com.lh.frame.subject.service.SubjectCategoryService;
import com.lh.frame.subject.service.SubjectInfoService;
import com.lh.frame.subject.service.SubjectLabelService;
import com.lh.frame.subject.service.SubjectMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 题目信息表(FrameSubjectInfo)表服务实现类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:07:59
 */
@Service
public class SubjectInfoServiceImpl implements SubjectInfoService {

    @Resource
    private SubjectInfoDao subjectInfoDao;

    @Resource
    private SubjectCategoryService subjectCategoryService;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;


    /**
     * 根据主题ID查询主题信息。
     *
     * @param subjectsId 主题的ID，用于查询特定的主题信息。
     * @return SubjectInfoResp 主题信息响应对象，包含了从数据库查询到的主题信息以及通过主题处理程序获取的题目选项和答案。
     */
    @Override
    public SubjectInfoResp queryById(Long subjectsId) {
        // 通过ID从数据库中查询主题信息
        SubjectInfo subjectInfo = subjectInfoDao.selectById(subjectsId);
        Integer subjectType = subjectInfo.getSubjectType();

        // 根据主题类型获取相应的主题处理程序
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(subjectType);
        // 使用处理程序查询主题的选项和答案
        SubjectOptionDTO subjectOptionDTO = handler.query(subjectInfo.getSubjectId());

        // 将主题信息实体转换为响应对象
        SubjectInfoResp subjectInfoResp = SubjectInfoEntityConverter.INSTANCE.convertEntityToResp(subjectInfo);
        // 设置主题的选项列表和答案
        subjectInfoResp.setOptionList(subjectOptionDTO.getOptionList());
        subjectInfoResp.setSubjectAnswer(subjectOptionDTO.getSubjectAnswer());

        // 填充主题相关的标签名称
        this.fillLabelNames(subjectInfoResp);

        return subjectInfoResp;
    }

    /**
     * 添加题目信息
     * @param subjectInfoReq 包含题目信息请求对象，用于添加题目的基本信息和相关属性如分类、标签等。
     * 方法将题目信息转换为实体，插入基础题目信息到数据库，并根据题目的类型插入相应的附加信息到对应的表中。
     * 同时，也会处理题目与分类、标签之间的关联关系插入。
     */
    @Override
    @Transactional
    public void addSubjectInfo(SubjectInfoReq subjectInfoReq) {
        // 将请求对象转换为实体对象
        SubjectInfo subjectInfo = SubjectInfoEntityConverter.INSTANCE.convertReqToEntity(subjectInfoReq);
        try {
            // 插入题目基础信息
            // TODO: 根据当前用户设置作者
            subjectInfoDao.insert(subjectInfo);
            // 设置题目ID，供后续使用
            subjectInfoReq.setSubjectId(subjectInfo.getSubjectId());
            // 根据题目类型获取相应的处理类
            SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(subjectInfo.getSubjectType());
            // 根据题目的类型，执行相应的添加逻辑
            handler.add(subjectInfoReq);

            // 处理题目与标签、分类的关联关系
            List<Long> labelIds = subjectInfoReq.getLabelIds();
            List<SubjectMapping> mappingList = labelIds.stream()
                    .map(labelId ->
                            // 构建题目与标签、分类的关联对象
                            new SubjectMapping()
                                    .setSubjectId(subjectInfoReq.getSubjectId())
                                    .setCategoryId(subjectInfoReq.getCategoryId())
                                    .setLabelId(labelId)
                    ).collect(Collectors.toList());
            // 批量插入题目与标签、分类的关联信息
            subjectMappingService.saveBatch(mappingList);
        } catch (Exception e) {
            // 抛出系统异常，表示添加题目信息失败
            throw new SystemException("添加题目信息失败");
        }

    }


    /**
     * 分页查询主题信息。
     *
     * @param subjectInfoReq 包含查询条件（如分类ID、标签ID）和分页信息（页码、页大小）的请求对象。
     * @return 返回主题信息的分页结果，包括当前页码、每页大小、总记录数和查询结果集。
     */
    @Override
    public PageResult<SubjectInfoResp> queryPage(SubjectInfoReq subjectInfoReq) {
        // 检查分页参数是否合法
        if (subjectInfoReq.getPageNo() <= 0 || subjectInfoReq.getPageSize() <= 0) {
            throw new IllegalArgumentException("Invalid page number or page size");
        }
        // 初始化分页对象
        IPage<SubjectInfo> page = new Page<SubjectInfo>()
                .setPages(subjectInfoReq.getPageNo()).setPages(subjectInfoReq.getPageSize());
        // 处理分类ID和标签ID的查询条件
        Long categoryId = subjectInfoReq.getCategoryId();
        Long labelIds = subjectInfoReq.getLabelId();
        // 对categoryId和labelIds进行空值检查
        if (categoryId == null && labelIds == null) {
            return new PageResult<SubjectInfoResp>();
        }
        List<SubjectMapping> subjectMappings = subjectMappingService.queryList(categoryId, labelIds);
        // 根据分类和标签ID获取主题ID列表
        if (CollectionUtils.isEmpty(subjectMappings)) {
            return new PageResult<SubjectInfoResp>();
        }
        List<Long> subjectIds = subjectMappings.stream().map(SubjectMapping::getSubjectId).collect(Collectors.toList());
        // 设置查询条件为题目ID在指定列表中
        LambdaQueryWrapper<SubjectInfo> subjectInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<SubjectInfo> queryWrapper = subjectInfoLambdaQueryWrapper
                .eq(SubjectInfo::getIsDeleted, SystemConstants.NO_DELETE)
                .in(SubjectInfo::getSubjectId, subjectIds);
        if(subjectInfoReq.getSubjectDifficult()!=null){
            queryWrapper.eq(SubjectInfo::getSubjectDifficult,subjectInfoReq.getSubjectDifficult());
        }
        // 执行分页查询
        IPage<SubjectInfo> subjectInfoIPage = subjectInfoDao.selectPage(page, queryWrapper);
        // 获取查询结果并转换为响应对象列表
        List<SubjectInfo> subjectInfoList = subjectInfoIPage.getRecords();
        List<SubjectInfoResp> subjectInfoRespList = SubjectInfoEntityConverter.INSTANCE.convertEntityToRespList(subjectInfoList);
        List<SubjectInfoResp> collect = subjectInfoRespList.stream().peek(subjectInfoResp -> {
            try {
                this.fillLabelNames(subjectInfoResp);
            } catch (Exception e) {
                throw new SystemException("查询失败");
            }
        }).collect(Collectors.toList());

        // 构建并返回分页查询结果
        return new PageResult<SubjectInfoResp>()
                .setPageNo(subjectInfoIPage.getCurrent())
                .setPageSize(subjectInfoIPage.getSize())
                .setTotal(subjectInfoIPage.getTotal())
                .setResult(collect);
    }



    /**
     * 填充标签名称到SubjectInfoResp对象中
     *
     * @param subjectInfoResp
     */
    private void fillLabelNames(SubjectInfoResp subjectInfoResp) {
        SubjectMapping subjectMapping = new SubjectMapping().setSubjectId(subjectInfoResp.getSubjectId());
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        if (!CollectionUtils.isEmpty(mappingList)) {
            List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
            List<SubjectLabel> subjectLabelList = subjectLabelService.queryList(labelIdList);
            List<String> labelNameList = subjectLabelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());
            subjectInfoResp.setLabelName(labelNameList);
        }
    }


}

