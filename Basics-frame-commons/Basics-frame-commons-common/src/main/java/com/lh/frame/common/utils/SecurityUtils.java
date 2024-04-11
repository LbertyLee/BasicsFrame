package com.lh.frame.common.utils;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.lh.frame.common.exception.ServiceException;
import org.springframework.http.HttpStatus;

public class SecurityUtils {
    /**
     * 用户ID
     **/
    public static Long getUserId()
    {
        try
        {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return Long.valueOf(tokenInfo.loginId.toString());
        }
        catch (Exception e)
        {
            throw new RuntimeException("获取用户ID异常",e);
        }
    }

    /**
     * 获取用户账户
     **/
    public static String getUsername() {
        try {
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            String string = tokenInfo.loginId.toString();
            JSONObject jsonObject = JSONObject.parseObject(string);
            return (String) jsonObject.get("username");

        } catch (Exception e) {
            throw new ServiceException("获取用户名称异常");
        }
    }
}
