package com.lh.frame.subject.convert;

import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.domain.vo.response.SubjectInfoResp;
import com.lh.frame.subject.entity.SubjectInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectInfoEntityConverter {
    SubjectInfoEntityConverter INSTANCE = Mappers.getMapper(SubjectInfoEntityConverter.class);



    SubjectInfo convertReqToEntity(SubjectInfoReq subjectInfoReq);

    SubjectInfoResp convertEntityToResp(SubjectInfo subjectInfo);

    List<SubjectInfoResp> convertEntityToRespList(List<SubjectInfo> subjectInfoList);
}
