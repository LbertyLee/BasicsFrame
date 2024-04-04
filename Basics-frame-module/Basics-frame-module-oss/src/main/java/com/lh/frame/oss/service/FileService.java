package com.lh.frame.oss.service;

import com.lh.frame.oss.entity.dto.UploadMultipartFile;
import com.lh.frame.oss.entity.vo.request.FileReq;
import com.lh.frame.oss.entity.vo.response.FileResp;
import io.minio.errors.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface FileService {


    /**
     * 小文件上传的接口方法。
     *
     * @param uploadMultipartFile 一个包含待上传文件信息的对象，通常包括文件内容、文件名等。
     * @param fileReq 包含上传文件请求参数的对象，例如文件类型、目标存储路径等。
     * @return 返回一个文件响应对象，里面包含了上传结果的信息，例如文件的存储路径、上传状态等。
     */
    FileResp upLoad(UploadMultipartFile uploadMultipartFile, FileReq fileReq) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;



}
