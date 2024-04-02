package com.lh.frame.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色和菜单关联表(FranmeSysRoleMenu)表实体类
 *
 * @author makejava
 * @since 2024-04-01 13:25:46
 */
@SuppressWarnings("serial")
@Data
@TableName("frame_sys_role_menu")
public class SysRoleMenu {
    //索引
    private Long id;
    //角色ID
    private Long roleId;
    //菜单ID
    private Long menuId;

}

