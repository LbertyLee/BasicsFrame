package com.lh.frame.subject.convert;

import com.lh.frame.subject.domain.vo.request.SubjectLabelReq;
import com.lh.frame.subject.entity.SubjectLabel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectLabelEntityConverter {
    SubjectLabelEntityConverter INSTANCE = Mappers.getMapper(SubjectLabelEntityConverter.class);

    SubjectLabel convertReqToEntity(SubjectLabelReq subjectLabelReq);

    SubjectLabelReq convertEntityToReq(SubjectLabel subjectLabel);

    List<SubjectLabelReq> convertEntityListToReqList(List<SubjectLabel> subjectLabelList);

}
