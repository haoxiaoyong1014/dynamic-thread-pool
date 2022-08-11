package cn.haoxiaoyong.example.bean.parser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ByteArrayResource;

import java.util.Collections;
import java.util.Map;

/**
 * @author haoxiaoyong
 * @version 1.0.0
 * @date created at 下午2:08 on 2022/8/8
 */
public class YamlConfigParser {

    public Map<Object, Object> doParse(String content) {

        if (StringUtils.isEmpty(content)) {
            return Collections.emptyMap();
        }
        YamlPropertiesFactoryBean bean = new YamlPropertiesFactoryBean();
        bean.setResources(new ByteArrayResource(content.getBytes()));
        return bean.getObject();
    }
}
