package com.lh.frame.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.subject.entity.SubjectBrief;
import org.apache.ibatis.annotations.Mapper;

/**
 * 简答题(FrameSubjectBrief)表数据库访问层
 *
 * @author LbertyLee
 * @since 2024-04-09 17:07:27
 */
@Mapper
public interface SubjectBriefDao extends BaseMapper<SubjectBrief> {

}

