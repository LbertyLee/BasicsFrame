package com.lh.frame.subject.service;

import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;

/**
 * 题目信息表(FrameSubjectInfo)表服务接口
 *
 * @author LbertyLee
 * @since 2024-04-09 17:07:59
 */
public interface SubjectInfoService  {

    void addSubjectInfo(SubjectInfoReq subjectInfoReq);
}

