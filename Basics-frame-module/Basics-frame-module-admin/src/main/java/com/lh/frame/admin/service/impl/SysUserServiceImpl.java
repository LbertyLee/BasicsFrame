package com.lh.frame.admin.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lh.frame.admin.convert.SysUserEntityToRespConverter;
import com.lh.frame.admin.dao.SysUserDao;
import com.lh.frame.admin.domain.vo.request.SysUserReq;
import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.admin.entity.SysUser;
import com.lh.frame.admin.service.SysUserService;
import com.lh.frame.common.entity.PageInfo;
import com.lh.frame.common.entity.PageResult;
import com.lh.frame.common.exception.SystemException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户信息表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2024-03-31 19:35:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserDao sysUserDao;

    private final static String SALT = "sajgdasyf";

    @Override
    public void addSysUser(SysUserReq sysUserReq) {
        SysUser sysUser = SysUserEntityToRespConverter.INSTANCE.convertReqToEntity(sysUserReq);
        sysUser.setPassword(SaSecureUtil.md5BySalt(sysUserReq.getPassword(), SALT));
        //检查用户是否存在
        Boolean aBoolean = checkUserName(sysUser.getUserName());
        if (!aBoolean) {
            throw new SystemException("用户名已存在");
        }
        try {
            sysUserDao.insert(sysUser);
        }catch (Exception e){
            throw new SystemException("添加用户失败，请联系管理员");
        }

    }

    @Override
    public SysUserResp getSysUserByUserId(Long id) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserId, id);
        SysUser sysUsers = sysUserDao.selectOne(queryWrapper);
        return SysUserEntityToRespConverter.INSTANCE.convertEntityToResp(sysUsers);
    }

    @Override
    public SysUser getSysUser(LambdaQueryWrapper<SysUser> queryWrapper) {
        return sysUserDao.selectOne(queryWrapper);
    }

    @Override
    public Boolean checkUserName(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUserName, username);
        return StringUtils.isEmpty(sysUserDao.selectOne(queryWrapper));
    }

    @Override
    public void deleteSysUserByUserId(Long userId) {
        try {
            sysUserDao.updateById(new SysUser().setUserId(userId).setDelFlag("1"));
        }catch (Exception e){
            throw new SystemException("删除用户失败，请联系管理员");
        }

    }

    @Override
    public void updateSysUser(SysUserReq sysUserReq) {
        SysUser sysUser = SysUserEntityToRespConverter.INSTANCE.convertReqToEntity(sysUserReq);
        LambdaUpdateWrapper<SysUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysUser::getUserId, sysUserReq.getUserId());
        try {
            sysUserDao.update(sysUser,updateWrapper);
        }catch (Exception e){
            throw new SystemException("更新用户失败，请联系管理员");
        }

    }

    @Override
    public PageResult<SysUserResp> listSysUser(SysUserReq sysUserReq) {
        Page<SysUser> page = new Page<>();
        page.setSize(sysUserReq.getPageSize()).setPages(sysUserReq.getPageNo());
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        //TODO 条件查询
        queryWrapper.eq(SysUser::getDelFlag,sysUserReq.getDelFlag());
        Page<SysUser> selectPage = sysUserDao.selectPage(page, queryWrapper);
        System.out.println(selectPage);
        List<SysUserResp> sysUserRespList = SysUserEntityToRespConverter
                .INSTANCE.convertListEntityToResp(selectPage.getRecords());
        PageResult<SysUserResp> pageResult = new PageResult<>();
        pageResult.setResult(sysUserRespList);
        pageResult.setTotal((int) selectPage.getTotal());
        pageResult.setPageSize((int) selectPage.getSize());
        pageResult.setTotalPages((int) selectPage.getPages());
        return pageResult;

    }


}

