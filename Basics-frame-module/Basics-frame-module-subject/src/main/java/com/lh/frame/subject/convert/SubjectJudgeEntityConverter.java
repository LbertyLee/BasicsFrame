package com.lh.frame.subject.convert;

import com.lh.frame.subject.domain.dto.SubjectAnswerDTO;
import com.lh.frame.subject.entity.SubjectJudge;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectJudgeEntityConverter {
    SubjectJudgeEntityConverter INSTANCE = Mappers.getMapper(SubjectJudgeEntityConverter.class);
    List<SubjectAnswerDTO> convertEntityListToRespList(List<SubjectJudge> subjectJudgeList);
}


