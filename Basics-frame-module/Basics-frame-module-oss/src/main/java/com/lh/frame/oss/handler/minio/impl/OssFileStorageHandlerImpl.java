package com.lh.frame.oss.handler.minio.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.lh.frame.oss.entity.File;
import com.lh.frame.oss.handler.AbsFileStorageHandler;
import com.lh.frame.oss.handler.FileStorageHandler;
import com.lh.frame.oss.handler.minio.properties.OssMinioConfigProperties;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 * @ClassName OssFileStorageHandlerImpl.java
 * @Description minio文件上传
 */
@Slf4j
@Service("minioOssFileStorageHandler")
@EnableConfigurationProperties(OssMinioConfigProperties.class)
public class OssFileStorageHandlerImpl extends AbsFileStorageHandler implements FileStorageHandler {

    @Resource
    private MinioClient minioClient;

    @Autowired
    OssMinioConfigProperties ossMinioConfigProperties;



    /**
     * 上传文件到指定的存储桶中。
     *
     * @param suffix      文件后缀名，用于指定文件的类型。
     * @param filename    要上传的文件名称。
     * @param bucketName  存储桶的名称，指定文件将上传到哪个存储桶中。
     * @param inputStream 文件的输入流，用于读取要上传的文件内容。
     * @return 返回上传结果的字符串表示。当前实现中，总是返回null。
     */
    @Override
    public String uploadFile(String suffix, String filename, String bucketName, boolean autoCatalog,
                             InputStream inputStream) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        // 上传文件到存储桶
        minioClient.putObject
                (PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(filename)
                        .contentType(suffix)
                        .stream(inputStream, -1, 5242889L).build());
        // 生成预签名URL
        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName).object(filename).build();
        return minioClient.getPresignedObjectUrl(args);
    }


    /***
     * @description 分片上传-初始化分片请求
     * @param suffix 文件后缀
     * @param filename 文件名称-可以指定存储空间下的目录，例如：a/b/c/filename
     * @param bucketName 存储空间的名称
     * @param autoCatalog 是否自动生成文件存储目录,如果在filename指定了目录，此值设置为false
     * @return uploadId 文件上传id
     */
    @Override
    public File initiateMultipartUpload(String suffix, String filename, String bucketName, boolean autoCatalog) {
        // 初始化分片上传

        // 返回上传ID和存储桶名称
        return null;
    }


    @Override
    public String uploadPart(String upLoadId, String filename, int partNumber, long partSize, String bucketName, InputStream inputStream) {
        return null;
    }

    @Override
    public String completeMultipartUpload(String upLoadId, List<String> partETags, String filename, String bucketName) {
        return null;
    }

    @Override
    public InputStream downloadFile(String bucketName, String pathUrl) throws IOException {
        return null;
    }

    @Override
    @SneakyThrows
    public void delete(String bucketName, String filename) {
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket(bucketName).object(filename).build()
        );
    }

    @Override
    public void deleteBatch(String bucketName, List<String> pathUrls) {

    }

    @Override
    public String getFileContent(String bucketName, String pathUrl) throws IOException {
        return null;
    }
}
