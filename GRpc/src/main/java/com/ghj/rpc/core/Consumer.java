package com.ghj.rpc.core;

import com.ghj.rpc.context.Request;
import com.ghj.rpc.context.Response;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 服务消费者
 * @author GeHejun
 * @date 2019-06-18
 */
public class Consumer implements InvocationHandler {

    ConsumerSession consumerSession = ConsumerSession.instance();

    /**
     * 生成代理对象
     * @param interfaceClass
     * @param <T>
     * @return
     */
    public <T>T proxy(Class<T> interfaceClass) {
        if (!interfaceClass.isInterface()) {
            throw new RuntimeException(interfaceClass + "is not a interface");
        }
        T t = null;
        try {
            t =  (T)Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{interfaceClass}, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Request request = new Request();
        //requestId
        request.setRequestId(UUID.randomUUID().toString().replace("-",""));
        //类名
        request.setClassName(method.getDeclaringClass().getName());
        //方法名字
        request.setMethodName(method.getName());
        //获取参数类型数组
        Class<?>[] parameterTypes = method.getParameterTypes();
        request.setParameterTypes(parameterTypes);
        //参数数组
        request.setParameters(args);
        //执行方法
        consumerSession.send(request);
        return ((Response)DataBus.outData(request.getRequestId(), 3000)).getResultResponse();
    }


}
