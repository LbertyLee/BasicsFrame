package com.lh.frame.auth.vo.response;

import com.lh.frame.admin.entity.SysUser;
import lombok.Data;

import java.util.Date;

@Data
public class LoginResp {
    private SysUser sysUser;
    private String token;

}
