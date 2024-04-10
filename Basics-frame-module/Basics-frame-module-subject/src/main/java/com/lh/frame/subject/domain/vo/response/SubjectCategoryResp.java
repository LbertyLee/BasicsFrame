package com.lh.frame.subject.domain.vo.response;

import com.lh.frame.subject.domain.vo.request.SubjectLabelReq;
import lombok.Data;

import java.util.List;

@Data
public class SubjectCategoryResp {
    //id
    private Long id;

    //分类id
    private Long categoryId;

    //分类名称
    private String categoryName;

    //分类类型(1:岗位 2:分类)
    private Integer categoryType;

    //图标连接
    private String imageUrl;

    //父级id
    private Long parentId;

    //是否删除 0: 未删除 1: 已删除
    private Long isDeleted;

    private List<SubjectLabelResp> subjectLabelRespList;
}
