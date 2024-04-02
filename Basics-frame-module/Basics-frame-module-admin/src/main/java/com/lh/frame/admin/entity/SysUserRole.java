package com.lh.frame.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户和角色关联表(FrameSysUserRole)表实体类
 *
 * @author makejava
 * @since 2024-04-01 13:30:28
 */
@SuppressWarnings("serial")
@Data
@TableName("frame_sys_user_role")
public class SysUserRole {
    //索引
    private Integer id;
    //用户ID
    private Long userId;
    //角色ID
    private Long roleId;


    }

