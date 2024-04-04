package com.lh.frame.oss.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class UploadMultipartFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**文件名称*/
    public String originalFilename;
    /**文件数组*/
    public byte[] fileByte;
}
