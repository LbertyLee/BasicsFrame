package com.lh.frame.subject.domain.vo.response;

import lombok.Data;

@Data
public class SubjectLikedResp {

    private Long subjectId;

    //点赞人id
    private String likeUserId;

    private SubjectInfoResp subjectInfoResp;



}
