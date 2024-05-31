package com.lh.frame.kafka.domain;

import lombok.Data;

@Data
public class KafkaBaseEntity {

    // 主键ID
    private String id;
    // 类型
    private String type;
    // 状态
    private String status;
    // 租户ID
    private String tenantId;
    // 租户代码
    private String tenantCode;
    // 租户名称
    private String tenantName;
}
