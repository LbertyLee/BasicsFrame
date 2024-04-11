package com.lh.frame.subject.domain.vo.response;

import com.lh.frame.common.entity.BaseEntity;
import lombok.Data;

@Data
public class SubjectLabelResp extends BaseEntity {


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
