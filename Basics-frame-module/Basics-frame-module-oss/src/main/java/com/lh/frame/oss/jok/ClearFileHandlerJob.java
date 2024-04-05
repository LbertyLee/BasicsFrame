package com.lh.frame.oss.jok;


import com.lh.frame.oss.service.FileService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *  清理垃圾文件
 */
@Component
public class ClearFileHandlerJob {

    @Resource
    private FileService fileService;

    @XxlJob(value = "clear-file")
    public ReturnT<String> execute(String param) {
        Boolean responseWrap = fileService.PeriodicallyClearFile();
        if (responseWrap){
            ReturnT.SUCCESS.setMsg("计划任务：清理垃圾文件-成功");
            return ReturnT.SUCCESS;
        }
        ReturnT.FAIL.setMsg("计划任务：清理垃圾文件-失败");
        return ReturnT.FAIL;

    }
}
