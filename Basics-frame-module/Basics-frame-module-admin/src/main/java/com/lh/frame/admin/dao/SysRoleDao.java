package com.lh.frame.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.admin.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.w3c.dom.ls.LSException;

import java.util.List;

/**
 * 角色信息表(SysRole)表数据库访问层
 *
 * @author makejava
 * @since 2024-03-31 21:41:28
 */
public interface SysRoleDao extends BaseMapper<SysRole> {

    List<String> getRolesListByUserId(@Param("userId") String userId);
}

