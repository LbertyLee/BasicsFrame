package com.lh.frame.application.admin.controller;

import com.lh.frame.admin.domain.vo.request.SysUserReq;
import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.admin.service.SysUserService;
import com.lh.frame.common.entity.PageResult;
import com.lh.frame.common.entity.Result;
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
@RequestMapping("sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 获取用户列表
     */
    @PostMapping("/list")
    public Result<PageResult<SysUserResp>> listSysUser(@RequestBody SysUserReq sysUserReq) {
        log.info("SysUserController.listSysUser.sysUserReq{}", sysUserReq);
        return Result.ok(sysUserService.listSysUser(sysUserReq));
    }

    /**
     * 新增用户
     */
    @PostMapping("/add")
    public Result addSysUser(@RequestBody @Validated SysUserReq sysUserReq) {
        log.info("SysUserController.addSysUser.sysUserReq{}", sysUserReq);
        sysUserService.addSysUser(sysUserReq);
        return Result.ok("新增系统用户成功");
    }

    /**
     * 获取系统用户详情
     *
     * @param userId 用户id
     */
    @GetMapping("/{userId}")
    public Result getSysUserByUserId(@PathVariable("userId") Long userId) {
        log.info("SysUserController.getSysUserByUserId.userId{}", userId);
        SysUserResp sysUserResp = sysUserService.getSysUserByUserId(userId);
        return Result.ok(sysUserResp);
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/delete/{userId}")
    public Result deleteSysUserByUserId(@PathVariable("userId") Long userId) {
        log.info("SysUserController.deleteSysUserByUserId.userId{}", userId);
        sysUserService.deleteSysUserByUserId(userId);
        return Result.ok("删除系统用户成功");
    }

    /**
     * 更新用户
     */
    @PutMapping("/update")
    public Result updateSysUser(@RequestBody SysUserReq sysUserReq) {
        log.info("SysUserController.updateSysUser.sysUserReq{}", sysUserReq);
        sysUserService.updateSysUser(sysUserReq);
        return Result.ok("更新系统用户成功");
    }


}

