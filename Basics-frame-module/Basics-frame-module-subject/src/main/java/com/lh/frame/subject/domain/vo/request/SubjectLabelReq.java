package com.lh.frame.subject.domain.vo.request;

import com.lh.frame.common.entity.PageInfo;
import lombok.Data;

@Data
public class SubjectLabelReq extends PageInfo {

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
