package com.lh.frame.auth.service;

import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.auth.vo.request.LoginReq;
import com.lh.frame.auth.vo.response.LoginResp;
import com.lh.frame.auth.vo.response.RouterResp;

import java.util.List;

public interface AuthServer {

    /**
     * 登录
     * @param loginReq
     * @return
     */
    LoginResp login(LoginReq loginReq);

    SysUserResp getUserInfo(String string);

    List<RouterResp> getRoleByLoginId(Long loginId);

}
