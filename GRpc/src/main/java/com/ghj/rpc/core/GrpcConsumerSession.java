package com.ghj.rpc.core;

import com.ghj.rpc.context.GrpcRequest;
import com.ghj.rpc.context.GrpcResponse;
import com.ghj.rpc.serializer.GrpcDecoder;
import com.ghj.rpc.serializer.GrpcEncoder;
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
public class GrpcConsumerSession {

    Bootstrap bootstrap;

    ChannelFuture channelFuture;

    GrpcResponse grpcResponse;

    public void setGrpcResponse(GrpcResponse grpcResponse) {
        this.grpcResponse = grpcResponse;
    }

    public GrpcConsumerSession() {
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
                            channel.pipeline().addLast(new GrpcDecoder());
                            channel.pipeline().addLast(new GrpcEncoder());
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
    public GrpcResponse send(GrpcRequest grpcRequest) {
        Channel channel = channelFuture.channel();
        ChannelFuture resultChannelFuture = channel.writeAndFlush(grpcRequest);
        resultChannelFuture.addListener((future) -> {
            if (future.isSuccess()) {
                this.grpcResponse = (GrpcResponse) future.get();
            }
        });
        return grpcResponse;

    }
}
