package com.lh.frame.common.utils;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
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
            throw new ServiceException("获取用户ID异常");
        }
    }
}
