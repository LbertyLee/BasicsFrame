package com.lh.frame.subject.convert;

import com.lh.frame.subject.domain.dto.SubjectAnswerDTO;
import com.lh.frame.subject.entity.SubjectRadio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectRadioEntityConverter {
    SubjectRadioEntityConverter INSTANCE = Mappers.getMapper(SubjectRadioEntityConverter.class);

    List<SubjectAnswerDTO> convertEntityListToRespList(List<SubjectRadio> subjectRadioList);
}
