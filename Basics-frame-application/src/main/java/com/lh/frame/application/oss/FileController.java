package com.lh.frame.application.oss;

import com.lh.frame.common.entity.Result;
import com.lh.frame.common.utils.ResultBuild;
import com.lh.frame.oss.entity.dto.UploadMultipartFile;
import com.lh.frame.oss.entity.vo.request.FileReq;
import com.lh.frame.oss.entity.vo.response.FileResp;
import com.lh.frame.oss.service.FileService;
import com.lh.frame.oss.service.impl.FileServiceImpl;
import io.minio.errors.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;
import javax.annotation.Resource;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;


    /**
     * 上传文件的接口。
     *
     * @param file    需要上传的文件，由客户端通过表单上传。
     * @param fileReq 包含文件上传相关请求信息的对象，例如文件名称、目标存储路径等。
     * @return 返回一个结果对象，其中包含上传操作的状态和可能的错误信息。
     */
    @PostMapping("/up-load")
    public Result<FileResp> upload(@RequestParam("file") MultipartFile file, FileReq fileReq) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //构建文件上传对象
        UploadMultipartFile uploadMultipartFile = UploadMultipartFile
                .builder()
                .originalFilename(file.getOriginalFilename())
                .fileByte(IOUtils.toByteArray(file.getInputStream()))
                .build();
        FileResp fileResp = fileService.upLoad(uploadMultipartFile, fileReq);
        return ResultBuild.success(fileResp);
    }

    @DeleteMapping("/{fileId}")
    public Result<Boolean> clearFileById(@PathVariable("fileId") String fileId) {
        return ResultBuild.success(fileService.clearFileById(fileId));
    }



}
