package com.lh.frame.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.admin.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户信息表(SysUser)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-31 19:35:07
 */
@Mapper
public interface SysUserDao  extends BaseMapper<SysUser> {

}

