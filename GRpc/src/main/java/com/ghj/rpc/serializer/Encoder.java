package com.ghj.rpc.serializer;

import com.ghj.rpc.util.KryoSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码器
 * @date 2019-06-18
 * @author GeHejun
 */
public class Encoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf)  {
        KryoSerializer kryoSerializer = new KryoSerializer();
        kryoSerializer.serialize(o, byteBuf);
        channelHandlerContext.flush();
    }

}
