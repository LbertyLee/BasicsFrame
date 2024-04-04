package com.lh.frame.admin.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户信息表(SysUser)表实体类
 *
 * @author makejava
 * @since 2024-03-31 19:35:08
 */
@Data
@Accessors(chain = true)
@TableName("frame_sys_user")
@AllArgsConstructor
@NoArgsConstructor
public class SysUser  {
    //索引
    private Long id;
    //用户ID
    @TableId(type = IdType.ASSIGN_ID)
    private Long userId;
    //用户账号
    private String userName;
    //用户昵称
    private String nickName;
    //用户类型（00系统用户）
    private String userType;
    //用户邮箱
    private String email;
    //手机号码
    private String phonenumber;
    //用户性别（0男 1女 2未知）
    private String sex;
    //头像地址
    private String avatar;
    //密码
    private String password;
    //帐号状态（0正常 1停用）
    private String status;
    //删除标志（0代表存在 2代表删除）
    private String delFlag;
    //最后登录IP
    private String loginIp;

    //创建者
    @TableField(value = "create_by",fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    //创建时间
    private LocalDateTime createTime;

    //更新者
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    //更新时间
    private LocalDateTime updateTime;
    //备注
    private String remark;

}


