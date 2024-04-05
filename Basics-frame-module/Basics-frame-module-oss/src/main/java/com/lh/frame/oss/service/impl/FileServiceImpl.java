package com.lh.frame.oss.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.lh.frame.common.exception.FileException;
import com.lh.frame.common.utils.EmptyUtil;
import com.lh.frame.oss.adapter.FileStorageAdapter;
import com.lh.frame.oss.convert.FileEntityConverter;
import com.lh.frame.oss.dao.FileDao;
import com.lh.frame.oss.entity.File;
import com.lh.frame.oss.entity.dto.UploadMultipartFile;
import com.lh.frame.oss.entity.vo.request.FileReq;
import com.lh.frame.oss.entity.vo.response.FileResp;
import com.lh.frame.oss.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;


/**
 * 文件存储service

 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${file.delay.time}")
    Integer fileDelayTime =1000;
    @Resource
    private FileStorageAdapter fileStorageAdapter;
    @Resource
    private IdentifierGenerator identifierGenerator;

    @Resource
    private  FileDao fileDao;

    @Override
    @Transactional
    public FileResp upLoad(UploadMultipartFile multipartFile, FileReq fileReq){
        ByteArrayInputStream byteArrayInputStream =new ByteArrayInputStream(multipartFile.getFileByte());
        try {
            String filename = identifierGenerator.nextId(fileReq)+"-"+multipartFile.getOriginalFilename();;
            fileReq.setFileName(fileReq.getFileName()+"/"+filename);
            //文件后缀名
            String suffix = fileReq.getFileName().substring(fileReq.getFileName().lastIndexOf("."));
            fileReq.setSuffix(suffix);
            //调用简单上传
            String pathUrl = fileStorageAdapter.uploadFile(fileReq, byteArrayInputStream);
            //保存数据库
            File file = FileEntityConverter.INSTANCE.convertReqToEntity(fileReq);
            file.setPathUrl(pathUrl);
            this.fileDao.insert(file);
            FileResp fileResp = FileEntityConverter.INSTANCE.convertEntityToResp(file);
            fileResp.setId(file.getId());
            //TODO 延期删除
            return  fileResp;
        }catch (Exception e){
            log.error("文件上传异常");
            throw new FileException("文件上传异常");
        }finally {
            try {
                byteArrayInputStream.close();
            } catch (Exception e) {
                log.error("文件上传操作失败");
            }
        }
    }

    @Override
    @Transactional
    public Boolean clearFileById(String fileId) {
        //删除数据库中文件
        LambdaQueryWrapper<File> lambdaQueryWrapper = new LambdaQueryWrapper<File>()
                .isNull(File::getBusinessId).eq(File::getId, fileId);
        File file = this.fileDao.selectOne(lambdaQueryWrapper);
        if(EmptyUtil.isNullOrEmpty(file)){
            return true;
        }
        //执行删除逻辑
        int i = this.fileDao.deleteById(fileId);
        //删除对象存储中的文件
        if(i<0){
            throw new FileException("删除文件失败");
        }
        fileStorageAdapter.delete(file.getStoreFlag(),file.getBucketName(),file.getFileName());
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public Boolean PeriodicallyClearFile() {
        LocalDateTime localDateTime =  LocalDateTime.now().minusSeconds(fileDelayTime/1000);
        LambdaQueryWrapper<File> lambdaQueryWrapper = new LambdaQueryWrapper<File>()
                .isNull(File::getBusinessId).eq(File::getCreateTime,localDateTime);
        File file = this.fileDao.selectOne(lambdaQueryWrapper);
        if(EmptyUtil.isNullOrEmpty(file)){
            return true;
        }
        //执行删除逻辑
        int i = this.fileDao.deleteById(file);
        //删除对象存储中的文件
        if(i<0){
            throw new FileException("删除文件失败");
        }
        fileStorageAdapter.delete(file.getStoreFlag(),file.getBucketName(),file.getFileName());
        return null;
    }


}

