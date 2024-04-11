package com.lh.frame.subject.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectMappingEntityConverter {
    SubjectMappingEntityConverter INSTANCE = Mappers.getMapper(SubjectMappingEntityConverter.class);

}
