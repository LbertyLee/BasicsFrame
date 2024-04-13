package com.lh.frame.application.backend.subject;

import com.lh.frame.common.entity.PageResult;
import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.ResultBuild;
import com.lh.frame.subject.domain.vo.request.SubjectInfoReq;
import com.lh.frame.subject.domain.vo.response.SubjectInfoResp;
import com.lh.frame.subject.service.SubjectInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/subject/info")
public class BackendSubjectInfoController {

    @Resource
    private SubjectInfoService subjectInfoService;


    /**
     * 添加主题信息
     * @param subjectInfoReq 主题信息请求对象，包含要添加的主题信息
     * @return 返回一个结果对象，表示添加操作的成功或失败，并附带消息
     */
    @PostMapping("/add")
    public Result<String> add(@RequestBody SubjectInfoReq subjectInfoReq) {
        // 调用service方法添加主题信息
        subjectInfoService.addSubjectInfo(subjectInfoReq);
        return ResultBuild.success("添加成功");
    }

    /**
     * 分页查询主题信息
     * @param subjectInfoReq 主题信息查询请求对象，包含查询条件
     * @return 返回一个分页结果对象，包含查询到的主题信息
     */
    @PostMapping("/query/page")
    public PageResult<SubjectInfoResp> queryPage(@RequestBody SubjectInfoReq subjectInfoReq) {
        // 调用service方法进行分页查询
        return subjectInfoService.queryPage(subjectInfoReq);
    }

    /**
     * 根据主题ID查询主题信息
     * @param subjectsId 要查询的主题ID
     * @return 返回一个结果对象，包含查询到的主题信息
     */
    @GetMapping("/query/{subjectsId}")
    public Result<SubjectInfoResp> queryById(@PathVariable("subjectsId") Long subjectsId) {
        // 调用service方法根据ID查询主题信息
        return ResultBuild.success(subjectInfoService.queryById(subjectsId));
    }
}
