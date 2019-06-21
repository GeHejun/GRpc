package com.ghj.rpc.core;

import com.ghj.rpc.annotation.Quote;
import com.ghj.rpc.util.PropertiesUtil;
import com.ghj.rpc.util.ReflectUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 查找服务形成代理
 * @author GeHejun
 * @date 2019-06-18
 */
public class Discoverer {

    /**
     * 发现注解类
     */
    public static void discover(Object target) throws IOException {
        String packageName = PropertiesUtil.loadProperties("/application.properties", "discover-package");
        List<Class<?>> classes = ReflectUtil.getClasses(packageName);
        classes.forEach(c -> {
            Field[] fields = c.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if (field.isAnnotationPresent(Quote.class)) {
                    Type genericType = field.getGenericType();
                        Consumer consumer = new Consumer();
                        Object object = consumer.proxy((Class) genericType);
                    try {
                        field.setAccessible(true);
                        field.set(target, object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }


        });
    }
}
