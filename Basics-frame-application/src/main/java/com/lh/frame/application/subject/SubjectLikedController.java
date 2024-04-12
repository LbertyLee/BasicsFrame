package com.lh.frame.application.subject;

import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.ResultBuild;
import com.lh.frame.common.utils.SecurityUtils;
import com.lh.frame.subject.domain.vo.request.SubjectLikedReq;
import com.lh.frame.subject.service.SubjectLikedService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/subject/liked")
public class SubjectLikedController {

    @Resource
    private SubjectLikedService subjectLikedService;

    /**
     * 添加点赞信息
     *
     * @param subjectLikedReq 包含点赞主体信息的请求对象，@RequestBody注解表明该参数是从请求体中获取的
     * @return 返回操作结果，成功则返回点赞成功的消息
     */
    @PostMapping("/add")
    public Result<String> add(@RequestBody SubjectLikedReq subjectLikedReq) {
        // 设置点赞用户的ID
        subjectLikedReq.setLikeUserId(String.valueOf(SecurityUtils.getUserId()));
        // 调用服务层方法，添加点赞信息
        subjectLikedService.addSubjectLiked(subjectLikedReq);
        // 返回成功结果
        return ResultBuild.success("操作成功");
    }
    @PostMapping("/query/page")
    public Result<Object> queryPage(@RequestBody SubjectLikedReq subjectLikedReq) {
        subjectLikedReq.setLikeUserId(String.valueOf(SecurityUtils.getUserId()));
        return ResultBuild.success(subjectLikedService.querySubjectLikedPage(subjectLikedReq));
    }

    @GetMapping("/test")
    public Result<String> test() {
        subjectLikedService.syncLiked();
        return ResultBuild.success();
    }
}
