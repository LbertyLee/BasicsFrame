package com.lh.frame.redis.config;

import com.lh.frame.common.exception.CacheException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;

@Slf4j
@Configuration
public class RedisCacheConfig extends CachingConfigurerSupport {
    @Resource
    private RedisConnectionFactory factory;


    /**
     * 创建并返回一个缓存解析器实例。
     * 这个方法没有参数。
     *
     * @return CacheResolver - 缓存解析器实例。它使用了通过cacheManager()方法配置的缓存管理器。
     */
    @Bean
    @Override
    public CacheResolver cacheResolver() {
        // 使用SimpleCacheResolver并注入缓存管理器来创建缓存解析器实例
        return new SimpleCacheResolver(Objects.requireNonNull(cacheManager()));
    }


    /**
     * 配置自定义的缓存错误处理器。
     * 当对缓存进行CRUD操作时发生异常，会触发相应的错误处理回调。
     *
     * @return CacheErrorHandler 缓存错误处理器实例。
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 自定义SimpleCacheErrorHandler，重写异常处理方法以定制错误处理逻辑
        return new SimpleCacheErrorHandler(){
            /**
             * 处理查询缓存时发生的异常。
             *
             * @param exception 发生的异常对象。
             * @param cache 发生异常的缓存实例。
             * @param key 查询缓存时使用的键。
             */
            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                log.error("查询缓存错误=>key{}",key, exception);
                throw new CacheException("查询缓存错误");
            }

            /**
             * 处理更新缓存时发生的异常。
             *
             * @param exception 发生的异常对象。
             * @param cache 发生异常的缓存实例。
             * @param key 更新缓存时使用的键。
             * @param value 要更新的缓存值。
             */
            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                log.error("更新缓存错误=>key{},value=>{}",key, value,exception);
                throw new CacheException("更新缓存错误");

            }

            /**
             * 处理删除缓存时发生的异常。
             *
             * @param exception 发生的异常对象。
             * @param cache 发生异常的缓存实例。
             * @param key 要删除缓存的键。
             */
            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                log.error("删除缓存错误=>key{}",key, exception);
                throw new CacheException("删除缓存错误");

            }

            /**
             * 处理清理缓存时发生的异常。
             *
             * @param exception 发生的异常对象。
             * @param cache 发生异常的缓存实例。
             */
            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                log.error("清理缓存错误", exception);
                throw new CacheException("清理缓存错误");
            }
        };
    }

    /**
     * 修改spring-cache默认前缀为:
     */
    @Bean
    public CacheKeyPrefix cacheKeyPrefix() {
        return cacheName -> "cache:"+cacheName + ":";
    }

    /**
     * 配置并创建Redis缓存管理器。
     * 这个方法定义了如何序列化Redis缓存的键和值，以及设置默认的缓存超时时间。
     *
     * @return CacheManager 返回一个配置好的Redis缓存管理器实例。
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        // 序列化缓存键为String
        RedisSerializer<String> keySerializer = new StringRedisSerializer();
        // 序列化缓存值为JSON格式
        GenericJackson2JsonRedisSerializer valuesSerializer = new GenericJackson2JsonRedisSerializer();

        // 配置Redis缓存设置，包括前缀、超时时间和序列化方式
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .computePrefixWith(cacheKeyPrefix()) // 设置缓存键的前缀
                .entryTtl(Duration.ofMinutes(60))
                // 配置键的序列化方式
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
                // 配置值的序列化方式
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valuesSerializer));

        // 基于配置构建并返回Redis缓存管理器
        return RedisCacheManager.builder(factory).cacheDefaults(config).build();
    }




}
