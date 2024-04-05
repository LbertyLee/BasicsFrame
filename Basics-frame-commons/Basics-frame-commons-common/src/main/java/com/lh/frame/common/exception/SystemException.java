package com.lh.frame.common.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 身份验证异常
 *
 * @  lihong
 * @date 2023/04/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SystemException extends RuntimeException
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

    public SystemException(String message){
        this.message=message;
    }


}