package com.ghj.rpc.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 服务消费者
 * @author GeHejun
 * @date 2019-06-18
 */
public class GRpcConsumer implements InvocationHandler {
    /**
     * 接口类
     */
    private Class<?> interfaceClass;

    /**
     *
     * @param interfaceClass
     * @return
     */
    public GRpcConsumer interfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
        return this;
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
