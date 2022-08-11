package cn.haoxiaoyong.example.bean.bean;

import cn.haoxiaoyong.example.bean.constant.DynamicThreadPoolConst;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @author haoxiaoyong
 * @version 1.0.0
 * @date created at 上午10:07 on 2022/8/8
 */
@Data
public class ThreadPoolProperties {

    /**
     * 线程池的名称
     */
    private String threadPoolName;

    /**
     * 核心线程数
     */
    private int corePoolSize = 1;

    /**
     * 最大线程数
     */
    private int maximumPoolSize = DynamicThreadPoolConst.AVAILABLE_PROCESSORS;

    /**
     * 线程队列数
     */
    private int queueCapacity = 1024;

    /**
     * 是否允许核心线程超时
     */
    private boolean allowCoreThreadTimeOut = false;

    /**
     * 超时时间
     */
    private long keepAliveTime = 30;

    /**
     * Timeout unit.
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;
}
