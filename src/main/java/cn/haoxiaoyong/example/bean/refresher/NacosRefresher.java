package cn.haoxiaoyong.example.bean.refresher;

import com.alibaba.nacos.spring.context.event.config.NacosConfigReceivedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author haoxiaoyong
 * @version 1.0.0
 * @date created at 上午11:14 on 2022/8/8
 */
@Component
public class NacosRefresher extends AbstractRefresher implements ApplicationListener<NacosConfigReceivedEvent> {


    @Override
    public void onApplicationEvent(NacosConfigReceivedEvent nacosConfigReceivedEvent) {

        refresher(nacosConfigReceivedEvent.getContent());
    }
}
