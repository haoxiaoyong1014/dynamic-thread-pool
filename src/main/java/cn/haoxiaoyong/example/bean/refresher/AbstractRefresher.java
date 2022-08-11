package cn.haoxiaoyong.example.bean.refresher;

import cn.haoxiaoyong.example.bean.bean.DynamicThreadPoolProperties;
import cn.haoxiaoyong.example.bean.core.DynamicThreadPoolRegistry;
import cn.haoxiaoyong.example.bean.parser.PropertiesBinder;
import cn.haoxiaoyong.example.bean.parser.YamlConfigParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author haoxiaoyong
 * @version 1.0.0
 * @date created at 下午2:00 on 2022/8/8
 */
@Slf4j
public abstract class AbstractRefresher {

    @Resource
    private DynamicThreadPoolRegistry dynamicThreadPoolRegistry;

    @Resource
    private DynamicThreadPoolProperties dtpProperties;


    public void refresher(String content) {
        if (StringUtils.isBlank(content)) {
            log.warn("DynamicTp refresh, empty content.");
            return;
        }
        //目前先支持yml解析
        YamlConfigParser yamlParser = new YamlConfigParser();
        Map<Object, Object> yamlMap = yamlParser.doParse(content);
        doRefresher(yamlMap);

    }

    protected void doRefresher(Map<Object, Object> properties) {
        if (MapUtils.isEmpty(properties)) {
            log.warn("DynamicTp refresh, empty properties.");
            return;
        }
        PropertiesBinder.bindDtpProperties(properties,dtpProperties);
        doRefresher(dtpProperties);
    }

    protected void doRefresher(DynamicThreadPoolProperties dtpProperties) {

        dynamicThreadPoolRegistry.refresher(dtpProperties);
    }
}
