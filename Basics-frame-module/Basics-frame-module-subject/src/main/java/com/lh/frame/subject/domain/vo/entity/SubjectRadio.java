package com.lh.frame.subject.domain.vo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lh.frame.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 单选题信息表(FrameSubjectRadio)表实体类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:08:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("frame_subject_radio")
public class SubjectRadio extends BaseEntity {

    //题目id
    private Long subjectId;

    //a,b,c,d
    private Integer optionType;

    //选项内容
    private String optionContent;

    //是否正确
    private Integer isCorrect;

    private Integer isDeleted;


}

