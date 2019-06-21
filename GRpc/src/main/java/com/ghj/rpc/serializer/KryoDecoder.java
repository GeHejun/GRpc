package com.ghj.rpc.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author GeHejun
 * @date 2019-06-21
 */
public class KryoDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        Object obj = KryoSerializer.deserialize(in);
        out.add(obj);
    }
}