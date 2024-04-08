package com.lh.frame.common.utils;

import com.lh.frame.common.entity.Result;
import com.lh.frame.common.enums.IBaseEnum;
import com.lh.frame.common.enums.ResultCodeEnum;

public class ResultBuild {
    public static <T> Result <T> build(IBaseEnum baseEnum, T data) {
        Result<T> result = new Result<T>();
        result.setCode(baseEnum.getCode());
        result.setMessage(baseEnum.getMsg());
        result.setSuccess(baseEnum.getSuccess());
        result.setData(data);
        return result;
    }

    public static <T> Result <T> build(IBaseEnum baseEnum) {
        Result<T> result = new Result<T>();
        result.setCode(baseEnum.getCode());
        result.setMessage(baseEnum.getMsg());
        result.setSuccess(baseEnum.getSuccess());
        return result;
    }

    /**
     * 公共成功响应方法
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data) {
        return build(ResultCodeEnum.SUCCESS, data);
    }
    public static <T> Result<T> success() {
        return build(ResultCodeEnum.SUCCESS);
    }

    /**
     * 公共失败响应方法
     */
    public static <T> Result<T> failed(T data) {
        return build(ResultCodeEnum.FAIL, data);
    }


}
