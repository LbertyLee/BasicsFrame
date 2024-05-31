package com.lh.frame.es.basic;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class EsSourceData implements Serializable {

    /**
     * 文档id
     */
    private String docId;

    /**
     * 文档数据
     */
    private Map<String, Object> data;

}
