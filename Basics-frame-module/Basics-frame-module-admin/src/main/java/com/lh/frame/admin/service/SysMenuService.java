package com.lh.frame.admin.service;


import com.lh.frame.admin.entity.SysMenu;

import java.util.List;

/**
 * 菜单权限表(FranmeSysMenu)表服务接口
 *
 * @author makejava
 * @since 2024-04-01 13:20:10
 */
public interface SysMenuService  {

    List<SysMenu> selectMenuByLoginId(Long loginId);
}

