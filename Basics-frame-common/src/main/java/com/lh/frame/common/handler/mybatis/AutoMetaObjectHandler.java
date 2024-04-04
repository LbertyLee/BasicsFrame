package com.lh.frame.common.handler.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lh.frame.common.constant.basic.SuperConstant;
import com.lh.frame.common.utils.EmptyUtil;
import com.lh.frame.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Slf4j
@Component
public class AutoMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充.....");
        Long userId = SecurityUtils.getUserId();
        if (EmptyUtil.isNullOrEmpty(userId)) {
            userId = null;
        }
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createBy", Long.class,userId);
        this.strictInsertFill(metaObject, "updateBy", Long.class,userId);
    }


    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充.....");

        Long userId = SecurityUtils.getUserId();
        if (EmptyUtil.isNullOrEmpty(userId)) {
            userId = null;
        }
        if (metaObject.hasSetter("updateTime")) {
            this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }
        if (metaObject.hasSetter("updateBy")) {
            this.setFieldValByName("updateBy", userId, metaObject);
        }
    }
}
