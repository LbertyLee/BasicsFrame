package com.lh.frame.subject.domain.vo.response;

import com.lh.frame.common.entity.BaseEntity;
import com.lh.frame.subject.domain.dto.SubjectAnswerDTO;
import lombok.Data;

import java.util.List;

@Data
public class SubjectInfoResp extends BaseEntity {


    private Long subjectId;

    //题目名称
    private String subjectName;

    //题目难度
    private Integer subjectDifficult;

    //出题人名
    private String settleName;

    //题目类型 1单选 2多选 3判断 4简答
    private Integer subjectType;

    //题目分数
    private Integer subjectScore;

    //题目解析
    private String subjectParse;

    /**
     * 标签name
     */
    private List<String> labelName;

    private Integer isDeleted;
    /**
     * 答案选项
     */
    private List<SubjectAnswerDTO> optionList;

    /**
     * 题目答案
     */
    private String subjectAnswer;
}
