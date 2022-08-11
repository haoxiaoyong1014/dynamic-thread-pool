package cn.haoxiaoyong.example.bean.config;

import cn.haoxiaoyong.example.bean.bean.ThreadPoolProperties;
import cn.haoxiaoyong.example.bean.queue.VariableLinkedBlockingQueue;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author haoxiaoyong
 * @version 1.0.0
 * @date created at 上午10:53 on 2022/8/8
 */
@Configuration
public class ThreadPoolConfiguration {

    /**
     * Default inner thread factory.
     */
    private final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("dy-thread-%d").build();

    /**
     * 拒绝策略
     */
    private final RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.AbortPolicy();

    @Bean
    public ThreadPoolExecutor commonExecutor() {
        return (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
    }

    @Bean
    public ThreadPoolExecutor dynamicThreadPoolExecutor1() {
        ThreadPoolProperties defaultThreadPoolProperties = new ThreadPoolProperties();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                defaultThreadPoolProperties.getCorePoolSize(),
                defaultThreadPoolProperties.getMaximumPoolSize(),
                defaultThreadPoolProperties.getKeepAliveTime(),
                defaultThreadPoolProperties.getTimeUnit(),
                new VariableLinkedBlockingQueue<>(defaultThreadPoolProperties.getQueueCapacity()),
                namedThreadFactory,
                rejectedExecutionHandler
        );
        executor.allowCoreThreadTimeOut(defaultThreadPoolProperties.isAllowCoreThreadTimeOut());
        return executor;
    }
}
