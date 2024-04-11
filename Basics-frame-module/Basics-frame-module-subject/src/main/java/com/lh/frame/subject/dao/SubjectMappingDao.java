package com.lh.frame.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.subject.entity.SubjectMapping;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题目分类关系表(FrameSubjectMapping)表数据库访问层
 *
 * @author LbertyLee
 * @since 2024-04-09 17:05:32
 */
@Mapper
public interface SubjectMappingDao extends BaseMapper<SubjectMapping> {

}

