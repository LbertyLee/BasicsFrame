package com.lh.frame.common.exception;

import lombok.Data;

@Data

public class ServiceException extends RuntimeException{
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

    public ServiceException(String message){
        this.message=message;
    }

}
