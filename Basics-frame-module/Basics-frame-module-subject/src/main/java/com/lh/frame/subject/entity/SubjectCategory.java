package com.lh.frame.subject.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lh.frame.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 题目分类(FrameSubjectCategory)表实体类
 *
 * @author makejava
 * @since 2024-04-09 16:53:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("frame_subject_category")
public class SubjectCategory extends BaseEntity {


    //分类名称
    private String categoryName;

    //分类类型
    private Integer categoryType;

    //图标连接
    private String imageUrl;

    //父级id
    private Long parentId;


    //是否删除 0: 未删除 1: 已删除
    private Integer isDeleted;
}

