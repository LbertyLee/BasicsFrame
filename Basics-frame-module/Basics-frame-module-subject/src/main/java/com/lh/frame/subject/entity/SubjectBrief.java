package com.lh.frame.subject.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lh.frame.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 简答题(FrameSubjectBrief)表实体类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:07:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("frame_subject_brief")
public class SubjectBrief extends BaseEntity {

    //题目id
    @TableId
    private Long subjectId;

    //题目答案
    private String subjectAnswer;

    //是否删除
    private Integer isDeleted;



}

