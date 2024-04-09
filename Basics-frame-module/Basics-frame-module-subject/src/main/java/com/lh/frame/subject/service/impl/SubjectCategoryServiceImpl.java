package com.lh.frame.subject.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lh.frame.common.constant.basic.SystemConstants;
import com.lh.frame.subject.constant.SubjectConstant;
import com.lh.frame.subject.convert.SubjectCategoryEntityConverter;
import com.lh.frame.subject.dao.SubjectCategoryDao;
import com.lh.frame.subject.domain.vo.request.SubjectCategoryReq;
import com.lh.frame.subject.entity.SubjectCategory;
import com.lh.frame.subject.service.SubjectCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 题目分类(FrameSubjectCategory)表服务实现类
 *
 * @author makejava
 * @since 2024-04-09 16:53:19
 */
@Service
public class SubjectCategoryServiceImpl implements SubjectCategoryService {

    @Resource
    private SubjectCategoryDao subjectCategoryDao;




    @Override
    public SubjectCategoryReq queryById(Long id) {
        try {
            SubjectCategory subjectCategory = subjectCategoryDao.selectById(id);
            return SubjectCategoryEntityConverter.INSTANCE.convertEntityToReq(subjectCategory);
        } catch (Exception e) {
            throw new RuntimeException("查询题目分类失败");
        }
    }

    @Override
    public void addCategory(SubjectCategoryReq subjectCategoryReq) {
        SubjectCategory subjectCategory = SubjectCategoryEntityConverter.INSTANCE.convertReqToEntity(subjectCategoryReq);
        try {
            subjectCategoryDao.insert(subjectCategory);
        } catch (Exception e) {
            throw new RuntimeException("添加题目分类失败");
        }

    }

    @Override
    public void deleteCategory(Long categoryId) {
        try {
            subjectCategoryDao.updateById(new SubjectCategory().
                    setCategoryId(categoryId).setIsDeleted(Long.valueOf(SystemConstants.IS_DELETE)));
        } catch (Exception e) {
            throw new RuntimeException("删除题目分类失败");
        }

    }


    /**
     * 查询题目分类的父类目。该方法不接受任何参数，返回一个分类父类目的列表。
     *
     * @return List<SubjectCategoryReq> 分类父类目的请求对象列表。
     */
    @Override
    public List<SubjectCategoryReq> queryParent() {
        try {
            // 从数据库中查询符合条件的题目分类信息
            List<SubjectCategory> subjectCategories = subjectCategoryDao.selectList(
                    new LambdaQueryWrapper<SubjectCategory>()
                            .eq(SubjectCategory::getCategoryType, SubjectConstant.SUBJECT_POSITION_ID)
                            .eq(SubjectCategory::getIsDeleted, SystemConstants.NO_DELETE));

            // 将查询到的实体类列表转换为请求对象列表
            return SubjectCategoryEntityConverter.INSTANCE.convertEntityListToReqList(subjectCategories);
        } catch (Exception e) {
            // 若查询过程中出现异常，则抛出运行时异常
            throw new RuntimeException("查询题目分类大类失败");
        }
    }


    @Override
    public List<SubjectCategoryReq> queryCategoryList(Long id) {
        try {
            List<SubjectCategory> subjectCategories = subjectCategoryDao.selectList(
                    new LambdaQueryWrapper<SubjectCategory>()
                            .eq(SubjectCategory::getParentId, id)
                            .eq(SubjectCategory::getIsDeleted, SystemConstants.NO_DELETE));
            return SubjectCategoryEntityConverter.INSTANCE.convertEntityListToReqList(subjectCategories);
        }catch (Exception e){
            throw new RuntimeException("查询题目分类小类失败");
        }

    }


}

