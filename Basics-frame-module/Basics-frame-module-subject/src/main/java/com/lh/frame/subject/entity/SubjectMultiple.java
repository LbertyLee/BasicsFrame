package com.lh.frame.subject.entity;



import com.baomidou.mybatisplus.annotation.TableName;

import com.lh.frame.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 * 多选题信息表(FrameSubjectMultiple)表实体类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:09:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("frame_subject_multiple")
public class SubjectMultiple extends BaseEntity {

    //题目id
    private Long subjectId;

    //选项类型
    private String optionType;

    //选项内容
    private String optionContent;

    //是否正确
    private Integer isCorrect;


    private Integer isDeleted;


}

