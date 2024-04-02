package com.lh.frame.auth.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.admin.entity.SysMenu;
import com.lh.frame.admin.entity.SysUser;
import com.lh.frame.admin.service.SysMenuService;
import com.lh.frame.admin.service.SysUserService;
import com.lh.frame.auth.service.AuthServer;
import com.lh.frame.auth.vo.request.LoginReq;
import com.lh.frame.auth.vo.response.LoginResp;
import com.lh.frame.auth.vo.response.Meta;
import com.lh.frame.auth.vo.response.RouterResp;
import com.lh.frame.common.exception.AuthException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("AuthServer")
public class AuthServerImpl implements AuthServer {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;

    private final static String SALT = "sajgdasyf";

    @Override
    public LoginResp login(LoginReq loginReq) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, loginReq.getUsername()).eq(SysUser::getPassword, SaSecureUtil.md5BySalt(loginReq.getPassword(), SALT));
        SysUser sysUser = sysUserService.getSysUser(queryWrapper);
        if (StrUtil.isEmptyIfStr(sysUser)) {
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

    /**
     * 获取用户信息
     * @param string
     * @return
     */
    @Override
    public SysUserResp getUserInfo(String string) {
        return sysUserService.getSysUserByUserId(Long.valueOf(string));
    }


    /**
     * 获取登录用户的路由
     * @param loginId
     * @return
     */
    @Override
    public List<RouterResp> getRoleByLoginId(Long loginId) {
        List<SysMenu> sysMenuList = sysMenuService.selectMenuByLoginId(loginId);
        List<SysMenu> list = convertToTree(sysMenuList);
        List<RouterResp> routerResps = sysMenuToRouterList(list);
        return  routerResps;
    }

    /**
     * 生成用户路由
     * @param sysMenuList
     * @return
     */
    public static List<RouterResp> sysMenuToRouterList(List<SysMenu> sysMenuList) {
        if (sysMenuList == null) {
            return new ArrayList<>();
        }
        return sysMenuList.stream()
                .map(AuthServerImpl::convertToRouterResp)
                .collect(Collectors.toList());
    }


    private static RouterResp convertToRouterResp(SysMenu sysMenu) {
        RouterResp routerResp = new RouterResp();
        routerResp.setId(sysMenu.getMenuId());
        routerResp.setName(sysMenu.getMenuName());
        routerResp.setPath(sysMenu.getPath());
        routerResp.setComponent(sysMenu.getComponent());
        routerResp.setPerms(sysMenu.getPerms());
        routerResp.setMeta(new Meta().setIcon(sysMenu.getIcon()).setTitle(sysMenu.getMenuName()));
        List<SysMenu> children = sysMenu.getChild();
        if (!children.isEmpty()) {
            routerResp.setChildren(sysMenuToRouterList(children));
        }
        return routerResp;
    }


    /**
     * 构建树形菜单
     * @param sysMenuList
     * @return
     */
    public  List<SysMenu> convertToTree(List<SysMenu> sysMenuList) {
        Map<Long, List<SysMenu>> menuMap = sysMenuList.stream()
                .collect(Collectors.groupingBy(SysMenu::getParentId));
        return buildTree(menuMap, 0L);
    }

    /**
     * 递归构建树形菜单
     * @param menuMap
     * @param parentId
     * @return
     */
    private  List<SysMenu> buildTree(Map<Long, List<SysMenu>> menuMap, Long parentId) {
        List<SysMenu> children = menuMap.getOrDefault(parentId, new ArrayList<>());
        return children.stream()
                .map(menu -> {
                    SysMenu node = new SysMenu();
                    BeanUtil.copyProperties(menu, node);
                    node.setChild(buildTree(menuMap, menu.getMenuId()));
                    return node;
                })
                .collect(Collectors.toList());
    }
}
