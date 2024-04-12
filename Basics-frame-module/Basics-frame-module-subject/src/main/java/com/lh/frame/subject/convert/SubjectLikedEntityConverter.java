package com.lh.frame.subject.convert;

import com.lh.frame.subject.domain.vo.request.SubjectLikedReq;
import com.lh.frame.subject.entity.SubjectLiked;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubjectLikedEntityConverter {

    SubjectLikedEntityConverter INSTANCE = Mappers.getMapper(SubjectLikedEntityConverter.class);

    SubjectLiked convertSubjectLikedReqToEntity(SubjectLikedReq subjectLikedReq);
}
