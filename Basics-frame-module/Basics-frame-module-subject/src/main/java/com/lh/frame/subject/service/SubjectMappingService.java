package com.lh.frame.subject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.frame.subject.domain.dto.SubjectMappingDTO;
import com.lh.frame.subject.entity.SubjectMapping;

import java.util.List;

/**
 * 题目分类关系表(FrameSubjectMapping)表服务接口
 *
 * @author LbertyLee
 * @since 2024-04-09 17:05:32
 */
public interface SubjectMappingService  extends IService<SubjectMapping> {

    List<SubjectMapping> queryList(Long categoryId, Long labelIds);

    List<SubjectMapping> queryLabelId(SubjectMapping subjectMapping);

}

