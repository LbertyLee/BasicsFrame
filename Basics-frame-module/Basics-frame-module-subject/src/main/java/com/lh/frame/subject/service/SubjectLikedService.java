package com.lh.frame.subject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.frame.common.entity.PageResult;
import com.lh.frame.subject.domain.vo.request.SubjectLikedReq;
import com.lh.frame.subject.domain.vo.response.SubjectLikedResp;
import com.lh.frame.subject.entity.SubjectLiked;

/**
 * 题目信息表(FrameSubjectLiked)表服务接口
 *
 * @author LbertyLee
 * @since 2024-04-09 17:05:12
 */
public interface SubjectLikedService  extends IService<SubjectLiked> {

    void addSubjectLiked(SubjectLikedReq subjectLikedReq);

    PageResult<SubjectLikedResp> querySubjectLikedPage(SubjectLikedReq subjectLikedReq);

    void syncLiked();
}

