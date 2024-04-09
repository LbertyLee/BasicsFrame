package com.lh.frame.subject.service;

import com.lh.frame.subject.domain.vo.request.SubjectLabelReq;

/**
 * 题目标签表(FrameSubjectLabel)表服务接口
 *
 * @author LbertyLee
 * @since 2024-04-09 17:04:29
 */
public interface SubjectLabelService  {



    /**
     * 添加标签
     *
     * @param subjectLabelReq 包含标签信息的请求对象，用于指定要添加的标签详情。
     *                        该对象应包含标签的名称、类别等标识标签的必要信息。
     * @return 无返回值
     *
     *         本方法用于将指定的标签信息添加到系统中。通过接收一个封装了标签信息的请求对象，
     *         将其持久化到数据库或其他存储介质中，实现标签的添加功能。
     */
    void addLabel(SubjectLabelReq subjectLabelReq);

    void deleteLabel(Long labelId);
}

