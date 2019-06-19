package com.ghj.rpc.core;

import com.ghj.rpc.context.GrpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 读取服务端返回的信息
 * @author GeHejun
 * @date 2019-06-19
 */
@Deprecated
public class GrpcConsumerHandler extends ChannelInboundHandlerAdapter {

    GrpcConsumerSession session;

    public GrpcConsumerHandler(GrpcConsumerSession consumerConnect) {
        session = consumerConnect;
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)  {
        GrpcResponse grpcResponse = (GrpcResponse)msg;
        session.setGrpcResponse(grpcResponse);
    }

}
