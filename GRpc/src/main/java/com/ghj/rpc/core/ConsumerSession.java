package com.ghj.rpc.core;

import com.ghj.rpc.context.Request;
import com.ghj.rpc.context.Response;
import com.ghj.rpc.serializer.Decoder;
import com.ghj.rpc.serializer.Encoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 消费者连接
 *
 * @author GeHejun
 * @date 2019-06-18
 */
public class ConsumerSession {

    Bootstrap bootstrap;

    ChannelFuture channelFuture;

    Response response;

    public void setResponse(Response response) {
        this.response = response;
    }

    public ConsumerSession() {
        init();
    }

    /**
     * 连接
     * @param host
     * @param port
     */
    public void connect(String host, int port) {
        channelFuture = bootstrap.connect(new InetSocketAddress(host, port));
    }

    /**
     * 初始化
     */
    public void init() {
        try {
            EventLoopGroup group = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) {
                            channel.pipeline().addLast(new Decoder());
                            channel.pipeline().addLast(new Encoder());
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 发送
     * @return
     */
    public Response send(Request request) {
        Channel channel = channelFuture.channel();
        ChannelFuture resultChannelFuture = channel.writeAndFlush(request);
        resultChannelFuture.addListener((future) -> {
            if (future.isSuccess()) {
                this.response = (Response) future.get();
            }
        });
        return response;

    }
}
