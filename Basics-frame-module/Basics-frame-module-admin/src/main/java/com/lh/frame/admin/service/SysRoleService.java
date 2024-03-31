package com.lh.frame.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lh.frame.admin.entity.SysRole;

import java.util.List;

/**
 * 角色信息表(SysRole)表服务接口
 *
 * @author makejava
 * @since 2024-03-31 21:41:28
 */
public interface SysRoleService  {

    List<String> getRolesListByUserId(String loginId);
}

