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
 * 题目信息表(FrameSubjectInfo)表实体类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:07:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("frame_subject_info")
public class SubjectInfo extends BaseEntity {


    @TableId(type = IdType.ASSIGN_ID)
    private Long subjectId;

    //题目名称
    private String subjectName;

    //题目难度
    private Integer subjectDifficult;

    //出题人名
    private String settleName;

    //题目类型 1单选 2多选 3判断 4简答
    private Integer subjectType;

    //题目分数
    private Integer subjectScore;

    //题目解析
    private String subjectParse;


    private Integer isDeleted;


}

