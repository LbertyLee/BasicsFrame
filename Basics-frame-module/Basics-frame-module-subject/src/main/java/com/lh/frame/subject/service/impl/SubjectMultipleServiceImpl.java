package com.lh.frame.subject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.frame.subject.dao.SubjectMultipleDao;
import com.lh.frame.subject.entity.SubjectMultiple;
import com.lh.frame.subject.service.SubjectMultipleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 多选题信息表(FrameSubjectMultiple)表服务实现类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:09:01
 */
@Service
public class SubjectMultipleServiceImpl extends ServiceImpl<SubjectMultipleDao, SubjectMultiple> implements SubjectMultipleService {

    @Resource
    private SubjectMultipleDao subjectMultipleDao;
    @Override
    public List<SubjectMultiple> queryBySubjectId(Long subjectId) {
       return subjectMultipleDao.selectList(new LambdaQueryWrapper<SubjectMultiple>()
               .eq(SubjectMultiple::getSubjectId,subjectId));
    }
}

