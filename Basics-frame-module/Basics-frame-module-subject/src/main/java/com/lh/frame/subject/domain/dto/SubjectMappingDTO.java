package com.lh.frame.subject.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubjectMappingDTO {

    //题目id
    private Long subjectId;

    //分类id
    private Long categoryId;

    //标签id
    private Long labelId;

    private Integer isDeleted;

    private List<String> labelName;

}
