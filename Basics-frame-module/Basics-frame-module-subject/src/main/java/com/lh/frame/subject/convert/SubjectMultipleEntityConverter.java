package com.lh.frame.subject.convert;

import com.lh.frame.subject.domain.dto.SubjectAnswerDTO;
import com.lh.frame.subject.entity.SubjectMultiple;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectMultipleEntityConverter {
    SubjectMultipleEntityConverter INSTANCE = Mappers.getMapper(SubjectMultipleEntityConverter.class);

    List<SubjectAnswerDTO> convertEntityListToAnswerDTOList(List<SubjectMultiple> subjectMultipleList);
}
