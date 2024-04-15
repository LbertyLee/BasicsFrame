package com.lh.frame.application.front.subject;

import com.google.common.base.Preconditions;
import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.ResultBuild;
import com.lh.frame.subject.domain.vo.response.SubjectCategoryResp;
import com.lh.frame.subject.service.SubjectCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/front/subject/category")
public class FrontSubjectCategoryController {


    @Resource
    private SubjectCategoryService subjectCategoryService;


    /**
     * 查询特定主题分类列表。
     *
     * @param id 主题ID，用于查询与之相关的分类列表。
     * @return 返回一个结果对象，其中包含查询到的分类列表。如果查询成功，结果对象的状态码为200，否则根据实际情况可能返回其他状态码。
     */
    @GetMapping("/query/list/{id}")
    public Result<List<SubjectCategoryResp>> queryCategoryList(@PathVariable  @Validated Long id){
        if (log.isInfoEnabled()){
            log.info("SubjectCategoryController.queryCategoryList.id:{}",id); // 记录请求的ID信息
        }
        Preconditions.checkNotNull(id, "分类id不能为空");
        return ResultBuild.success(subjectCategoryService.queryCategoryList(id)); // 调用服务层方法查询分类列表，并包装成结果对象返回
    }



    /**
     * 查询父级主题分类
     * 本接口不需要接收任何参数，调用后会返回一个包含所有父级主题分类的列表。
     *
     * @return Result<List<SubjectCategoryReq>> 返回一个结果对象，其中包含查询到的父级主题分类列表。
     */
    @GetMapping("/query/parent")
    public Result<List<SubjectCategoryResp>> queryParent(){
        // 调用服务层方法，查询父级主题分类，并将结果封装成成功的结果对象返回
        return ResultBuild.success(subjectCategoryService.queryParent());
    }







}

