package com.lh.frame.subject.handler;

import com.lh.frame.common.constant.basic.SystemConstants;
import com.lh.frame.common.exception.SystemException;
import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.domain.vo.response.SubjectInfoResp;
import com.lh.frame.subject.entity.SubjectJudge;
import com.lh.frame.subject.enums.SubjectInfoTypeEnum;
import com.lh.frame.subject.service.SubjectJudgeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 判断题
 * @author lihong
 */
@Component
public class JudgeTypeHandler implements SubjectTypeHandler{
    @Resource // 注解自动注入SubjectJudgeService
    private SubjectJudgeService subjectJudgeService;

    /**
     * 获取处理器类型
     * @return 返回 SubjectInfoTypeEnum.JUDGE，代表判断题类型
     */
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.JUDGE;
    }

    /**
     * 添加判断题
     * @param subjectInfoReq 包含题目信息的请求对象
     * @throws SystemException 如果添加失败抛出系统异常
     */
    @Override
    public void add(SubjectInfoReq subjectInfoReq) {
        try {
            // 创建一个 SubjectJudge 实例并设置题目ID和正确选项
            SubjectJudge subjectJudge = new SubjectJudge().setSubjectId(subjectInfoReq.getSubjectId())
                    .setIsCorrect(subjectInfoReq.getOptionList().get(0).getIsCorrect());
            // 保存题目判断信息
            subjectJudgeService.save(subjectJudge);
        }catch (Exception e){
            throw new SystemException("添加判断题失败");
        }
    }

    /**
     * 查询题目信息
     * @param subjectId 题目ID
     * @return 返回题目信息响应对象，当前实现返回null
     */
    @Override
    public SubjectInfoResp query(Long subjectId) {
        return null;
    }
}

