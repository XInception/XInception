package org.sat.redis.daemon;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.redis.*;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.sat.inception.RedisInception;

import java.io.IOException;

@Slf4j
public class Daemon {

    EventLoopGroup bossGroup = new NioEventLoopGroup(1);

    EventLoopGroup workerGroup = new NioEventLoopGroup();

    RedisInception redisInception=new RedisInception();

    ChannelFuture f = null;

    DaemonProperty property = null;

    public static void main(String[] args) throws IOException {
        Daemon b = new Daemon();
        b.start(new DaemonProperty("/application.properties"));
    }

    public void start(DaemonProperty daemonProperty) {
        property = daemonProperty;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LoggingHandler());
                            pipeline.addLast(new RedisDecoder());
                            pipeline.addLast(new RedisEncoder());
                            pipeline.addLast(new ServerHandler(redisInception));
                        }
                    });
            f = b.bind(property.server, property.port);
            log.info("REDIS DEMON 启动完成 {} {} ", property.server, property.port);
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }



}
