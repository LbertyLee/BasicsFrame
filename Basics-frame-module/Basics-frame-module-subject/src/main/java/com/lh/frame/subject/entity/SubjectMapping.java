package com.lh.frame.subject.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lh.frame.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 题目分类关系表(FrameSubjectMapping)表实体类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:05:32
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("frame_subject_mapping")
public class SubjectMapping extends BaseEntity {

    //题目id
    private Long subjectId;

    //分类id
    private Long categoryId;

    //标签id
    private Long labelId;

    private Integer isDeleted;


}

