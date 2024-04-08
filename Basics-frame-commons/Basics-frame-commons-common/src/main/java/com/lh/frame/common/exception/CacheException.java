package com.lh.frame.common.exception;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 缓存操作异常类
 */
@Data
@AllArgsConstructor
public class CacheException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     *
     */
    private String detailMessage;

    public CacheException (String message){
        this.message=message;
    }


}
