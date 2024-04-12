package com.lh.frame.subject.constant;

import com.lh.frame.common.constant.basic.CacheConstant;

public class SubjectLikedConstant extends CacheConstant {
    /**题目点赞 redis key*/
    public static final String SUBJECT_LIKED_KEY = "subject.liked";

    /**题目收藏 redis key*/
    public static final String SUBJECT_COLLECT_KEY = "subject.collect";

    /**题目点赞数量*/
    public static final String SUBJECT_LIKED_COUNT_KEY = "subject.liked.count";
    /**题目点赞数量*/
    public static final String SUBJECT_COLLECT_COUNT_KEY = "subject.collect.count";


    /**题目点赞详情*/
    public static final String SUBJECT_LIKED_DETAIL_KEY = "subject.liked.detail";
    /**题目收藏详情*/
    public static final String SUBJECT_COLLECT_DETAIL_KEY = "subject.collect.detail";

    /** 题目发布排行榜 redis key*/
    public static final String RANK_KEY = "subject_rank";
}
