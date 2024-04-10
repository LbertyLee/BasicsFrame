package com.lh.frame.subject.domain.vo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lh.frame.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 题目信息表(FrameSubjectCollect)表实体类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:06:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("frame_subject_collect")
public class SubjectCollect extends BaseEntity {

    //题目id
    private Long subjectId;

    //收藏人id
    private String collectUserId;

    //收藏状态 1收藏 0不收藏
    private Integer status;

    private Integer isDeleted;


}

