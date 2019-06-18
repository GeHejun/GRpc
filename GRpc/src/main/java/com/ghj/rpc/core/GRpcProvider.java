package com.ghj.rpc.core;

import com.ghj.rpc.serializer.GRpcDecoder;
import com.ghj.rpc.serializer.GRpcEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务提供者
 * @author GeHejun
 * @date 2019-06-18
 */
@Data
public class GRpcProvider{
    /**
     * 存放接口和对象之间的映射关系
     */
    private Map<String, Object> mappingMap = new HashMap(16);
    /**
     * 接口
     */
    private Class<?> interfaceClass;
    /**
     * 实现类
     */
    private Object classImplement;
    /**
     * 超时时间
     */
    private int timeOut;
    /**
     * 版本
     */
    private int version;
    /**
     * 序列化方式
     */
    private String type;

    /**
     * 发布服务
     */
    public void publish() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new GRpcEncoder());
                            ch.pipeline().addLast(new GRpcDecoder());
                            ch.pipeline().addLast(new RpcProviderHandler());
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE , true )
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_SNDBUF, 1024)
                    .option(ChannelOption.SO_RCVBUF, 2048);


            ChannelFuture f = serverBootstrap.bind(8888).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}
