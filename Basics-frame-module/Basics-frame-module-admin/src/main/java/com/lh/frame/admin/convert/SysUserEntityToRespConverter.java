package com.lh.frame.admin.convert;

import com.lh.frame.admin.domain.vo.response.SysUserResp;
import com.lh.frame.admin.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper

public interface SysUserEntityToRespConverter {
    SysUserEntityToRespConverter INSTANCE = Mappers.getMapper(SysUserEntityToRespConverter.class);

    SysUserResp convertEntityToResp(SysUser sysUser);

}
