package com.lh.frame.subject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lh.frame.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 题目标签表(FrameSubjectLabel)表实体类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:04:29
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("frame_subject_label")
public class SubjectLabel extends BaseEntity {


    //标签id
    @TableId(type = IdType.ASSIGN_ID)
    private Long labelId;
    //标签分类
    private String labelName;
    //排序
    private Integer sortNum;
    //分类id
    private String categoryId;

    private Integer isDeleted;

}

