package com.lh.frame.common.entity;


import com.lh.frame.common.enums.IBaseEnum;
import com.lh.frame.common.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 结果集封装类，用于统一返回接口调用结果。
 * @param <T> 返回的数据类型
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

    // 标记接口调用是否成功
    private Boolean success;

    // 结果状态码，用于详细描述接口调用结果
    private Integer code;

    // 结果消息，提供关于接口调用结果的额外信息
    private String message;

    // 接口调用成功时返回的数据
    private T data;
}

