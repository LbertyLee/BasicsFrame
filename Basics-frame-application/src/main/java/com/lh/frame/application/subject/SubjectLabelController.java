package com.lh.frame.application.subject;

import com.lh.frame.common.entity.PageResult;
import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.ResultBuild;
import com.lh.frame.subject.domain.vo.request.SubjectLabelReq;
import com.lh.frame.subject.domain.vo.response.SubjectLabelResp;
import com.lh.frame.subject.service.SubjectLabelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/subject/label")
public class SubjectLabelController {

    @Resource
    private SubjectLabelService subjectLabelService;




    /**
     * 添加标签
     * @param subjectLabelReq 包含要添加的标签信息的请求体，具体包括标签的各种属性。
     * @return Result<String> 返回一个结果对象，其中包含操作结果的信息。如果添加成功，则返回成功消息。
     */
    @PostMapping("/addLabel")
    public Result<String> addLabel(@RequestBody  SubjectLabelReq subjectLabelReq) {
        // 记录接收到的标签信息日志
        log.info("SubjectLabelController.addLabel.subjectLabelReq:{}", subjectLabelReq);
        // 调用服务层方法，实际添加标签
        subjectLabelService.addLabel(subjectLabelReq);
        // 返回添加成功的消息
        return ResultBuild.success("添加标签成功");
    }
    @PostMapping("/page")
    public PageResult<SubjectLabelResp> page(@RequestBody SubjectLabelReq subjectLabelReq) {
        log.info("SubjectLabelController.page.subjectLabelReq:{}", subjectLabelReq);
        return subjectLabelService.page(subjectLabelReq);
    }



    /**
     * 删除标签
     * @param labelId 标签的ID
     * @return Result<String> 返回一个结果对象，其中包含操作结果的信息。如果删除成功，则返回成功消息。
     */
    @DeleteMapping("/deleteLabel/{labelId}")
    public Result<String> deleteLabel(@PathVariable("labelId") Long labelId) {
        log.info("SubjectLabelController.deleteLabel.labelId:{}", labelId);
        // 调用服务层方法，实际删除标签
        subjectLabelService.deleteLabel(labelId);
        // 返回删除成功的消息
        return ResultBuild.success("删除标签成功");
    }

    /**
     * 修改标签
     */
    @PutMapping("/update")
    public Result<String> updateLabel(@RequestBody SubjectLabelReq subjectLabelReq) {

        log.info("SubjectLabelController.updateLabel.subjectLabelReq:{}", subjectLabelReq);
        // 调用服务层方法，实际修改标签
        subjectLabelService.updateLabel(subjectLabelReq);
        // 返回修改成功的消息
        return ResultBuild.success("修改标签成功");
    }


}
