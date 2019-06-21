package com.ghj.rpc.core;

import com.ghj.rpc.context.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端的handler
 * @author GeHejun
 * @date 2019-06-21
 */
public class ConsumerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Response response = (Response)msg;
        DataBus.inData(response.getRequestId(), response);
    }
}
