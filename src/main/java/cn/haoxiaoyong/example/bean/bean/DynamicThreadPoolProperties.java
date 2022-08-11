package cn.haoxiaoyong.example.bean.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

import static cn.haoxiaoyong.example.bean.constant.DynamicThreadPoolConst.MAIN_PROPERTIES_PREFIX;


/**
 * @author haoxiaoyong
 * @version 1.0.0
 * @date created at 上午9:59 on 2022/8/8
 */
@Data
@ConfigurationProperties(prefix = MAIN_PROPERTIES_PREFIX)
public class DynamicThreadPoolProperties {

    /**
     * 是否开始动态配置
     */
    private boolean enabled = true;

    /**
     * 线程池基础配置
     */
    private List<ThreadPoolProperties> executors;

}
