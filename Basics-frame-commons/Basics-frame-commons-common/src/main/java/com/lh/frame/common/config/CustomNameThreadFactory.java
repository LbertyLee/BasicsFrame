package com.lh.frame.common.config;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 自定义线程工厂类，用于创建具有特定名称前缀的线程。
 */
public class CustomNameThreadFactory implements ThreadFactory {

    // 用于为线程池编号，确保每个线程池的名称唯一
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    // 线程组，用于管理线程
    private final ThreadGroup group;
    // 用于为线程编号，确保每个线程的名称唯一
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    // 线程名称前缀
    private final String namePrefix;

    /**
     * 构造函数，初始化线程工厂。
     *
     * @param name 线程名称前缀。如果为空或白话，则默认为"pool"。
     */
    public CustomNameThreadFactory(String name) {
        // 获取系统安全管理者指定的线程组，若无则使用当前线程所属的线程组
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        // 处理传入的线程名称前缀，确保其不为空
        if (StringUtils.isBlank(name)) {
            name = "pool";
        }
        // 构造线程名称前缀，包含线程池编号和"thread-"后缀
        namePrefix = name + "-" +
                poolNumber.getAndIncrement() +
                "-thread-";
    }

    /**
     * 创建一个新的线程。
     *
     * @param r 线程执行的任务。
     * @return 新创建的线程。
     */
    @Override
    public Thread newThread(Runnable r) {
        // 创建线程，设置线程名称和任务
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        // 设置线程为非守护线程，默认为守护线程
        if (t.isDaemon()){
            t.setDaemon(false);
        }
        // 设置线程优先级为正常优先级，默认优先级
        if (t.getPriority() != Thread.NORM_PRIORITY){
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}

