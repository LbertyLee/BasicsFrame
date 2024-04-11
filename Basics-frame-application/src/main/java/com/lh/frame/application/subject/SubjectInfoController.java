package com.lh.frame.application.subject;

import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.ResultBuild;
import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.service.SubjectInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/subject/info")
public class SubjectInfoController {

    @Resource
    private SubjectInfoService subjectInfoService;

    @PostMapping("/add")
    public Result<String> add(@RequestBody SubjectInfoReq subjectInfoReq) {
        // 调用service方法
        subjectInfoService.addSubjectInfo(subjectInfoReq);
        return ResultBuild.success("添加成功");
    }
}
