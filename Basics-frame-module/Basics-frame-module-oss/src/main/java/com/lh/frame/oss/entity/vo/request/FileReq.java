package com.lh.frame.oss.entity.vo.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class FileReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**后缀名*/
    private String suffix;

    /**文件名*/
    private String fileName;

    /**文件夹*/
    private String folder;

    /**访问路径*/
    private String pathUrl;

    /**存储标识（不同的存储标识参考FileConstant）,现支持 aliyunoss、qiniu*/
    private String storeFlag;

    /**文件的key*/
    private String objectKey;

    //每个分片大小（byte）
    private Long chunkSize;
    //分片数量
    private Integer chunkNum;

    /**存储空间名称*/
    private String bucketName;

    /**base64图片*/
    private String base64Image;

    /**分片上传文件Id*/
    private String uploadId;

    /**是否自动生成文件存储目录,如果在storeFilename指定了目录，此值设置为false*/
    private Boolean autoCatalog;

    /**md5值*/
    private String md5;

    /**分片上传分片信息*/
    private List<String> partETags;

    /**上传状态*/
    private String status;

    /**企业号*/
    private String companyNo;

    /**历史上传:0 历史有此文件 1、历史无此文件*/
    private String isHistory;
}
