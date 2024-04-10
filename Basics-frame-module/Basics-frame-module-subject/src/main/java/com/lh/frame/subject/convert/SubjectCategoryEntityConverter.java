package com.lh.frame.subject.convert;

import com.lh.frame.subject.domain.vo.request.SubjectCategoryReq;
import com.lh.frame.subject.domain.vo.entity.SubjectCategory;
import com.lh.frame.subject.domain.vo.response.SubjectCategoryResp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectCategoryEntityConverter {
    SubjectCategoryEntityConverter INSTANCE = Mappers.getMapper(SubjectCategoryEntityConverter.class);

    SubjectCategory convertReqToEntity(SubjectCategoryReq subjectCategoryReq);

    SubjectCategoryReq convertEntityToReq(SubjectCategory subjectCategory);


    List<SubjectCategoryResp> convertEntityListToRespList(List<SubjectCategory> subjectCategories);

    SubjectCategoryResp convertEntityToResp(SubjectCategory subjectCategory);


}
