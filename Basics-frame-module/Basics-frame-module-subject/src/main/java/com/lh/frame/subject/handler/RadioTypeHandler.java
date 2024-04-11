package com.lh.frame.subject.handler;

import com.lh.frame.subject.convert.SubjectRadioEntityConverter;
import com.lh.frame.subject.domain.dto.SubjectAnswerDTO;
import com.lh.frame.subject.domain.dto.SubjectOptionDTO;
import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.entity.SubjectRadio;
import com.lh.frame.subject.enums.SubjectInfoTypeEnum;
import com.lh.frame.subject.service.SubjectRadioService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 单选题目的策略类
 */
@Component
public class RadioTypeHandler implements SubjectTypeHandler {
    // 注入SubjectRadioService，用于处理题目与选项的保存和查询
    @Resource
    private SubjectRadioService subjectRadioService;

    /**
     * 获取处理器类型
     *
     * @return 返回题目信息类型枚举，此处为单选题
     */
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.RADIO;
    }

    /**
     * 添加单选题选项
     *
     * @param subjectInfoReq 包含题目信息和选项信息的请求对象
     *                       将subjectInfoReq中的选项信息转换为SubjectRadio对象列表，并批量保存到数据库
     */
    @Override
    public void add(SubjectInfoReq subjectInfoReq) {
        // 将请求中的选项转换为SubjectRadio实体列表
        List<SubjectRadio> subjectRadioList = subjectInfoReq.getOptionList().stream().map(option -> {
            return new SubjectRadio().setSubjectId(subjectInfoReq.getSubjectId())
                    .setIsCorrect(option.getIsCorrect())
                    .setOptionType(option.getOptionType())
                    .setOptionContent(option.getOptionContent());
        }).collect(Collectors.toList());
        // 批量保存转换后的选项信息
        subjectRadioService.saveBatch(subjectRadioList);
    }

    /**
     * 查询单选题选项
     *
     * @param subjectId 题目的ID
     * @return 返回包含选项信息的DTO对象
     * 根据题目ID查询选项信息，转换为SubjectOptionDTO并返回
     */
    @Override
    public SubjectOptionDTO query(Long subjectId) {
        // 根据题目ID查询选项信息
        List<SubjectRadio> subjectRadioList = subjectRadioService.queryBySubjectId(subjectId);
        // 将查询到的选项信息转换为响应对象列表
        List<SubjectAnswerDTO> subjectAnswerDTOS = SubjectRadioEntityConverter.INSTANCE.convertEntityListToRespList(subjectRadioList);
        // 构建并返回包含选项信息的DTO对象
        return new SubjectOptionDTO().setOptionList(subjectAnswerDTOS);
    }
}

