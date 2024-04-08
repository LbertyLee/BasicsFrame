package com.lh.frame.common.anno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
public enum test {

    /*** 成功*/
    SUCCESS(200,"成功"),
    /*** 失败*/
    FAIL(500,"失败");

    private Integer code;
    private String msg;
    //编码
    public Integer getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }


    public static void main(String[] args) {

        System.out.println(test.SUCCESS.getCode());
        System.out.println(test.SUCCESS.getMsg());
    }

}

