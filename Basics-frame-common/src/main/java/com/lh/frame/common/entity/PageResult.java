package com.lh.frame.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页返回实体
 *
 * @author: lihong
 * @date: 2023/10/5
 */
@Data
public class PageResult<T> implements Serializable {

    private Integer pageNo = 1;

    private Integer pageSize = 20;

    private Integer total = 0;

    private Integer totalPages = 0;

    private List<T> result = Collections.emptyList();

    public void setRecords(List<T> result) {
        this.result = result;
        if (result != null && result.size() > 0) {
            setTotal(result.size());
        }
    }
}
