package com.lh.frame.admin.service.impl;

import com.lh.frame.admin.dao.SysMenuDao;
import com.lh.frame.admin.entity.SysMenu;
import com.lh.frame.admin.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单权限表(FranmeSysMenu)表服务实现类
 *
 * @author makejava
 * @since 2024-04-01 13:20:10
 */
@Service("SysMenuService")
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SysMenuDao sysMenuDao;
    @Override
    public List<SysMenu> selectMenuByLoginId(Long loginId) {
        return sysMenuDao.selectListByUserId(loginId);

    }
}

