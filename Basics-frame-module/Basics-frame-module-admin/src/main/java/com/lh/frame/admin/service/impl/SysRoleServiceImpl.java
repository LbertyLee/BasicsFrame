package com.lh.frame.admin.service.impl;


import com.lh.frame.admin.dao.SysRoleDao;
import com.lh.frame.admin.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 角色信息表(SysRole)表服务实现类
 *
 * @author makejava
 * @since 2024-03-31 21:41:28
 */
@Service("sysRoleService")
public class SysRoleServiceImpl  implements SysRoleService {

    @Resource
    private SysRoleDao sysRoleDao;

    @Override
    public List<String> getRolesListByUserId(String loginId) {
        return sysRoleDao.getRolesListByUserId(loginId);
    }
}

