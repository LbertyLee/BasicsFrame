package com.lh.frame.common.enums;

import com.lh.frame.common.constant.basic.ResultCodeConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCodeEnum implements IBaseEnum {

    SUCCESS(ResultCodeConstant.SUCCESS, ResultCodeConstant.SUCCESS_MESSAGE, ResultCodeConstant.SUCCESS_BOOLEAN),
    FAIL(ResultCodeConstant.FAIL, ResultCodeConstant.FAIL_MESSAGE, ResultCodeConstant.FAIL_BOOLEAN);

    public Integer code;

    public String msg;

    private Boolean success;


    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public Boolean getSuccess() {
        return success;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
