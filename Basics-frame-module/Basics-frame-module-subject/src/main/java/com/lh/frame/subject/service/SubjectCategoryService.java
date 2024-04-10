package com.lh.frame.subject.service;


import com.lh.frame.subject.domain.vo.request.SubjectCategoryReq;
import com.lh.frame.subject.domain.vo.response.SubjectCategoryResp;

import java.util.List;

/**
 * 题目分类(FrameSubjectCategory)表服务接口
 *
 * @author makejava
 * @since 2024-04-09 16:49:32
 */
public interface SubjectCategoryService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SubjectCategoryReq queryById(Long id);

    /**
     * 新增数据
     *
     * @param subjectCategoryReq 实例对象
     */
    void addCategory(SubjectCategoryReq subjectCategoryReq);

    /**
     * 删除指定的类别。
     *
     * @param id 类别的唯一标识符。
     * 该方法没有返回值，它只是负责删除指定ID的类别。
     */
    void deleteCategory(Long id);


    /**
     * 查询岗位列表
     * @return
     */
    List<SubjectCategoryResp> queryParent();

    /**
     * 根据岗位ID查询分类列表
     * @param id
     * @return
     */
    List<SubjectCategoryResp> queryCategoryList(Long id);

    /**
     * 更新主题分类信息。
     *
     * @param subjectCategoryReq 包含更新后的主题分类信息的请求对象。
     *                           该对象应包含需要更新的分类的ID以及更新后的分类属性值。
     *                           通过这个参数，函数将更新服务器上相应主题分类的信息。
     * @return 无返回值。但可能会抛出异常，如果更新过程中遇到任何错误（如数据库连接问题或输入数据不合法）。
     */
    void updateCategory(SubjectCategoryReq subjectCategoryReq);


}

