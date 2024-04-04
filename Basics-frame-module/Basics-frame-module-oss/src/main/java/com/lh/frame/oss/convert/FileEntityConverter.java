package com.lh.frame.oss.convert;

import com.lh.frame.oss.entity.File;
import com.lh.frame.oss.entity.vo.request.FileReq;
import com.lh.frame.oss.entity.vo.response.FileResp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 文件实体转换器接口，定义了如何将请求对象转换为文件实体。
 */
@Mapper
public interface FileEntityConverter {
    // 单例模式，获取转换器实例
    FileEntityConverter INSTANCE = Mappers.getMapper(FileEntityConverter.class);

    /**
     * 将文件请求对象转换为文件实体。
     *
     * @param fileReq 文件请求对象，包含了请求中关于文件的信息。
     * @return 转换后的文件实体，包含了文件的所有必要信息。
     */
    File convertReqToEntity(FileReq fileReq);

    FileResp convertEntityToResp(File file);

}

