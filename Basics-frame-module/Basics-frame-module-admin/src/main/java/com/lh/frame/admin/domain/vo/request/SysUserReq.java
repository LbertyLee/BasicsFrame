package com.lh.frame.admin.domain.vo.request;

import com.lh.frame.common.entity.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SysUserReq extends PageInfo implements Serializable {

    //用户ID
    private  String userId;

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

    //备注
    private String remark;
}
