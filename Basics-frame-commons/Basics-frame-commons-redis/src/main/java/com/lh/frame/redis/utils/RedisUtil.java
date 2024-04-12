package com.lh.frame.redis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class RedisUtil {
    @Resource
    private RedisTemplate redisTemplate;

    private static final String CACHE_KEY_SEPARATOR = ".";

    /**
     * 构建缓存key
     * @param strObjs 字符串数组，用于构建key
     * @return 拼接后的key字符串
     */
    public String buildKey(String... strObjs) {
        return Stream.of(strObjs).collect(Collectors.joining(CACHE_KEY_SEPARATOR));
    }

    /**
     * 检查指定的key是否存在
     * @param key 要检查的缓存键
     * @return 如果key存在返回true，否则返回false
     */
    public boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 删除指定的key
     * @param key 要删除的缓存键
     * @return 如果删除成功返回true，否则返回false
     */
    public boolean del(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 为指定的key设置值（不带过期时间）
     * @param key 缓存键
     * @param value 缓存值
     */
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 为指定的key设置值，并设定过期时间
     * @param key 缓存键
     * @param value 缓存值
     * @param time 过期时间
     * @param timeUnit 时间单位
     * @return 如果设置成功返回true，否则返回false
     */
    public boolean setNx(String key, String value, Long time, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
    }

    /**
     * 获取指定key的字符串类型缓存值
     * @param key 缓存键
     * @return 缓存值，如果key不存在返回null
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 向有序集合中添加元素和分数
     * @param key 有序集合的键
     * @param value 要添加的元素
     * @param score 元素的分数
     * @return 如果元素被成功添加返回true，否则返回false
     */
    public Boolean zAdd(String key, String value, Long score) {
        return redisTemplate.opsForZSet().add(key, value, Double.valueOf(String.valueOf(score)));
    }

    /**
     * 获取有序集合的元素数量
     * @param key 有序集合的键
     * @return 有序集合的元素数量
     */
    public Long countZset(String key) {
        return redisTemplate.opsForZSet().size(key);
    }

    /**
     * 获取有序集合指定范围的元素
     * @param key 有序集合的键
     * @param start 开始位置
     * @param end 结束位置
     * @return 有序集合指定范围的元素集合
     */
    public Set<String> rangeZset(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 从有序集合中移除指定的元素
     * @param key 有序集合的键
     * @param value 要移除的元素
     * @return 被移除的元素数量
     */
    public Long removeZset(String key, Object value) {
        return redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 从有序集合中移除指定的多个元素
     * @param key 有序集合的键
     * @param value 要移除的元素集合
     */
    public void removeZsetList(String key, Set<String> value) {
        value.stream().forEach((val) -> redisTemplate.opsForZSet().remove(key, val));
    }

    /**
     * 获取有序集合中指定元素的分数
     * @param key 有序集合的键
     * @param value 元素
     * @return 元素的分数
     */
    public Double score(String key, Object value) {
        return redisTemplate.opsForZSet().score(key, value);
    }

    /**
     * 获取有序集合中指定分数范围的元素
     * @param key 有序集合的键
     * @param start 开始分数
     * @param end 结束分数
     * @return 有序集合中指定分数范围的元素集合
     */
    public Set<String> rangeByScore(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeByScore(key, Double.valueOf(String.valueOf(start)), Double.valueOf(String.valueOf(end)));
    }

    /**
     * 为有序集合中指定元素增加分数
     * @param key 有序集合的键
     * @param obj 元素
     * @param score 要增加的分数
     * @return 增加分数后的元素分数
     */
    public Object addScore(String key, Object obj, double score) {
        return redisTemplate.opsForZSet().incrementScore(key, obj, score);
    }

    /**
     * 获取有序集合中指定元素的排名
     * @param key 有序集合的键
     * @param obj 元素
     * @return 元素的排名
     */
    public Object rank(String key, Object obj) {
        return redisTemplate.opsForZSet().rank(key, obj);
    }

    /**
     * 获取有序集合中指定范围元素及其分数
     * @param key 有序集合的键
     * @param start 开始位置
     * @param end 结束位置
     * @return 有序集合中指定范围的元素及其分数的集合
     */
    public Set<ZSetOperations.TypedTuple<String>> rankWithScore(String key, long start, long end) {
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        return set;
    }

    /**
     * 向哈希表中添加值
     * @param key 哈希表的键
     * @param hashKey 哈希表中的字段
     * @param hashVal 哈希表中字段的值
     */
    public void putHash(String key, String hashKey, Object hashVal) {
        redisTemplate.opsForHash().put(key, hashKey, hashVal);
    }

    /**
     * 获取哈希表中指定键的整型值
     * @param key 哈希表的键
     * @return 哈希表中指定键的整型值，如果不存在则返回null
     */
    public Integer getInt(String key) {
        return (Integer) redisTemplate.opsForValue().get(key);
    }

    /**
     * 为指定键的值增加指定数量
     * @param key 键
     * @param count 要增加的数量
     */
    public void increment(String key,Integer count) {
        redisTemplate.opsForValue().increment(key,count);
    }

    /**
     * 获取哈希表的所有键值对并删除这些键值对
     * @param key 哈希表的键
     * @return 哈希表的所有键值对
     */
    public Map<Object, Object> getHashAndDelete(String key) {
        Map<Object, Object> map = new HashMap<>();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(key, ScanOptions.NONE);
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            Object hashKey = entry.getKey();
            Object value = entry.getValue();
            map.put(hashKey, value);
            redisTemplate.opsForHash().delete(key, hashKey);
        }
        return map;
    }

    /**
     * 构建详情键值。
     * 该方法通过组合详情类型、目标ID和用户ID，生成一个唯一的键值。
     *
     * @param detailType 详情类型，用于区分不同的详情。
     * @param targetId 目标ID，标识详情所属的目标对象。
     * @param userId 用户ID，标识该详情所属的用户。
     * @return 返回由详情类型、目标ID和用户ID组合而成的键值字符串。
     */
    public String buildDetailKey(String detailType, Long targetId, String userId) {
        return detailType + "." + targetId + "." + userId;
    }

    /**
     * 构建计数键值。
     * 该方法通过组合主题喜欢计数键和主题ID，生成一个用于记录喜欢计数的键值。
     *
     * @param countKey 主题喜欢计数键，用于区分不同主题的喜欢计数。
     * @param targetId 主题ID，标识计数所属的主题对象。
     * @return 返回由主题喜欢计数键和主题ID组合而成的键值字符串。
     */
    public String buildCountKey(String countKey, Long targetId) {
        return countKey + "." + targetId;
    }

    /**
     * 构建哈希键
     * @param targetId 目标ID，表示特定的目标对象，例如帖子、图片等的ID
     * @param likeUserId 喜欢这个目标对象的用户ID
     * @return 返回一个字符串，格式为"目标ID:用户ID"，用作哈希表的键
     */
    public String buildHashKey(String targetId, String likeUserId) {
        // 将目标ID和用户ID连接成一个字符串，作为哈希键
        return targetId + ":" + likeUserId;
    }

    public void multi() {
        redisTemplate.multi();
    }

    public void discard() {
        redisTemplate.discard();
    }

    public void exec() {
        redisTemplate.exec();
    }
}
