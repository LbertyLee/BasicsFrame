package com.lh.frame.admin.convert;

import com.lh.frame.admin.domain.vo.request.SysUserReq;
import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.admin.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper

public interface SysUserEntityConverter {
    SysUserEntityConverter INSTANCE = Mappers.getMapper(SysUserEntityConverter.class);

    SysUserResp convertEntityToResp(SysUser sysUser);

    SysUser convertReqToEntity(SysUserReq sysUserReq);

    List<SysUserResp> convertListEntityToResp(List<SysUser> records);
}
