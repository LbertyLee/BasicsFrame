package com.lh.frame.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lh.frame.admin.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表(FranmeSysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-01 13:20:10
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenu> {

    List<SysMenu> selectListByUserId(@Param("userId") Long userId);
}

