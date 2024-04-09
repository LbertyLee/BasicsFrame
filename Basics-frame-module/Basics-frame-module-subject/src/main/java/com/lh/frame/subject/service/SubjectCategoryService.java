package com.lh.frame.subject.service;


import com.lh.frame.subject.domain.vo.request.SubjectCategoryReq;

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

    void deleteCategory(Long id);

    /**
     * 查询岗位列表
     * @return
     */
    List<SubjectCategoryReq> queryParent();

    /**
     * 根据岗位ID查询分类列表
     * @param id
     * @return
     */
    List<SubjectCategoryReq> queryCategoryList(Long id);
}

