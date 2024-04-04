package com.lh.frame.oss.adapter;


import com.lh.frame.oss.entity.vo.request.FileReq;
import io.minio.errors.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * @ClassName FileStorageAdapter.java
 * @Description 文件存储适配处理
 */
public interface FileStorageAdapter {


    String uploadFile(FileReq fileReq, InputStream inputStream) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;


}
