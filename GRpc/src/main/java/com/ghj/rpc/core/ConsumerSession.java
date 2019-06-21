package com.ghj.rpc.core;

import com.ghj.rpc.context.Request;
import com.ghj.rpc.serializer.KryoDecoder;
import com.ghj.rpc.serializer.KryoEncoder;
import com.ghj.rpc.util.PropertiesUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 消费者连接
 *
 * @author GeHejun
 * @date 2019-06-18
 */
public class ConsumerSession {

    public static volatile ConsumerSession consumerSession;

    SocketChannel socketChannel;

    public static ConsumerSession instance() {
        if (consumerSession == null) {
            synchronized(ConsumerSession.class) {
                if (consumerSession == null) {
                    String serverIp = null;
                    Integer serverPort = null;
                    try {
                    serverIp = PropertiesUtil.loadProperties("/application.properties","server-ip");
                    serverPort = Integer.valueOf(PropertiesUtil.loadProperties("/application.properties","server-port"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    consumerSession = new ConsumerSession(serverIp, serverPort);
                }
            }
        }
        return consumerSession;
    }




    public ConsumerSession(String host, int port) {
        init(host, port);
    }

    /**
     * 初始化
     */
    public void init(String host, int port) {
        try {
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) {
                            channel.pipeline().addLast(new KryoEncoder());
                            channel.pipeline().addLast(new KryoDecoder());
                            channel.pipeline().addLast(new ConsumerHandler());
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future =bootstrap.connect(host, port).sync();
            if (future.isSuccess()) {
                socketChannel = (SocketChannel) future.channel();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 发送
     * @return
     */
    public void send(Request request) {
        socketChannel.writeAndFlush(request);
    }

}
