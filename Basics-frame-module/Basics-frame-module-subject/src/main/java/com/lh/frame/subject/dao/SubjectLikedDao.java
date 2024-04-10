package com.lh.frame.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.subject.domain.vo.entity.SubjectLiked;
import org.apache.ibatis.annotations.Mapper;

/**
 * 题目信息表(FrameSubjectLiked)表数据库访问层
 *
 * @author LbertyLee
 * @since 2024-04-09 17:05:12
 */
@Mapper
public interface SubjectLikedDao extends BaseMapper<SubjectLiked> {

}

