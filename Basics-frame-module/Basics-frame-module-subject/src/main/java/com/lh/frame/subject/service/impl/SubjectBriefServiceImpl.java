package com.lh.frame.subject.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lh.frame.subject.dao.SubjectBriefDao;
import com.lh.frame.subject.dao.SubjectMultipleDao;
import com.lh.frame.subject.entity.SubjectBrief;
import com.lh.frame.subject.entity.SubjectMultiple;
import com.lh.frame.subject.service.SubjectBriefService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 简答题(FrameSubjectBrief)表服务实现类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:07:27
 */
@Service
public class SubjectBriefServiceImpl  extends ServiceImpl<SubjectBriefDao, SubjectBrief> implements SubjectBriefService {

}

