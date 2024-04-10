package com.lh.frame.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.subject.domain.vo.entity.SubjectJudge;
import org.apache.ibatis.annotations.Mapper;

/**
 * 判断题(FrameSubjectJudge)表数据库访问层
 *
 * @author LbertyLee
 * @since 2024-04-09 17:08:12
 */
@Mapper
public interface SubjectJudgeDao extends BaseMapper<SubjectJudge> {

}

