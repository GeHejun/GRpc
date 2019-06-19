package com.ghj.rpc.core;

import com.ghj.rpc.annotation.GrcpService;
import com.ghj.rpc.util.PropertiesUtil;
import com.ghj.rpc.util.ReflectUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 查找指定包下的类
 * @author GeHejun
 * @date 2019-06-19
 */
public class Registry {

    /**
     * 存放接口和实现类映射关系
     */
    public static HashMap handlerMap = new HashMap(16);

    /**
     * 发现注解类
     */
    public static void register() throws IOException {
        String packageName = PropertiesUtil.loadProperties("/grpc.properties", "base-package");
        List<Class<?>> classes = ReflectUtil.getClasses(packageName);
        classes.forEach(c -> {
            if (c.isAnnotationPresent(GrcpService.class)) {
                Class<?>[] interfaces = c.getInterfaces();
                try {
                    Object o = c.newInstance();
                    for (int i = 0; i < interfaces.length; i++) {
                        if (handlerMap.get(interfaces[i].getName()) == null) {
                            handlerMap.put(interfaces[i].getName(), o);
                        }
                    }
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }

        });
    }




}
