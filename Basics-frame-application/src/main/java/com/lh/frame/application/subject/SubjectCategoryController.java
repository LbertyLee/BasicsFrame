package com.lh.frame.application.subject;

import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.ResultBuild;
import com.lh.frame.subject.domain.vo.request.SubjectCategoryReq;
import com.lh.frame.subject.domain.vo.response.SubjectCategoryResp;
import com.lh.frame.subject.service.SubjectCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/subject/category")
public class SubjectCategoryController {


    @Resource
    private SubjectCategoryService subjectCategoryService;

    /**
     * 添加题目分类
     *
     * @param subjectCategoryReq 包含题目分类信息的请求体
     */
    @PostMapping("/add")
    public Result<String> addCategory(@RequestBody @Validated  SubjectCategoryReq subjectCategoryReq){
        log.info("SubjectCategoryController.addCategory.subjectCategoryReq:{}",subjectCategoryReq);
        subjectCategoryService.addCategory(subjectCategoryReq);
        return ResultBuild.success("添加题目分类成功");
    }


    /**
     * 查询特定主题分类列表。
     *
     * @param id 主题ID，用于查询与之相关的分类列表。
     * @return 返回一个结果对象，其中包含查询到的分类列表。如果查询成功，结果对象的状态码为200，否则根据实际情况可能返回其他状态码。
     */
    @GetMapping("/query/list/{id}")
    public Result<List<SubjectCategoryResp>> queryCategoryList(@PathVariable  @Validated Long id){
        log.info("SubjectCategoryController.queryCategoryList.id:{}",id); // 记录请求的ID信息
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

    /**
     * 删除指定的题目分类
     * @param categoryId 分类ID，通过URL路径变量传递
     */
    @DeleteMapping("/delete/{categoryId}")
    public Result<String> deleteCategory( @PathVariable  @Validated Long categoryId){
        // 记录日志，打印删除操作的类别ID
        log.info("SubjectCategoryController.deleteCategory.categoryId:{}",categoryId);
        // 调用服务层方法，执行删除操作
        subjectCategoryService.deleteCategory(categoryId);
        // 返回成功结果，包含删除成功的消息
        return ResultBuild.success("删除题目分类成功");
    }

    @PutMapping("/update")
    public Result<String> updateCategory(@RequestBody  @Validated SubjectCategoryReq subjectCategoryReq){
        log.info("SubjectCategoryController.updateCategory.subjectCategoryReq:{}",subjectCategoryReq);
        subjectCategoryService.updateCategory(subjectCategoryReq);
        return ResultBuild.success("更新题目分类成功");
    }





}

