package com.lh.frame.application.admin.controller;

import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.admin.service.SysUserService;
import com.lh.frame.common.entity.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用户信息表(SysUser)表控制层
 *
 * @author makejava
 * @since 2024-03-31 19:35:06
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;


    /**
     * 获取系统用户详情
     *
     * @param id 用户id
     * @return
     */
    @GetMapping("/{id}")
    public Result getSysUser(@PathVariable("id") Long id) {
        SysUserResp sysUserResp= sysUserService.getSysUserById(id);
        return Result.ok(sysUserResp);
    }



}

