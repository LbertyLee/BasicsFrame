package com.lh.frame.subject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.frame.common.constant.basic.SystemConstants;
import com.lh.frame.common.entity.PageResult;
import com.lh.frame.subject.convert.SubjectLabelEntityConverter;
import com.lh.frame.subject.dao.SubjectLabelDao;
import com.lh.frame.subject.domain.vo.request.SubjectLabelReq;
import com.lh.frame.subject.entity.SubjectLabel;
import com.lh.frame.subject.domain.vo.response.SubjectLabelResp;
import com.lh.frame.subject.service.SubjectLabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    /**
     * 添加主题标签。
     *
     * @param subjectLabelReq 包含主题标签信息的请求对象，用于指定要添加的主题标签的详细信息。
     *
     * 本方法通过将请求对象转换为实体对象，然后将该实体对象插入数据库来实现标签的添加。
     * 如果在添加标签的过程中发生异常，将抛出一个运行时异常。
     */
    @Override
    public void addLabel(SubjectLabelReq subjectLabelReq) {
        try{
            // 将请求对象转换为实体对象
            SubjectLabel subjectLabel = SubjectLabelEntityConverter.INSTANCE.convertReqToEntity(subjectLabelReq);
            // 向数据库插入主题标签实体
            subjectLabelDao.insert(subjectLabel);
        }catch (Exception e){
            // 当添加标签过程中出现异常时，抛出运行时异常
            throw new RuntimeException("添加标签失败");
        }
    }

    /**
     * 删除标签
     * @param labelId 标签的唯一标识符
     * 方法尝试更新标签的状态为已删除，如果操作失败会抛出运行时异常
     */
    @Override
    public void deleteLabel(Long labelId) {
        try {
            // 通过标签ID更新标签状态为已删除
            subjectLabelDao.updateById(new SubjectLabel().setLabelId(labelId)
                    .setIsDeleted(SystemConstants.IS_DELETE));
        }catch (Exception e){
            // 如果删除过程中出现异常，则抛出运行时异常
            throw new RuntimeException("删除标签失败");
        }
    }


    /**
     * 更新主题标签信息。
     *
     * @param subjectLabelReq 包含更新后的标签信息请求对象。
     *                         该对象应包含用于更新标签的所有必要字段。
     * @throws RuntimeException 如果更新过程中发生任何异常，则抛出运行时异常。
     */
    @Override
    public void updateLabel(SubjectLabelReq subjectLabelReq) {
        try {
            // 将请求对象转换为实体对象，然后通过DAO层更新数据库中的标签信息
            subjectLabelDao.updateById(SubjectLabelEntityConverter.INSTANCE.convertReqToEntity(subjectLabelReq));
        }catch (Exception e){
            // 捕获异常并抛出运行时异常，以通知调用者更新失败
            throw new RuntimeException("更新标签失败");
        }
    }

    @Override
    public List<SubjectLabelResp> getLabelList(Long categoryId) {
        // 根据分类ID和删除状态查询标签信息
        List<SubjectLabel> subjectLabels = subjectLabelDao.selectList(
                new LambdaUpdateWrapper<SubjectLabel>()
                        .eq(SubjectLabel::getCategoryId, categoryId)
                        .eq(SubjectLabel::getIsDeleted, SystemConstants.NO_DELETE));
        // 将查询到的标签实体列表转换为请求参数列表
        return SubjectLabelEntityConverter.INSTANCE.convertEntityListToRespList(subjectLabels);
    }

    /**
     * 分页查询主题标签信息
     *
     * @param subjectLabelReq 包含分页参数和查询条件的主题标签请求对象
     * @return 返回主题标签的分页结果，包含当前页的数据列表、总条数、页大小、总页数等信息
     */
    @Override
    public PageResult<SubjectLabelResp> page(SubjectLabelReq subjectLabelReq) {
        // 初始化分页对象
        Page<SubjectLabel> page = new Page<>();
        page.setSize(subjectLabelReq.getPageSize()).setPages(subjectLabelReq.getPageNo());

        // 构造查询条件，只查询未被删除的主题标签
        LambdaQueryWrapper<SubjectLabel> eq = new LambdaQueryWrapper<SubjectLabel>().eq(SubjectLabel::getIsDeleted, SystemConstants.NO_DELETE);

        // 执行分页查询
        Page<SubjectLabel> subjectLabelPage = subjectLabelDao.selectPage(page, eq);
        List<SubjectLabel> records = subjectLabelPage.getRecords();

        // 将查询结果转换为响应对象列表
        List<SubjectLabelResp> subjectLabelRespList = SubjectLabelEntityConverter.INSTANCE.convertEntityListToRespList(records);

        // 构造并返回分页结果
        PageResult<SubjectLabelResp> pageResult = new PageResult<>();
        pageResult.setResult(subjectLabelRespList);
        pageResult.setTotal(subjectLabelPage.getTotal());
        pageResult.setPageSize( subjectLabelPage.getSize());
        pageResult.setTotalPages( subjectLabelPage.getPages());
        // 设置分页参数
        return pageResult;
    }

    @Override
    public List<SubjectLabel> queryList(List<Long> labelIdList) {
        if (labelIdList != null && labelIdList.size() > 0){
            return subjectLabelDao.selectList(new LambdaQueryWrapper<SubjectLabel>().in(SubjectLabel::getLabelId, labelIdList));
        }

        return null;
    }

}

