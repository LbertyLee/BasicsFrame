package com.lh.frame.application.subject;

import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.ResultBuild;
import com.lh.frame.subject.domain.vo.request.SubjectCategoryReq;
import com.lh.frame.subject.service.SubjectCategoryService;
import lombok.extern.slf4j.Slf4j;
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
    public Result<String> addCategory(@RequestBody SubjectCategoryReq subjectCategoryReq){
        log.info("SubjectCategoryController.addCategory.subjectCategoryReq:{}",subjectCategoryReq);
        subjectCategoryService.addCategory(subjectCategoryReq);
        return ResultBuild.success("添加题目分类成功");
    }


    /**
     * 查询题目分类大类
     */
    @GetMapping("/query/parent")
    public Result<List<SubjectCategoryReq>> queryParent(){
        return ResultBuild.success(subjectCategoryService.queryParent());
    }

    /**
     * 删除指定的题目分类
     *
     * @param categoryId 分类ID，通过URL路径变量传递
     */
    @DeleteMapping("/delete/{categoryId}")
    public Result<String> deleteCategory( @PathVariable Long categoryId){
        // 记录日志，打印删除操作的类别ID
        log.info("SubjectCategoryController.deleteCategory.categoryId:{}",categoryId);
        // 调用服务层方法，执行删除操作
        subjectCategoryService.deleteCategory(categoryId);
        // 返回成功结果，包含删除成功的消息
        return ResultBuild.success("删除题目分类成功");
    }

    /**
     * 根据岗位ID查询分类列表。
     *
     */
    @GetMapping("/query/list/{id}")
    public Result<List<SubjectCategoryReq>> queryCategoryList(@PathVariable Long id){
        log.info("SubjectCategoryController.queryCategoryList.id:{}",id);
        return ResultBuild.success(subjectCategoryService.queryCategoryList(id));
    }




}

