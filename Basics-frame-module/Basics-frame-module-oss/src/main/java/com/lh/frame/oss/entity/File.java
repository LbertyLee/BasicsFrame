package com.lh.frame.oss.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件表(FrameFile)表实体类
 *
 * @author makejava
 * @since 2024-04-04 17:34:10
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("frame_file")
@SuppressWarnings("serial")
public class File extends Model<File> {
    //主键
    private Long id;
    //业务ID
    private Long businessId;
    //业务类型
    private String businessType;
    //后缀名
    private String suffix;
    //文件名
    private String fileName;
    //访问路径
    private String pathUrl;
    //存储源标识

    private String storeFlag;
    //存储空间名称
    private String bucketName;
    //唯一上传id
    private String uploadId;
    //md5值
    private String md5;
    //状态
    private String status;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;
    //创建人ID
    private Long createBy;
    //修改人ID
    private Long updateBy;
    //是否有效
    private String dataState;
    //商户ID【系统内部识别使用】
    private String companyNo;

    }

