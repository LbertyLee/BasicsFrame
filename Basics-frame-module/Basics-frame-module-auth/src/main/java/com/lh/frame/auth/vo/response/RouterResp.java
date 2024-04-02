package com.lh.frame.auth.vo.response;

import lombok.Data;

import java.util.List;

@Data
public class RouterResp {

    private Long id;

    /**路由名称*/
    private String name;

    /**路由地址*/
    private String path;

    /**组件路径**/
    private String component;

    /**权限标识*/
    private String perms;

    /**子路由*/
    private List<RouterResp> children;

    private Meta meta;

    @Data
    static
    class Meta {
        private String icon;
        private  String link;
        private  String title;
    }
}
