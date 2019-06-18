package com.ghj.rpc.serializer;

import com.ghj.rpc.util.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 解码器
 *
 * @author GeHejun
 * @date 2019-06-18
 */
public class GRpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        KryoSerializer kryoSerializer = new KryoSerializer();
        kryoSerializer.deserialize(byteBuf, Object.class);
        channelHandlerContext.flush();
    }
}
