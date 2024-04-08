package com.lh.frame.common.constant.mq;


public class RabbitConstant {

    /*** ==================交换机名称。===============*/
    public static final String EXCHANGE_NAME = "basics-exchange";

    /*** ==================声明主题的名称。===============*/
    public static final String TOPIC_NAME = "basics-topic";


    /*** ==================声明队列的名称。===============*/
    public static final String DIRECT_QUEUE_NAME1 = "direct_queue_name1";
    public static final String DIRECT_QUEUE_NAME2 = "direct_queue_name2";

    public static final String  QUEUE_NAME= "basics-queue";
    public static final String TOPIC_QUEUE_NAME = "basics-topic-queue";
    public static final String TOPIC_QUEUE_NAME_WTO = "basics-topic-queue-wto";

    /**=================声明队列key的名称=====================*/
    public static final String DIRECT_KEY_ORANGE = "orange";
    public static final String DIRECT_KEY_BLACK = "black";
    public static final String DIRECT_KEY_GREEN = "green";


    /*** ==================文件清理相关===============*/
    public static final String FILE_DELAYED_MESSAGE	 = "file_delayed_message";
    public static final String FILE_CLEAR_QUEUE = "file_queue";
    public static final String FILE_CLEAR_KEY = "file-key";




}
