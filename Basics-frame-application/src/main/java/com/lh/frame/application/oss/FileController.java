package com.lh.frame.application.oss;

import com.lh.frame.common.entity.Result;
import com.lh.frame.oss.service.FileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/file")
public class FileController {
    @Resource
    private FileService fileService;

    /**
     * 测试获取所有的存储空间
     * @return {@link String}
     */
    @GetMapping("/testGetAllBuckets")
    public Result testGetAllBuckets(){
        List<String> allBucket = fileService.getAllBucket();
        HashMap<String, List<String>> allBucketMap = new HashMap<>();
        allBucketMap.put("allBucket",allBucket);
        return Result.ok(allBucketMap);
    }

    /**
     * 获取文件访问路径
     * @param bucketName 存储空间名称
     * @param objectName 文件名称
     * @return {@link String}
     */
    @GetMapping("/getUrl")
    public Result getUrl(String bucketName, String objectName){
        return Result.ok(fileService.getUrl(bucketName, objectName));
    }

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public Result upload(MultipartFile uploadFile, String bucket, String objectName){
        String url = fileService.uploadFile(uploadFile, bucket, objectName);
        return Result.ok(url);
    }

    /**
     * 下载文件
     */
    @RequestMapping("/download")
    public Result download(String bucket, String objectName){
        InputStream url = fileService.downLoad(bucket, objectName);
        return Result.ok(url);
    }
}
