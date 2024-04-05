package com.lh.frame.oss.adapter.impl;


import com.lh.frame.common.constant.file.FileConstant;
import com.lh.frame.common.utils.EmptyUtil;
import com.lh.frame.common.utils.RegisterBeanHandler;
import com.lh.frame.oss.adapter.FileStorageAdapter;
import com.lh.frame.oss.entity.vo.request.FileReq;
import com.lh.frame.oss.handler.FileStorageHandler;
import io.minio.errors.*;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service("fileStorageAdapter")
public class FileStorageAdapterImpl implements FileStorageAdapter {

    @Resource
    RegisterBeanHandler registerBeanHandler;

    private static Map<String,String> fileStorageHandlers =new HashMap<>();

    static {
        fileStorageHandlers.put(FileConstant.MINIO_OSS,"minioOssFileStorageHandler");
        fileStorageHandlers.put(FileConstant.QINIU_KODO,"kodoFileStorageHandler");
    }


    @Override
    public String uploadFile(FileReq fileReq, InputStream inputStream) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String fileStorageHandlerString = EmptyUtil.isNullOrEmpty(fileReq.getStoreFlag())?
                fileStorageHandlers.get(FileConstant.MINIO_OSS):fileStorageHandlers.get(fileReq.getStoreFlag());
        FileStorageHandler fileStorageHandler = registerBeanHandler.getBean(
                fileStorageHandlerString, FileStorageHandler.class);
        return fileStorageHandler.uploadFile(fileReq.getSuffix(),
                fileReq.getFileName(), fileReq.getBucketName(), false, inputStream);
    }

    @Override
    public void delete(String storeFlag, String bucketName, String pathUrl) {
        String fileStorageHandlerString = EmptyUtil.isNullOrEmpty(storeFlag)?
                fileStorageHandlers.get(FileConstant.MINIO_OSS):fileStorageHandlers.get(storeFlag);
        FileStorageHandler fileStorageHandler = registerBeanHandler.getBean(
                fileStorageHandlerString, FileStorageHandler.class);
        fileStorageHandler.delete(bucketName, pathUrl);
    }
}
