package com.lh.frame.application.backend.admin.controller;

import com.lh.frame.admin.domain.vo.request.SysUserReq;
import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.admin.service.SysUserService;
import com.lh.frame.common.entity.PageResult;
import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.ResultBuild;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 登录控制层
 *
 * @author makejava
 * @since 2024-03-31 19:35:06
 */
@Slf4j
@RestController
@RequestMapping("/backend/sysUser")
public class BackendSysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 获取用户列表
     */
    @PostMapping("/list")
    public Result<PageResult<SysUserResp>> listSysUser(@RequestBody SysUserReq sysUserReq) {
        log.info("SysUserController.listSysUser.sysUserReq{}", sysUserReq);
        return ResultBuild.success(sysUserService.listSysUser(sysUserReq));
    }

    /**
     * 新增用户
     */
    @PostMapping("/add")
    public Result addSysUser(@RequestBody @Validated SysUserReq sysUserReq) {
        log.info("SysUserController.addSysUser.sysUserReq{}", sysUserReq);
        sysUserService.addSysUser(sysUserReq);
        return ResultBuild.success("新增系统用户成功");
    }

    /**
     * 获取系统用户详情
     *
     * @param userId 用户id
     */
    @GetMapping("/{userId}")
    public Result<SysUserResp> getSysUserByUserId(@PathVariable("userId") Long userId) {
        log.info("SysUserController.getSysUserByUserId.userId{}", userId);
        SysUserResp sysUserResp = sysUserService.getSysUserByUserId(userId);
        return ResultBuild.success(sysUserResp);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/delete/{userId}")
    public Result<String> deleteSysUserByUserId(@PathVariable("userId") Long userId) {
        log.info("SysUserController.deleteSysUserByUserId.userId{}", userId);
        sysUserService.deleteSysUserByUserId(userId);
        return ResultBuild.success("删除系统用户成功");
    }

    /**
     * 更新用户
     */
    @PutMapping("/update")
    public Result<String> updateSysUser(@RequestBody SysUserReq sysUserReq) {
        log.info("SysUserController.updateSysUser.sysUserReq{}", sysUserReq);
        sysUserService.updateSysUser(sysUserReq);
        return ResultBuild.success("更新系统用户成功");
    }


}

