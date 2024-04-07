package com.lh.frame.rabbitmq.entity;

import cn.hutool.core.date.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Description：Rabbit消息封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MqMessage implements Serializable {

    private static final long serialVersionUID = -8140693840257585779L;
    /**消息id**/
    private Long id;

    /**标题**/
    private String title;

    /**消息内容**/
    private String content;

    /**业务类型**/
    private String messageType;

    /**产生时间**/
    private DateTime produceTime;

    /**发送人**/
    private String sender;
    /**
     * ket
     */
    private String key;

}
