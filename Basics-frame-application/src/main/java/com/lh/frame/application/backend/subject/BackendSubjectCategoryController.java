package com.lh.frame.application.backend.subject;

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
@RequestMapping("/backend/subject/category")
public class BackendSubjectCategoryController {


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
     * 查询树形分类结构
     */
    @GetMapping("/query/tree")
    public Result<List<SubjectCategoryResp>> queryTree(){
        return ResultBuild.success(subjectCategoryService.queryTree());
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


    /**
     * 更新题目分类
     *
     * @param subjectCategoryReq 包含更新信息的题目分类请求对象，通过RequestBody接收前端传来的JSON数据。
     *                           该对象需经过Validated注解验证，确保数据的合法性。
     * @return 返回操作结果，如果更新成功，则返回一个成功状态和消息。
     */
    @PutMapping("/update")
    public Result<String> updateCategory(@RequestBody @Validated SubjectCategoryReq subjectCategoryReq){
        log.info("SubjectCategoryController.updateCategory.subjectCategoryReq:{}", subjectCategoryReq);
        // 调用服务层方法，执行分类更新操作
        subjectCategoryService.updateCategory(subjectCategoryReq);
        // 返回成功结果，包含更新成功的消息
        return ResultBuild.success("更新题目分类成功");
    }




}

