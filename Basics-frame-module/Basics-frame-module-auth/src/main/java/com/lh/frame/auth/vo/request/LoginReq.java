package com.lh.frame.auth.vo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginReq {

    //用户账号
    @NotBlank(message = "用户账号不能为空")
    private String username;

    //密码
    @NotBlank(message = "用户密码不能为空")
    private String password;

}
