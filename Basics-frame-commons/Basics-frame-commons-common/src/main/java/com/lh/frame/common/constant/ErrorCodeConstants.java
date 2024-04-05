package com.lh.frame.common.constant;


/**
 * 错误代码常量类
 */
public class ErrorCodeConstants {
    // 成功状态
    public static final String OK = "OK";
    // 验证有效token
    public static final String AUTH_INVALID_TOKEN = "AUTH_INVALID_TOKEN";
    // 认证异常
    public static final String AUTH_ERROR = "AUTH_ERROR";
    // 业务错误
    public static final String BUSINESS_ERROR = "BUSINESS_ERROR";
    // 参数错误
    public static final String PARAMETER_VALIDATION_ERROR = "PARAMETER_VALIDATION_ERROR";
    // 超过上传文件上限异常
    public static final String MAX_UPLOAD_FILE_ERROR = "MAX_UPLOAD_FILE_ERROR";

    // 超过上传文件上限异常
    public static final String UPLOAD_FILE_ERROR = "UPLOAD_FILE_ERROR";

    // 请求格式错误异常
    public static final String CONTENT_TYPE_ERROR = "CONTENT_TYPE_ERROR";

    // 二维码不存在
    public static final String QRCODE_NOT_EXIST = "QRCODE_NOT_EXIST";

    // 二维码已过期
    public static final String QRCODE_EXPIRED = "QRCODE_EXPIRED";

    // 二维码已被使用
    public static final String QRCODE_USED = "QRCODE_USED";

    // 二维码还未被扫描
    public static final String QRCODE_NOT_SCAN = "QRCODE_NOT_SCAN";

    // 短信超过发送限制
    public static final String SMS_SEND_LIMIT = "SMS_SEND_LIMIT";
    // 需要验证码才可发送短信
    public static final String SMS_NEED_CAPTCHA_VERIFY = "SMS_NEED_CAPTCHA_VERIFY";
    // 短信验证码验证失败
    public static final String SMS_VERIFY_FAILED = "SMS_VERIFY_FAILED";
    // 短信验证码已被使用
    public static final String SMS_VERIFY_USED = "SMS_VERIFY_USED";
    // 短信验证码达到尝试上限
    public static final String SMS_VERIFY_TRY_LIMIT = "SMS_VERIFY_TRY_LIMIT";
    // 账号或密码错误
    public static final String INVALID_USERNAME_OR_PASSWORD = "INVALID_USERNAME_OR_PASSWORD";
    // 账号还未设置密码
    public static final String ACCOUNT_NOT_SET_PASSWORD = "ACCOUNT_NOT_SET_PASSWORD";

}
