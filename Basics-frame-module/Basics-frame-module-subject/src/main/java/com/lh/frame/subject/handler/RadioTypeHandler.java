package com.lh.frame.subject.handler;

import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.domain.vo.response.SubjectInfoResp;
import com.lh.frame.subject.entity.SubjectJudge;
import com.lh.frame.subject.entity.SubjectRadio;
import com.lh.frame.subject.enums.SubjectInfoTypeEnum;
import com.lh.frame.subject.service.SubjectRadioService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RadioTypeHandler implements SubjectTypeHandler{

    @Resource
    private SubjectRadioService subjectRadioService;
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.RADIO;
    }

    @Override
    public void add(SubjectInfoReq subjectInfoReq) {
        List<SubjectRadio> subjectRadioList = subjectInfoReq.getOptionList().stream().map(option -> {
            return new SubjectRadio().setSubjectId(subjectInfoReq.getSubjectId())
                    .setIsCorrect(option.getIsCorrect())
                    .setOptionType(option.getOptionType())
                    .setOptionContent(option.getOptionContent());
        }).collect(Collectors.toList());
        subjectRadioService.saveBatch(subjectRadioList);
    }

    @Override
    public SubjectInfoResp query(Long subjectId) {
        return null;
    }
}
