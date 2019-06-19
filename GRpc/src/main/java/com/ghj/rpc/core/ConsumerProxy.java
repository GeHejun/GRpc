package com.ghj.rpc.core;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ConsumerProxy implements MethodInterceptor {
    @Override
    public Object intercept(Object sub, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Discoverer.discover();
        Object object = methodProxy.invokeSuper(sub, objects);
        return object;
    }
}
