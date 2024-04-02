package com.lh.frame.auth.convert;

import com.lh.frame.admin.entity.SysMenu;
import com.lh.frame.auth.vo.response.RouterResp;
import com.lh.frame.common.entity.TreeNode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SysMenuToRouterConvert {
    SysMenuToRouterConvert INSTANCE = Mappers.getMapper(SysMenuToRouterConvert.class);


}
