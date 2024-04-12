package com.lh.frame.subject.domain.vo.request;

import com.lh.frame.common.entity.PageInfo;
import lombok.Data;

@Data
public class SubjectLikedReq extends PageInfo {

    private Long subjectId;

    //点赞人id
    private String likeUserId;

    //点赞状态 1点赞 0不点赞
    private Integer status;


    private Integer isDeleted;
}
