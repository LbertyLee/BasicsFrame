package com.lh.frame.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.subject.domain.vo.entity.SubjectLabel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题目标签表(FrameSubjectLabel)表数据库访问层
 *
 * @author LbertyLee
 * @since 2024-04-09 17:04:29
 */
@Mapper
public interface SubjectLabelDao extends BaseMapper<SubjectLabel> {

}

