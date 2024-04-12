package com.lh.frame.task.job;

import com.lh.frame.oss.service.FileService;
import com.lh.frame.subject.service.SubjectLikedService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class SyncFileJob {
    @Resource
    private FileService fileService;

    @XxlJob("syncFileJobHandler")
    public void syncLikedJobHandler() throws Exception {
        XxlJobHelper.log("syncLikedJobHandler.start");
        try {
            fileService.syncFile();
        } catch (Exception e) {
            XxlJobHelper.log("syncLikedJobHandler.error" + e.getMessage());
        }
    }
}
