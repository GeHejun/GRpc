package com.ghj.rpc.serializer;




import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class KryoEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object message, ByteBuf out) {
        KryoSerializer.serialize(message, out);
        ctx.flush();
    }
}