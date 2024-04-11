package com.lh.frame.subject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.frame.subject.dao.SubjectRadioDao;
import com.lh.frame.subject.entity.SubjectRadio;
import com.lh.frame.subject.service.SubjectRadioService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 单选题信息表(FrameSubjectRadio)表服务实现类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:08:27
 */
@Service
public class SubjectRadioServiceImpl extends ServiceImpl<SubjectRadioDao, SubjectRadio> implements SubjectRadioService{

    @Resource
    private SubjectRadioDao subjectRadioDao;

    @Override
    public List<SubjectRadio> queryBySubjectId(Long subjectId) {
        return subjectRadioDao.selectList(new LambdaQueryWrapper<SubjectRadio>().eq(SubjectRadio::getSubjectId, subjectId));
    }
}

