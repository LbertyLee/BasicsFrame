package com.lh.frame.subject.constant;

import com.lh.frame.common.constant.basic.CacheConstant;

public class SubjectConstant extends CacheConstant {

    /** 岗位标识*/
    public static final Integer SUBJECT_POSITION_ID = 1;


    //缓存父包
    public static final String PREFIX= "subject:";

    //分类包
    public static final String CATEGORY= PREFIX+"category";

    //缓存父包
    public static final String BASIC= PREFIX+"basic";

    //分布式锁前缀
    public static final String LOCK_PREFIX = PREFIX+"lock:";

    //page分页
    public static final String PAGE= PREFIX+"page";

    //list下拉框
    public static final String LIST= PREFIX+"list";

}
