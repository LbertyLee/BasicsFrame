package com.lh.frame.application.auth.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.lh.frame.auth.service.AuthServer;
import com.lh.frame.auth.vo.request.LoginReq;
import com.lh.frame.auth.vo.response.LoginResp;
import com.lh.frame.common.entity.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {

    @Resource
    private AuthServer authServer;

    @PostMapping("/login")
    public Result login(@RequestBody LoginReq loginReq){
        LoginResp loginResp =authServer.login(loginReq);
        return Result.ok(loginResp);
    }

    @GetMapping("/getRole")
    public Result getRole(){
        //获取当前登录用户的角色信息
        return Result.ok(StpUtil.getRoleList());
    }
}
