package com.lh.frame.auth.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.admin.entity.SysMenu;
import com.lh.frame.admin.entity.SysUser;
import com.lh.frame.admin.service.SysMenuService;
import com.lh.frame.admin.service.SysUserService;
import com.lh.frame.auth.service.AuthServer;
import com.lh.frame.auth.vo.request.LoginReq;
import com.lh.frame.auth.vo.response.LoginResp;
import com.lh.frame.auth.vo.response.RouterResp;
import com.lh.frame.common.exception.AuthException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("AuthServer")
public class AuthServerImpl implements AuthServer {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;

    private final static String SALT="sajgdasyf";

    @Override
    public LoginResp login(LoginReq loginReq) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName,loginReq.getUsername())
                        .eq(SysUser::getPassword,SaSecureUtil.md5BySalt(loginReq.getPassword(), SALT));
        SysUser sysUser=sysUserService.getSysUser(queryWrapper);
        if(StrUtil.isEmptyIfStr(sysUser)){
            throw new AuthException("用户名或密码错误");
        }
        // 登录
        StpUtil.login(sysUser.getUserId());
        // 第2步，获取 Token  相关参数
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        LoginResp loginResp = new LoginResp();
        loginResp.setToken(tokenInfo.tokenValue);
        loginResp.setSysUser(sysUser);
        return loginResp;
    }

    @Override
    public SysUserResp getUserInfo(String string) {
        return sysUserService.getSysUserByUserId(Long.valueOf(string));
    }


    @Override
    public List<RouterResp> getRoleByLoginId(Long loginId) {
        List<SysMenu> sysMenuList=sysMenuService.selectMenuByLoginId(loginId);
//        sysMenuList.stream().map(sysMenu ->)
        return null;
    }
}
