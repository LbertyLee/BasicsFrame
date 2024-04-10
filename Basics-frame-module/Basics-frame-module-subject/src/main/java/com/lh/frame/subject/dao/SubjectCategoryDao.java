package com.lh.frame.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.subject.domain.vo.entity.SubjectCategory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题目分类(FrameSubjectCategory)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-09 16:53:15
 */
@Mapper
public interface SubjectCategoryDao extends BaseMapper<SubjectCategory> {

}

