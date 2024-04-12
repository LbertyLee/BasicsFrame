package com.lh.frame.subject.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.MapUtils;
import com.lh.frame.common.entity.PageResult;
import com.lh.frame.common.exception.SystemException;
import com.lh.frame.redis.utils.RedisUtil;
import com.lh.frame.subject.constant.SubjectLikedConstant;
import com.lh.frame.subject.convert.SubjectLikedEntityConverter;
import com.lh.frame.subject.dao.SubjectLikedDao;
import com.lh.frame.subject.domain.vo.request.SubjectLikedReq;
import com.lh.frame.subject.domain.vo.response.SubjectLikedResp;
import com.lh.frame.subject.entity.SubjectLiked;
import com.lh.frame.subject.enums.SubjectLikedStatusEnum;
import com.lh.frame.subject.service.SubjectLikedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 题目信息表(FrameSubjectLiked)表服务实现类
 *
 * @author LbertyLee
 * @since 2024-04-09 17:05:12
 */
@Slf4j
@Service
public class SubjectLikedServiceImpl extends ServiceImpl<SubjectLikedDao, SubjectLiked> implements SubjectLikedService {


    @Resource
    private RedisUtil redisUtil;

    /**
     * 添加题目点赞信息
     *
     * @param subjectLikedReq 包含点赞信息的请求对象，包括题目ID、点赞用户ID和点赞状态
     * @throws SystemException 如果点赞过程中发生异常，则抛出系统异常
     */
    @Override
    public void addSubjectLiked(SubjectLikedReq subjectLikedReq) {
        try {
            Long subjectId = subjectLikedReq.getSubjectId();
            String likeUserId = subjectLikedReq.getLikeUserId();
            Integer status = subjectLikedReq.getStatus();

            // 检查输入参数的有效性（示例，根据实际情况进行调整）
            if (subjectId == null || likeUserId == null || status == null) {
                throw new IllegalArgumentException("Invalid request parameters.");
            }
            // 使用事务保证数据一致性
            String hashKey = redisUtil.buildHashKey(subjectId.toString(), likeUserId);
            redisUtil.putHash(SubjectLikedConstant.SUBJECT_LIKED_KEY, hashKey, status);

            String detailKey = redisUtil.buildDetailKey(SubjectLikedConstant.SUBJECT_LIKED_DETAIL_KEY, subjectId, likeUserId);
            String countKey = redisUtil.buildCountKey(SubjectLikedConstant.SUBJECT_LIKED_COUNT_KEY, subjectId);

            if (SubjectLikedStatusEnum.LIKED.getCode() == status) {
                if (Objects.nonNull(redisUtil.get(detailKey))) {
                    return;
                }
                redisUtil.increment(countKey, 1);
                redisUtil.set(detailKey, "1");
            } else {
                // 检查当前计数是否有效（大于0），无效则直接返回
                Integer count = redisUtil.getInt(countKey);
                if (Objects.isNull(count) || count <= 0) {
                    return;
                }
                redisUtil.increment(countKey, -1);
                redisUtil.del(detailKey);
            }
        } catch (Exception e) {
            // 异常处理逻辑（示例，根据实际情况进行调整）
            System.err.println("Error processing subject like: " + e.getMessage());
            // 可能需要根据异常类型进行更细致的处理
        }
    }

    @Override
    public PageResult<SubjectLikedResp> querySubjectLikedPage(SubjectLikedReq subjectLikedReq) {

        return null;
    }

    @Override
    public void syncLiked() {
        // 从Redis中获取并删除用户点赞的哈希表
        Map<Object, Object> subjectLikedMap = redisUtil.getHashAndDelete(SubjectLikedConstant.SUBJECT_LIKED_KEY);
        if (log.isInfoEnabled()) {
            // 如果日志级别允许，打印获取到的点赞信息
            log.info("syncLiked.subjectLikedMap:{}", JSON.toJSONString(subjectLikedMap));
        }
        // 如果点赞信息为空，则直接返回，无需进一步操作
        if (MapUtils.isEmpty(subjectLikedMap)) {
            return;
        }
        try{
            // 批量处理点赞信息，转换为SubjectLiked实体，并插入数据库
            List<SubjectLiked> subjectLikedList = new LinkedList<>();
            subjectLikedMap.forEach((key, val) -> {
                SubjectLiked subjectLiked = new SubjectLiked();
                // 解析key字符串为点赞的主体ID和点赞用户ID
                String[] keyArr = key.toString().split(":");
                String subjectId = keyArr[0];
                String likedUser = keyArr[1];
                subjectLiked.setSubjectId(Long.valueOf(subjectId));
                subjectLiked.setLikeUserId(likedUser);
                // 设置点赞状态
                subjectLiked.setStatus(Integer.valueOf(val.toString()));
                subjectLikedList.add(subjectLiked);
            });
            this.saveBatch(subjectLikedList);
        }catch (Exception e){
            log.error("syncLiked.error:{}", e.getMessage());
            throw new SystemException("同步点赞信息失败");
        }

    }

}

