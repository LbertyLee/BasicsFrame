package com.lh.frame.common.constant.basic;

/**
 * @ClassName CacheConstant.java
 * @Description 基础缓存父类
 */
public class CacheConstant {

    //默认redis等待时间
    public static final int REDIS_WAIT_TIME = 5;

    //默认redis自动释放时间
    public static final int REDIS_LEASETIME = 4;

    private static final String CACHE_KEY_SEPARATOR = ".";


}
