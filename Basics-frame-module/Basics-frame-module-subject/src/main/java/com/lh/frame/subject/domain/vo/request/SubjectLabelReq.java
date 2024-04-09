package com.lh.frame.subject.domain.vo.request;

import lombok.Data;

@Data
public class SubjectLabelReq {

    //标签分类
    private String labelName;

    //排序
    private Integer sortNum;

    //分类id
    private String categoryId;

    private Integer isDeleted;
}
