package com.cong.swag.common.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-01-04
 */
public class ConfigUtils {

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigUtils.class);
    private static Properties props;

    /**
     * 获取key的属性值
     * @param key
     * @return
     */
    public static String getValue(String key){
        init();
        String k = props.getProperty(key);
        if(k == null){
            k = props.getProperty(key.toLowerCase());
        }
        return k;
    }


    /**
     * 获取property key的值，如果值不存在，返回defaultValue
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getValue(String key, String defaultValue){
        init();
        String value = props.getProperty(key);
        if(value == null){
            value = props.getProperty(key.toLowerCase());
        }
        if(value == null) {
            value = defaultValue;
        }

        return value;
    }


    public static void updateProperty(String key,String value) {
        init();
        props.setProperty(key, value);
        props.setProperty(key.toLowerCase(), value);
    }

    private static void init() {
        if(props == null) {
            PropertyPlaceholderConfigurer propertyConfigurer = new PropertyPlaceholderConfigurer();
            PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
            try {
                Resource[] locations = resourceResolver.getResources("classpath*:**/*.properties");
                propertyConfigurer.setLocations(locations);
                propertyConfigurer.setFileEncoding("utf-8");
                props = (Properties) MethodUtils.invokeMethod(propertyConfigurer, true,"mergeProperties");
                if(props != null){
                    Properties newProps = new Properties();
                    for(Object key : props.keySet()){
                        if(key == null) {
                            continue;
                        }
                        String keyLower = key.toString().toLowerCase();
                        Object value = props.get(key);
                        newProps.put(keyLower, value);
                        newProps.put(key, value);
                    }
                    props.clear();
                    props.putAll(newProps);
                }
            } catch (IOException e) {
                LOGGER.warn("", e);
                props = new Properties();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }

    public void setPropertyConfigurer(
        PropertyPlaceholderConfigurer propertyConfigurer)
        throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        LOGGER.info("start to set propertyConfigurer");
        props = (Properties) MethodUtils.invokeMethod(propertyConfigurer, true, "mergeProperties");
        init();
    }

}
