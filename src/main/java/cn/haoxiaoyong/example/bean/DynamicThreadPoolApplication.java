package cn.haoxiaoyong.example.bean;

import cn.haoxiaoyong.example.bean.bean.DynamicThreadPoolProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author haoxiaoyong
 * @version 1.0.0
 * @date created at 下午7:34 on 2022/8/11
 */
@SpringBootApplication
@EnableConfigurationProperties(DynamicThreadPoolProperties.class)
public class DynamicThreadPoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicThreadPoolApplication.class,args);
    }
}
