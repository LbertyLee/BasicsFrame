package com.lh.frame.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCodeEnum implements IBaseEnum {

    SUCCESS(200, "成功", true),
    FAIL(500, "失败", false);

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
