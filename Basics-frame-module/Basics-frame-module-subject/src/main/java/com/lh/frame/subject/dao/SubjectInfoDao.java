package com.lh.frame.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.subject.entity.SubjectInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题目信息表(FrameSubjectInfo)表数据库访问层
 *
 * @author LbertyLee
 * @since 2024-04-09 17:07:59
 */
@Mapper
public interface SubjectInfoDao extends BaseMapper<SubjectInfo> {

}

