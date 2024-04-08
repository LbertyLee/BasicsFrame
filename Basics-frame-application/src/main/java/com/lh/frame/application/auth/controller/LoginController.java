package com.lh.frame.application.auth.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.auth.service.AuthServer;
import com.lh.frame.auth.vo.request.LoginReq;
import com.lh.frame.auth.vo.response.LoginResp;
import com.lh.frame.auth.vo.response.RouterResp;
import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.ResultBuild;
import com.lh.frame.common.utils.SecurityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class LoginController {

    @Resource
    private AuthServer authServer;

    @PostMapping("/login")
    public Result<LoginResp> login(@RequestBody @Validated LoginReq loginReq){
        LoginResp loginResp =authServer.login(loginReq);
        return ResultBuild.success(loginResp);
    }

    @GetMapping("/getInfo")
    public  Result<SysUserResp> getInfo(){
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        Object loginId = tokenInfo.loginId;
        return ResultBuild.success(authServer.getUserInfo(loginId.toString()));
    }

    @GetMapping("/getRole")
    public Result<List<String>> getRole(){
        //获取当前登录用户的角色信息
        return ResultBuild.success(StpUtil.getRoleList());
    }
    @GetMapping("/getRoleByLoginId")
    public Result<List<RouterResp>> getRoleByLoginId(){
        Long loginId = SecurityUtils.getUserId();
        return ResultBuild.success(authServer.getRoleByLoginId(loginId));
    }

    @PostMapping("/logout")
    public Result logout(){
        //退出登录
        StpUtil.logout();
        return ResultBuild.success("退出成功");
    }
}
