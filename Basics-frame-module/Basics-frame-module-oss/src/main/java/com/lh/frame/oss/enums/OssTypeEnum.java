package com.lh.frame.oss.enums;

import java.util.Objects;

public enum OssTypeEnum {
    MINIO("minio","minio"),
    ALIYUN("aliyun","aliyun");

    public String name;

    public String type;

    OssTypeEnum(String name, String type){
        this.name = name;
        this.type = type;
    }

    public static OssTypeEnum getByType(String type){
        for(OssTypeEnum resultCodeEnum : OssTypeEnum.values()){
            if(Objects.equals(resultCodeEnum.type, type)){
                return resultCodeEnum;
            }
        }
        return null;
    }
}
