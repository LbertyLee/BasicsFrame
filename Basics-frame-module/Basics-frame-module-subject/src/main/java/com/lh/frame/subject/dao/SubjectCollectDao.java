package com.lh.frame.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.subject.entity.SubjectCollect;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题目信息表(FrameSubjectCollect)表数据库访问层
 *
 * @author LbertyLee
 * @since 2024-04-09 17:06:01
 */
@Mapper
public interface SubjectCollectDao extends BaseMapper<SubjectCollect> {

}

