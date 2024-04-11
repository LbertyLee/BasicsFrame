package com.lh.frame.subject.service;

import com.lh.frame.common.entity.PageResult;
import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.domain.vo.response.SubjectInfoResp;

import java.util.List;

/**
 * 题目信息表(FrameSubjectInfo)表服务接口
 *
 * @author LbertyLee
 * @since 2024-04-09 17:07:59
 */
public interface SubjectInfoService  {

    /**
     * 添加主题信息
     * @param subjectInfoReq 包含主题信息请求参数的对象
     */
    void addSubjectInfo(SubjectInfoReq subjectInfoReq);

    /**
     * 分页查询主题信息
     * @param subjectInfoReq 包含主题信息查询条件的请求参数对象
     * @return 返回主题信息的分页查询结果
     */
    PageResult<SubjectInfoResp> queryPage(SubjectInfoReq subjectInfoReq);

    /**
     * 根据主题ID查询主题信息
     * @param subjectsId 主题的ID
     * @return 返回对应主题ID的主题信息响应对象
     */
    SubjectInfoResp queryById(Long subjectsId);
}

