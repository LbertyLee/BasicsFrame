package com.lh.frame.subject.domain.vo.response;

import lombok.Data;

@Data
public class SubjectLabelResp {


    //标签id
    private String labelId;

    //标签分类
    private String labelName;

    //排序
    private Integer sortNum;

    //分类id
    private String categoryId;

    private Integer isDeleted;
}
