package cn.haoxiaoyong.example.bean.bean;

import lombok.Data;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author haoxiaoyong
 * @version 1.0.0
 * @date created at 上午10:18 on 2022/8/9
 */
@Data
public class DynamicMainProp {

    private int corePoolSize;

    private int maximumPoolSize;

    private long keepAliveTime;

    private int queueCapacity;

    private boolean allowCoreThreadTimeOut;

    public DynamicMainProp() {
    }

    public DynamicMainProp(ThreadPoolExecutor executor) {
        this.corePoolSize = executor.getCorePoolSize();
        this.maximumPoolSize = executor.getMaximumPoolSize();
        this.keepAliveTime = executor.getKeepAliveTime(TimeUnit.SECONDS);
        this.queueCapacity = executor.getQueue().size();
        this.allowCoreThreadTimeOut = executor.allowsCoreThreadTimeOut();
    }


}
