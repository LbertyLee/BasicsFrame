package com.lh.frame.subject.handler;

import com.lh.frame.subject.dao.SubjectBriefDao;
import com.lh.frame.subject.dao.SubjectJudgeDao;
import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.domain.vo.response.SubjectInfoResp;
import com.lh.frame.subject.entity.SubjectBrief;
import com.lh.frame.subject.enums.SubjectInfoTypeEnum;
import com.lh.frame.subject.service.SubjectBriefService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BriefTypeHandler implements SubjectTypeHandler{

    @Resource
    SubjectBriefService subjectBriefService;
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.BRIEF;
    }

    @Override
    public void add(SubjectInfoReq subjectInfoReq) {
        SubjectBrief subjectBrief = new SubjectBrief()
                .setSubjectId(subjectInfoReq.getSubjectId())
                .setSubjectAnswer(subjectInfoReq.getSubjectAnswer());
        subjectBriefService.save(subjectBrief);
    }

    @Override
    public SubjectInfoResp query(Long subjectId) {
        return null;
    }
}
