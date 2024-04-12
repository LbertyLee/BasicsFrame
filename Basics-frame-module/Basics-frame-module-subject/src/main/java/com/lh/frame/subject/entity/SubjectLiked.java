package com.lh.frame.subject.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lh.frame.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 题目信息表(FrameSubjectLiked)表实体类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:05:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("frame_subject_liked")
public class SubjectLiked  {

    //题目id
    private Long subjectId;

    //点赞人id
    private String likeUserId;

    //点赞状态 1点赞 0不点赞
    private Integer status;


    private Integer isDeleted;





}

