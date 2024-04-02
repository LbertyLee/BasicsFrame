package com.lh.frame.oss.entity;

import lombok.Data;

/**
 * 文件类
 *
 */
@Data
public class FileInfo {

    /**
     * 文件名称
     */
    private String fileName;

    /**
     *目录标志
     */
    private Boolean directoryFlag;

    private String etag;


}
