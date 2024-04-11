package com.lh.frame.subject.handler;

import com.lh.frame.subject.dao.SubjectBriefDao;
import com.lh.frame.subject.domain.dto.SubjectAnswerDTO;
import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.domain.vo.response.SubjectInfoResp;
import com.lh.frame.subject.entity.SubjectMultiple;
import com.lh.frame.subject.enums.SubjectInfoTypeEnum;
import com.lh.frame.subject.service.SubjectBriefService;
import com.lh.frame.subject.service.SubjectMultipleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MultipleTypeHandler implements SubjectTypeHandler{

    @Resource
    private SubjectMultipleService subjectMultipleService;
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.MULTIPLE;
    }

    @Override
    public void add(SubjectInfoReq subjectInfoReq) {
        List<SubjectAnswerDTO> optionList = subjectInfoReq.getOptionList();
        List<SubjectMultiple> subjectMultiples = optionList.stream().map(option -> {
            // 添加选项
            SubjectMultiple subjectMultiple = new SubjectMultiple();
            subjectMultiple.setSubjectId(subjectInfoReq.getSubjectId());
            subjectMultiple.setOptionContent(option.getOptionContent());
            subjectMultiple.setOptionType(option.getOptionType());
            subjectMultiple.setOptionContent(option.getOptionContent());
            subjectMultiple.setIsCorrect(option.getIsCorrect());
            return subjectMultiple;
        }).collect(Collectors.toList());
        subjectMultipleService.saveBatch(subjectMultiples);
    }

    @Override
    public SubjectInfoResp query(Long subjectId) {
        return null;
    }
}
