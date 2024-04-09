package com.lh.frame.subject.service.impl;

import com.lh.frame.common.constant.basic.SystemConstants;
import com.lh.frame.subject.convert.SubjectLabelEntityConverter;
import com.lh.frame.subject.dao.SubjectLabelDao;
import com.lh.frame.subject.domain.vo.request.SubjectLabelReq;
import com.lh.frame.subject.entity.SubjectLabel;
import com.lh.frame.subject.service.SubjectLabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 题目标签表(FrameSubjectLabel)表服务实现类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:04:29
 */
@Service
public class SubjectLabelServiceImpl  implements SubjectLabelService {

    @Resource
    private SubjectLabelDao subjectLabelDao;

    @Override
    public void addLabel(SubjectLabelReq subjectLabelReq) {
        try{
            SubjectLabel subjectLabel = SubjectLabelEntityConverter.INSTANCE.convertReqToEntity(subjectLabelReq);
            subjectLabelDao.insert(subjectLabel);
        }catch (Exception e){
            throw new RuntimeException("添加标签失败");
        }
    }

    @Override
    public void deleteLabel(Long labelId) {
        try {
            subjectLabelDao.updateById(new SubjectLabel().setLabelId(labelId)
                    .setIsDeleted(Integer.valueOf(SystemConstants.IS_DELETE)));
        }catch (Exception e){
            throw new RuntimeException("删除标签失败");
        }
    }
}

