package com.lh.frame.subject.config;

import com.lh.frame.common.config.CustomNameThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Configuration
public class ThreadPoolConfig {

    /**
     * 创建并返回一个自定义的线程池。
     *
     * @return ThreadPoolExecutor 返回配置好的线程池实例。
     */
    @Bean(name = "labelThreadPool") // 将这个Bean命名为"labelThreadPool"
    public ThreadPoolExecutor getLabelThreadPool() {
        // 线程池的核心配置：
        // corePoolSize: 核心线程数为20
        // maximumPoolSize: 最大线程数为100
        // keepAliveTime: 空闲线程的存活时间为5秒
        // unit: 时间单位为秒
        // workQueue: 使用链接队列作为任务队列，容量为40
        // threadFactory: 使用自定义的线程工厂创建线程，线程名字前缀为"label"
        // rejectionPolicy: 当队列满且无法添加新线程时，由调用者运行任务
        return new ThreadPoolExecutor(20, 100, 5,
                TimeUnit.SECONDS, new LinkedBlockingDeque<>(40),
                new CustomNameThreadFactory("label"),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

}

