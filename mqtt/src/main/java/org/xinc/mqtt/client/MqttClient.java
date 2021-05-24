package org.xinc.mqtt.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MqttClient {
    boolean isConnect = false;

    public boolean isConnect() {
        return isConnect;
    }

    private EventLoopGroup eventLoopGroup;

    private Bootstrap bootstrap;

    MqttClientProperty property = null;

    Channel downstream;

    Channel c;

    public MqttClient() {

    }

    public MqttClient(MqttClientProperty mysqlClientProperty, Channel downStream) {
        downstream = downStream;
        property = mysqlClientProperty;
        start(mysqlClientProperty, downStream);
    }

    BlockingQueue blockingQueue=new ArrayBlockingQueue(1);


    public void start(MqttClientProperty mysqlClientProperty, Channel channel) {
        downstream = channel;
        property = mysqlClientProperty;
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(
                        new LoggingHandler(),
                        new HttpServerCodec(),
                        new HttpClientCodec()
                );
            }
        });
        var cf = bootstrap.connect(property.server, property.port);

        cf.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("http 服务器连接成功");
            }
        });
        try {
            cf.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!cf.isSuccess()) {
            throw new RuntimeException(cf.cause());
        }
        c = cf.channel();
    }
}
