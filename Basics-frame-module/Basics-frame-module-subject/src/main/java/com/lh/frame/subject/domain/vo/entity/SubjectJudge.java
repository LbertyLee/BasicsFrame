package com.lh.frame.subject.domain.vo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lh.frame.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 判断题(FrameSubjectJudge)表实体类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:08:12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("frame_subject_judge")
public class SubjectJudge extends BaseEntity {

    //题目id
    private Long subjectId;

    //是否正确
    private Integer isCorrect;


    private Integer isDeleted;


}

