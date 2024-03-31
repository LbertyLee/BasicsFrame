package com.lh.frame.auth.service;

import com.lh.frame.auth.vo.request.LoginReq;
import com.lh.frame.auth.vo.response.LoginResp;

public interface AuthServer {

    /**
     * 登录
     * @param loginReq
     * @return
     */
    LoginResp login(LoginReq loginReq);

}
