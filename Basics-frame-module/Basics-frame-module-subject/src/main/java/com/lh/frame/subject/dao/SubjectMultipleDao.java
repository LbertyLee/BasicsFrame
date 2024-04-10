package com.lh.frame.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.subject.domain.vo.entity.SubjectMultiple;
import org.apache.ibatis.annotations.Mapper;

/**
 * 多选题信息表(FrameSubjectMultiple)表数据库访问层
 *
 * @author LbertyLee
 * @since 2024-04-09 17:09:01
 */
@Mapper
public interface SubjectMultipleDao extends BaseMapper<SubjectMultiple> {

}

