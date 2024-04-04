package com.lh.frame.admin.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.lh.frame.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 角色信息表(SysRole)表实体类
 *
 * @author makejava
 * @since 2024-03-31 21:41:28
 */
@SuppressWarnings("serial")
@Data
@TableName("frame_sys_role")
@NoArgsConstructor
@AllArgsConstructor
public class SysRole extends BaseEntity {
    //角色ID
    private Long roleId;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;
    //角色状态（0正常 1停用）
    private String status;
    //删除标志（0代表存在 2代表删除）
    private String delFlag;
    //备注
    private String remark;


}

