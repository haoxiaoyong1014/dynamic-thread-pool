package cn.haoxiaoyong.example.bean.core;

import cn.haoxiaoyong.example.bean.bean.DynamicMainProp;
import cn.haoxiaoyong.example.bean.bean.DynamicThreadPoolProperties;
import cn.haoxiaoyong.example.bean.bean.ThreadPoolProperties;
import cn.haoxiaoyong.example.bean.queue.VariableLinkedBlockingQueue;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static cn.haoxiaoyong.example.bean.constant.DynamicThreadPoolConst.PROPERTIES_CHANGE_SHOW_STYLE;


/**
 * @author haoxiaoyong
 * @version 1.0.0
 * @date created at 下午3:05 on 2022/8/8
 */
@Slf4j
@Component
public class DynamicThreadPoolRegistry {

    @Resource
    private Map<String, ThreadPoolExecutor> threadPoolExecutorMap = new HashMap<>();

    public void refresher(DynamicThreadPoolProperties dtpProperties) {
        if (Objects.isNull(dtpProperties) || CollectionUtils.isEmpty(dtpProperties.getExecutors())) {
            log.warn("DynamicTp refresh, empty threadPoolProperties.");
            return;
        }

        dtpProperties.getExecutors().forEach(x -> {
            if (StringUtils.isBlank(x.getThreadPoolName())) {
                log.warn("DynamicTp refresh, threadPoolName must not be empty.");
                return;
            }
            ThreadPoolExecutor executor = threadPoolExecutorMap.get(x.getThreadPoolName());
            refresher(executor, x);
        });
    }


    private void refresher(ThreadPoolExecutor executor, ThreadPoolProperties properties) {

        if (properties.getCorePoolSize() < 0 ||
                properties.getMaximumPoolSize() <= 0 ||
                properties.getMaximumPoolSize() < properties.getCorePoolSize() ||
                properties.getKeepAliveTime() < 0) {

            log.error("DynamicTp refresh,  invalid configuration: {}", properties);
            return;
        }

        if (Objects.equals(properties.getCorePoolSize(), executor.getCorePoolSize())
                && Objects.equals(properties.getMaximumPoolSize(), executor.getMaximumPoolSize())
                && Objects.equals(properties.getQueueCapacity(), executor.getQueue().size())
                && Objects.equals(properties.getKeepAliveTime(), executor.getKeepAliveTime(properties.getTimeUnit()))
                && Objects.equals(properties.isAllowCoreThreadTimeOut(), executor.allowsCoreThreadTimeOut())) {

            log.warn("DynamicTp refresh, main properties of [{}] have not changed.", properties.getThreadPoolName());
            return;
        }
        DynamicMainProp oldDynamic = new DynamicMainProp(executor);
        doRefresher(executor, properties);

        log.info("DynamicTp refresh, name: [{}],  corePoolSize: [{}], maxPoolSize: [{}], " +
                        "queueCapacity: [{}], keepAliveTime: [{}], " +
                        "allowsCoreThreadTimeOut: [{}]",
                properties.getThreadPoolName(),
                String.format(PROPERTIES_CHANGE_SHOW_STYLE, oldDynamic.getCorePoolSize(), properties.getCorePoolSize()),
                String.format(PROPERTIES_CHANGE_SHOW_STYLE, oldDynamic.getMaximumPoolSize(), properties.getMaximumPoolSize()),
                String.format(PROPERTIES_CHANGE_SHOW_STYLE, oldDynamic.getQueueCapacity(), properties.getQueueCapacity()),
                String.format("%ss => %ss", oldDynamic.getKeepAliveTime(), properties.getKeepAliveTime()),
                String.format(PROPERTIES_CHANGE_SHOW_STYLE, oldDynamic.isAllowCoreThreadTimeOut(),
                        properties.isAllowCoreThreadTimeOut()));
        oldDynamic = null;
    }

    private void doRefresher(ThreadPoolExecutor executor, ThreadPoolProperties properties) {

        executor.setCorePoolSize(properties.getCorePoolSize());
        executor.setMaximumPoolSize(properties.getMaximumPoolSize());
        executor.setKeepAliveTime(properties.getKeepAliveTime(), properties.getTimeUnit());
        executor.allowCoreThreadTimeOut(properties.isAllowCoreThreadTimeOut());
        val blockingQueue = executor.getQueue();
        if (blockingQueue instanceof LinkedBlockingQueue) {
            log.warn("DynamicTp refresh, {} :capacity cannot be changed.", blockingQueue);
        }
        if (blockingQueue instanceof VariableLinkedBlockingQueue) {
            ((VariableLinkedBlockingQueue<Runnable>) blockingQueue).setCapacity(properties.getQueueCapacity());
        }
    }
}
