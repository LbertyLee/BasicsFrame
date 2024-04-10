package com.lh.frame.subject.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.subject.domain.vo.entity.SubjectRadio;
import org.apache.ibatis.annotations.Mapper;

/**
 * 单选题信息表(FrameSubjectRadio)表数据库访问层
 *
 * @author LbertyLee
 * @since 2024-04-09 17:08:27
 */
@Mapper
public interface SubjectRadioDao extends BaseMapper<SubjectRadio> {

}

