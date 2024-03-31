package com.lh.frame.auth.vo.request;

import lombok.Data;

@Data
public class LoginReq {

    //用户账号
    private String userName;

    //密码
    private String password;

}
