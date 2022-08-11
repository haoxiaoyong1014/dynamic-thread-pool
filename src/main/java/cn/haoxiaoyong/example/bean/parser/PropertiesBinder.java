package cn.haoxiaoyong.example.bean.parser;

import cn.haoxiaoyong.example.bean.bean.DynamicThreadPoolProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import java.util.Map;

import static cn.haoxiaoyong.example.bean.constant.DynamicThreadPoolConst.MAIN_PROPERTIES_PREFIX;


/**
 * @author haoxiaoyong
 * @version 1.0.0
 * @date created at 上午9:59 on 2022/8/8
 */
public class PropertiesBinder {

    private PropertiesBinder() {}

    public static void bindDtpProperties(Map<?, Object> properties, DynamicThreadPoolProperties dtpProperties) {
        ConfigurationPropertySource sources = new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(sources);
        ResolvableType type = ResolvableType.forClass(DynamicThreadPoolProperties.class);
        Bindable<?> target = Bindable.of(type).withExistingValue(dtpProperties);
        binder.bind(MAIN_PROPERTIES_PREFIX, target);
    }

    public static void bindDtpProperties(Environment environment, DynamicThreadPoolProperties dtpProperties) {
        Binder binder = Binder.get(environment);
        ResolvableType type = ResolvableType.forClass(DynamicThreadPoolProperties.class);
        Bindable<?> target = Bindable.of(type).withExistingValue(dtpProperties);
        binder.bind(MAIN_PROPERTIES_PREFIX, target);
    }
}
