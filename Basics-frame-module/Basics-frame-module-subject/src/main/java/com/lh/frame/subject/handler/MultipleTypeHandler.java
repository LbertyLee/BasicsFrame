package com.lh.frame.subject.handler;

import com.lh.frame.subject.convert.SubjectMultipleEntityConverter;
import com.lh.frame.subject.dao.SubjectBriefDao;
import com.lh.frame.subject.domain.dto.SubjectAnswerDTO;
import com.lh.frame.subject.domain.dto.SubjectOptionDTO;
import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.domain.vo.response.SubjectInfoResp;
import com.lh.frame.subject.entity.SubjectMultiple;
import com.lh.frame.subject.enums.SubjectInfoTypeEnum;
import com.lh.frame.subject.service.SubjectBriefService;
import com.lh.frame.subject.service.SubjectMultipleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多选题目的策略类
 */
@Component
public class MultipleTypeHandler implements SubjectTypeHandler {
    // 注入SubjectMultipleService，用于处理多选题相关的服务操作
    @Resource
    private SubjectMultipleService subjectMultipleService;

    /**
     * 获取处理器类型，本处理器处理多选题。
     *
     * @return 返回题型枚举，此处为MULTIPLE表示多选题。
     */
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.MULTIPLE;
    }

    /**
     * 添加多选题题目及其选项。
     *
     * @param subjectInfoReq 包含题目信息和选项信息的请求对象。
     */
    @Override
    public void add(SubjectInfoReq subjectInfoReq) {
        // 将subjectInfoReq中的选项信息转换为SubjectMultiple对象集合
        List<SubjectAnswerDTO> optionList = subjectInfoReq.getOptionList();
        List<SubjectMultiple> subjectMultiples = optionList.stream().map(option -> {
            SubjectMultiple subjectMultiple = new SubjectMultiple();
            // 设置题目选项的基本信息
            subjectMultiple.setSubjectId(subjectInfoReq.getSubjectId());
            subjectMultiple.setOptionContent(option.getOptionContent());
            subjectMultiple.setOptionType(option.getOptionType());
            subjectMultiple.setOptionContent(option.getOptionContent());
            subjectMultiple.setIsCorrect(option.getIsCorrect());
            return subjectMultiple;
        }).collect(Collectors.toList());
        // 批量保存题目选项信息
        subjectMultipleService.saveBatch(subjectMultiples);
    }

    /**
     * 根据题目ID查询多选题的选项信息。
     *
     * @param subjectId 题目ID。
     * @return 返回包含选项信息的SubjectOptionDTO对象。
     */
    @Override
    public SubjectOptionDTO query(Long subjectId) {
        // 根据题目ID查询题目选项信息
        List<SubjectMultiple> subjectMultiples = subjectMultipleService.queryBySubjectId(subjectId);
        // 将查询到的SubjectMultiple实体列表转换为SubjectAnswerDTO列表
        List<SubjectAnswerDTO> subjectAnswerDTOS = SubjectMultipleEntityConverter.INSTANCE.convertEntityListToAnswerDTOList(subjectMultiples);
        // 构建并返回包含选项信息的SubjectOptionDTO对象
        return new SubjectOptionDTO().setOptionList(subjectAnswerDTOS);
    }
}

