package com.lh.frame.subject.service.impl;

import com.lh.frame.common.exception.SystemException;
import com.lh.frame.subject.convert.SubjectInfoEntityConverter;
import com.lh.frame.subject.dao.SubjectInfoDao;
import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.entity.SubjectInfo;
import com.lh.frame.subject.handler.SubjectTypeHandler;
import com.lh.frame.subject.handler.SubjectTypeHandlerFactory;
import com.lh.frame.subject.service.SubjectInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 题目信息表(FrameSubjectInfo)表服务实现类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:07:59
 */
@Service
public class SubjectInfoServiceImpl  implements SubjectInfoService {

    @Resource
    private SubjectInfoDao subjectInfoDao;

    @Resource
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    @Override
    @Transactional
    public void addSubjectInfo(SubjectInfoReq subjectInfoReq) {
        SubjectInfo subjectInfo = SubjectInfoEntityConverter.INSTANCE.convertReqToEntity(subjectInfoReq);
        try{
            //插入题目基础信息
            //TODO 根据当前用户设置作者
//            subjectInfo.setSettleName()
            subjectInfoDao.insert(subjectInfo);
            subjectInfoReq.setSubjectId(subjectInfo.getSubjectId());
            SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(subjectInfo.getSubjectType());
            //根据题目类型插入不同的表中
            handler.add(subjectInfoReq);
            //批量插入题目，分类，标签关联
        }catch (Exception e){
            throw new SystemException("添加题目信息失败");
        }

    }
}

