package org.xinc.http.client;

import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class HttpClientHandler extends ChannelDuplexHandler {


    Channel downStreamChanel = null;
    HttpClientProperty property = null;

    public HttpClientHandler(Channel downStreamChanel, HttpClientProperty property) {
        this.downStreamChanel = downStreamChanel;
        this.property = property;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("转发给前端");
        downStreamChanel.write(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.print("发生异常: ");
        cause.printStackTrace(System.err);
        ctx.close();
    }
}
