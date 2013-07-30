package com.gap.metrics.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

@Slf4j
public class ExposablePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements IPropertyBag{

    private final Map<String,String> resolvedProps = new HashMap<String,String>();

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = keyStr.matches("(?i).*(password|sensitive).*") ? "********" : props.getProperty(keyStr);
            resolvedProps.put(keyStr, value);
            ExposablePropertyPlaceholderConfigurer.log.debug("Captured property {} {}", keyStr, value);
        }
    }

    @Override
    public Map<String,String> getLoadedProperties() {
        return resolvedProps;
    }


}
