package com.lh.frame.common.entity;

import lombok.Builder;
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

    private Long pageNo = 1L;

    private Long pageSize = 20L;

    private Long total = 0L;

    private Long totalPages = 0L;

    private List<T> result = Collections.emptyList();

}
