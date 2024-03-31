package com.lh.frame.admin.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lh.frame.admin.domain.vo.request.SysUserReq;
import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.admin.entity.SysUser;

/**
 * 用户信息表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2024-03-31 19:35:08
 */
public interface SysUserService  {

    SysUserResp addSysUser(SysUserReq sysUserRes);

    SysUserResp getSysUserById(Long id);

    SysUser getSysUser(LambdaQueryWrapper<SysUser> queryWrapper);
}

