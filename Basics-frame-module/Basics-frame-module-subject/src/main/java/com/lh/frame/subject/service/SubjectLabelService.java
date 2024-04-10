package com.lh.frame.subject.service;

import com.lh.frame.common.entity.PageResult;
import com.lh.frame.subject.domain.vo.request.SubjectLabelReq;
import com.lh.frame.subject.domain.vo.response.SubjectLabelResp;

import java.util.List;

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

    /**
     * 更新标签信息。
     *
     * @param subjectLabelReq 包含标签更新信息的对象，例如要更新的标签名等。
     *                        该对象的具体结构和字段取决于业务需求。
     * @return 由于方法签名中没有返回值，所以此方法更新操作完成后不返回任何内容。
     */
    void updateLabel(SubjectLabelReq subjectLabelReq);


    /**
     * 获取指定分类下的标签列表。
     *
     * @param categoryId 分类的ID，用于指定获取哪个分类下的标签列表。
     * @return 返回一个SubjectLabelReq类型的列表，包含了指定分类下的所有标签请求信息。
     */
    List<SubjectLabelResp> getLabelList(Long categoryId);

    /**
     * 分页查询主题标签信息的接口方法
     *
     * @param subjectLabelReq 包含查询条件和分页信息的请求对象
     * @return 返回主题标签的分页查询结果，包括总页数、当前页码、每页记录数和标签信息列表
     */
    PageResult<SubjectLabelResp> page(SubjectLabelReq subjectLabelReq);

}

