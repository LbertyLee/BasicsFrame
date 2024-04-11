package com.lh.frame.subject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.frame.subject.entity.SubjectBrief;
import com.lh.frame.subject.entity.SubjectInfo;
import com.lh.frame.subject.entity.SubjectMultiple;

import java.util.List;

/**
 * 简答题(FrameSubjectBrief)表服务接口
 *
 * @author LbertyLee
 * @since 2024-04-09 17:07:27
 */
public interface SubjectBriefService  extends IService<SubjectBrief> {


    SubjectBrief queryBySubjectId(Long subjectId);

}

