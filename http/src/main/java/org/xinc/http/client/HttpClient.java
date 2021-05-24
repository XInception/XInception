package org.xinc.http.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;


@Slf4j
public class HttpClient {

    private EventLoopGroup eventLoopGroup;

    private Bootstrap bootstrap;

    HttpClientProperty property = null;

    Channel downstream;

    Channel upstreamChannel;

    Queue<Object> msgs=new LinkedList<>();

    boolean is_connect=false;

    boolean isConnect(){
        return  is_connect;
    }

    public HttpClient(HttpClientProperty mysqlClientProperty, Channel downStream) {

        downstream = downStream;

        property = mysqlClientProperty;

        start(mysqlClientProperty, downStream);
    }

    public void start(HttpClientProperty httpClientProperty, Channel channel) {
        downstream = channel;

        property = httpClientProperty;

        eventLoopGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();

        bootstrap.group(eventLoopGroup);

        bootstrap.channel(NioSocketChannel.class);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(
                        new LoggingHandler(),
                        new HttpResponseDecoder(),
                        new HttpRequestEncoder()
                );
            }
        });

        var cf = bootstrap.connect(property.server, property.port);

        cf.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                System.out.println("http 服务器连接成功");
                future.channel().pipeline().addLast( new HttpClientHandler(channel, httpClientProperty));
                consume();

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
        upstreamChannel = cf.channel();
        log.info("server info:" + upstreamChannel.remoteAddress().toString());
    }


    public  void consume(){
        System.out.println("消费");
        while (true){
            //写入后端
            MsgQueue msgQueue=(MsgQueue)msgs.poll();
            if(msgQueue==null){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            if("write".equals(msgQueue.op)){
                System.out.println("------>"+msgQueue.op);
                System.out.println("------>"+msgQueue.payload);
                upstreamChannel.write(msgQueue.payload);
            }else {
                System.out.println("------>XXX");
                upstreamChannel.flush();
            }


        }
    }
    public void write(Object msg) {
        //写入队列
        if(msg instanceof HttpRequest){
           //改写http host port
        }
        msgs.add(new MsgQueue("write", msg));
    }
    public void flush(){
        msgs.add(new MsgQueue("flush"));
    }

    static class  MsgQueue{
        String op;
        Object payload;

        public MsgQueue(String op) {
            this.op=op;
        }

        public MsgQueue(String op, Object payload) {
            this.op=op;
            this.payload=payload;
        }
    }
}
