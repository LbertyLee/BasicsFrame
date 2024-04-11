package com.lh.frame.subject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.frame.subject.dao.SubjectJudgeDao;
import com.lh.frame.subject.entity.SubjectJudge;
import com.lh.frame.subject.service.SubjectJudgeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 判断题(FrameSubjectJudge)表服务实现类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:08:12
 */
@Service
public class SubjectJudgeServiceImpl  extends ServiceImpl<SubjectJudgeDao, SubjectJudge> implements SubjectJudgeService {

    @Resource
    private SubjectJudgeDao subjectJudgeDao;
    @Override
    public List<SubjectJudge> queryBySubjectId(Long subjectId) {
        return subjectJudgeDao.selectList(new LambdaQueryWrapper<SubjectJudge>().eq(SubjectJudge::getSubjectId, subjectId));
    }

}

