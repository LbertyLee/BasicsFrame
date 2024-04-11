package com.lh.frame.subject.handler;


import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.domain.vo.response.SubjectInfoResp;
import com.lh.frame.subject.enums.SubjectInfoTypeEnum;

public interface SubjectTypeHandler {

    /**
     * 枚举身份的识别
     */
    SubjectInfoTypeEnum getHandlerType();

    /**
     * 实际的题目的插入
     */
    void add(SubjectInfoReq subjectInfoReq);

    /**
     * 实际的题目的查询
     */
    SubjectInfoResp query(Long subjectId);

}
