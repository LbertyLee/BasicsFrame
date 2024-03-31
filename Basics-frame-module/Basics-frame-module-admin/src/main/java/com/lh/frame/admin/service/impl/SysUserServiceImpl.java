package com.lh.frame.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lh.frame.admin.convert.SysUserEntityToRespConverter;
import com.lh.frame.admin.dao.SysUserDao;
import com.lh.frame.admin.domain.vo.request.SysUserReq;
import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.admin.entity.SysUser;
import com.lh.frame.admin.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2024-03-31 19:35:09
 */
@Service("sysUserService")
public class SysUserServiceImpl  implements SysUserService  {

    @Resource
    private SysUserDao sysUserDao;

    @Override
    public SysUserResp addSysUser(SysUserReq sysUserRes) {
        SysUser sysUsers = sysUserDao.selectOne(null);
        return SysUserEntityToRespConverter.INSTANCE.convertEntityToResp(sysUsers);
    }

    @Override
    public SysUserResp getSysUserById(Long id) {
        SysUser sysUsers = sysUserDao.selectOne(null);
        return SysUserEntityToRespConverter.INSTANCE.convertEntityToResp(sysUsers);
    }

    @Override
    public SysUser getSysUser(LambdaQueryWrapper<SysUser> queryWrapper) {
        return  sysUserDao.selectOne(queryWrapper);
    }
}

