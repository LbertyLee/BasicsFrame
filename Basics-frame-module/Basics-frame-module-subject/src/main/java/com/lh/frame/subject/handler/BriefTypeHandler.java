package com.lh.frame.subject.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lh.frame.subject.dao.SubjectBriefDao;
import com.lh.frame.subject.dao.SubjectJudgeDao;
import com.lh.frame.subject.domain.dto.SubjectAnswerDTO;
import com.lh.frame.subject.domain.dto.SubjectOptionDTO;
import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.domain.vo.response.SubjectInfoResp;
import com.lh.frame.subject.entity.SubjectBrief;
import com.lh.frame.subject.entity.SubjectInfo;
import com.lh.frame.subject.enums.SubjectInfoTypeEnum;
import com.lh.frame.subject.service.SubjectBriefService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 简答题目的策略类
 */
@Component // 注解定义这是一个组件
public class BriefTypeHandler implements SubjectTypeHandler {

    @Resource // 注解自动注入SubjectBriefService
    SubjectBriefService subjectBriefService;

    /**
     * 获取处理器类型
     *
     * @return 返回主题信息类型枚举，此处为简要类型
     */
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.BRIEF;
    }

    /**
     * 添加主题信息
     *
     * @param subjectInfoReq 包含主题信息请求对象，主要为主题ID和答案
     */
    @Override
    public void add(SubjectInfoReq subjectInfoReq) {
        // 创建主题简要对象并设置主题ID和答案，然后保存
        SubjectBrief subjectBrief = new SubjectBrief()
                .setSubjectId(subjectInfoReq.getSubjectId())
                .setSubjectAnswer(subjectInfoReq.getSubjectAnswer());
        subjectBriefService.save(subjectBrief);
    }

    /**
     * 根据主题ID查询主题答案
     *
     * @param subjectId 主题ID
     * @return 返回主题选项DTO，包含主题答案
     */
    @Override
    public SubjectOptionDTO query(Long subjectId) {
        // 通过主题ID查询主题简要信息
        SubjectBrief subjectBrief = subjectBriefService.queryBySubjectId(subjectId);
        // 创建并返回主题选项DTO，设置主题答案
        return new SubjectOptionDTO().setSubjectAnswer(subjectBrief.getSubjectAnswer());
    }
}

