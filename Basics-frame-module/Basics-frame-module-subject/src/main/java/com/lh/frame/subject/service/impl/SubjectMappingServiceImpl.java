package com.lh.frame.subject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.frame.subject.dao.SubjectMappingDao;
import com.lh.frame.subject.entity.SubjectMapping;
import com.lh.frame.subject.service.SubjectMappingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 题目分类关系表(FrameSubjectMapping)表服务实现类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:05:32
 */
@Service
public class SubjectMappingServiceImpl  extends ServiceImpl<SubjectMappingDao , SubjectMapping> implements SubjectMappingService {

    @Resource
    private SubjectMappingDao subjectMappingDao;

    /**
     * 查询指定分类ID和标签ID列表下的主题映射列表。
     *
     * @param categoryId 分类ID，指定查询的分类。
     * @param labelIds 标签ID列表，指定查询的标签集合。
     * @return 返回主题映射列表，这些映射属于指定的分类且标签ID在给定的列表中。
     */
    @Override
    public List<SubjectMapping> queryList(Long categoryId,Long labelIds) {
        // 创建查询包装器，并设置查询条件为分类ID等于categoryId且标签ID在labelIds列表中
        LambdaQueryWrapper<SubjectMapping> subjectMappingLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<SubjectMapping> queryWrapper = subjectMappingLambdaQueryWrapper.eq(SubjectMapping::getCategoryId, categoryId)
                .eq(SubjectMapping::getLabelId, labelIds);
        // 根据查询条件从数据库中选择主题映射列表并返回
        return  subjectMappingDao.selectList(queryWrapper);
    }

    @Override
    public List<SubjectMapping> queryLabelId(SubjectMapping subjectMapping) {
        LambdaQueryWrapper<SubjectMapping> queryWrapper = new LambdaQueryWrapper<SubjectMapping>()
                .eq(SubjectMapping::getSubjectId, subjectMapping.getSubjectId());
        return subjectMappingDao.selectList(queryWrapper);
    }
}

