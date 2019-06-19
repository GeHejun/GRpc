package com.ghj.rpc.core;


import com.ghj.rpc.context.Request;
import com.ghj.rpc.context.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;

/**
 * @author GeHejun
 * @date 2019-16-18
 */
public class ProviderHandler extends ChannelInboundHandlerAdapter {

    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request gRpcRequest = (Request)msg;
        Object result = handle(gRpcRequest);
        Response gRpcResponse = pack(gRpcRequest, result);
        ctx.writeAndFlush(gRpcResponse);
    }



    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    /**
     * 执行方法
     * @param gRpcRequest
     */
    private Object handle(Request gRpcRequest) throws InvocationTargetException {
        //获取请求的接口名
        String interfaceName = gRpcRequest.getClassName();
        //获取接口对应实现类 zookeeper
        Object classImpl = Registry.handlerMap.get(interfaceName);
        //获取实现类对应的Class对象
        Class<?> classImplClass = classImpl.getClass();
        //获取方法名
        String methodName = gRpcRequest.getMethodName();
        //获取参数类型
        Class[] parameterTypes = gRpcRequest.getParameterTypes();
        //获取参数列表
        Object[] parameters = gRpcRequest.getParameters();
        //创建类
        FastClass fastClass = FastClass.create(classImplClass);
        //获取类对应的方法
        FastMethod method = fastClass.getMethod(methodName, parameterTypes);
        //执行方法得到结果
        Object result = method.invoke(classImpl, parameters);
        return result;
    }

    private Response pack(Request gRpcRequest, Object result) {
        Response gRpcResponse = new Response();
        gRpcResponse.setRequestId(gRpcRequest.getRequestId());
        gRpcResponse.setResultResponse(result);
        return gRpcResponse;
    }
}
