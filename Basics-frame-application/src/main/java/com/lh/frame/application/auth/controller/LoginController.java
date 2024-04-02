package com.lh.frame.application.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.lh.frame.auth.service.AuthServer;
import com.lh.frame.auth.vo.request.LoginReq;
import com.lh.frame.auth.vo.response.LoginResp;
import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.SecurityUtils;
import org.springframework.validation.annotation.Validated;
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
    public Result login(@RequestBody @Validated LoginReq loginReq){
        LoginResp loginResp =authServer.login(loginReq);
        return Result.ok(loginResp);
    }

    @GetMapping("/getInfo")
    public  Result getInfo(){
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        Object loginId = tokenInfo.loginId;
        return Result.ok(authServer.getUserInfo(loginId.toString()));
    }

    @GetMapping("/getRole")
    public Result getRole(){
        //获取当前登录用户的角色信息

        return Result.ok(StpUtil.getRoleList());
    }
    @GetMapping("/getRoleByLoginId")
    public Result getRoleByLoginId(){
        Long loginId = SecurityUtils.getUserId();
        return Result.ok(authServer.getRoleByLoginId(loginId));
    }

    @PostMapping("/logout")
    public Result logout(){
        //退出登录
        StpUtil.logout();
        return Result.ok();
    }
}
