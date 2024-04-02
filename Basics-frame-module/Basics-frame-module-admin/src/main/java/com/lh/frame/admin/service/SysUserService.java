package com.lh.frame.admin.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lh.frame.admin.domain.vo.request.SysUserReq;
import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.admin.entity.SysUser;
import com.lh.frame.common.entity.PageResult;

/**
 * 用户信息表(SysUser)表服务接口
 *
 * @author makejava
 * @since 2024-03-31 19:35:08
 */
public interface SysUserService  {

    /**
     * 新增用户
     * @param sysUserRes
     */
    void addSysUser(SysUserReq sysUserRes);

    /**
     * 根据用户id获取用户信息

     * @param id
     * @return
     */
    SysUserResp getSysUserByUserId(Long id);

    /**
     * 查询用户信息
     * @param queryWrapper
     * @return
     */
    SysUser getSysUser(LambdaQueryWrapper<SysUser> queryWrapper);

    /**
     * 检查用户名是否存在
     * @param username
     * @return
     */
    Boolean checkUserName(String username);

    /**
     * 删除用户信息
     * @param userId
     */
    void deleteSysUserByUserId(Long userId);

    /**
     * 更新用户信息
     * @param sysUserReq
     */
    void updateSysUser(SysUserReq sysUserReq);

    /**
     *
     * @param sysUserReq
     * @return
     */
    PageResult<SysUserResp> listSysUser(SysUserReq sysUserReq);

}

