package com.lh.frame.subject.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SubjectAnswerDTO implements Serializable {

    /**
     * 答案选项标识
     */
    private String optionType;

    /**
     * 答案
     */
    private String optionContent;

    /**
     * 是否正确
     */
    private Integer isCorrect;
}