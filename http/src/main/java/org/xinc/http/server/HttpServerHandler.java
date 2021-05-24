package org.xinc.http.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.xinc.function.InceptionException;
import org.xinc.http.HttpInception;
import org.xinc.http.HttpUpstreamPool;
import org.xinc.http.RequestUtils;
import org.xinc.http.client.HttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Slf4j
public class HttpServerHandler extends SimpleChannelInboundHandler<Object> {


    HttpInception httpInception = new HttpInception();

    private HttpRequest request;
    
    StringBuilder responseData = new StringBuilder();

    KeyedObjectPool<Map<String, Object>, HttpClient> upstreamPool = new GenericKeyedObjectPool<>(new HttpUpstreamPool());

    HashMap<String, Object> config = new HashMap<>();
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端已经离线 返还 http 句柄");
        HttpClient upstreamClient = (HttpClient) ctx.channel().attr(AttributeKey.valueOf("http_connect")).get();
        upstreamPool.returnObject(config, upstreamClient);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端已经上线 获取http 句柄");
        config.put("downStream",ctx.channel());
        HttpClient upstreamClient = upstreamPool.borrowObject(config);
        ctx.channel().attr(AttributeKey.valueOf("http_connect")).set(upstreamClient);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        System.out.println("请求类型");
        HttpClient upstreamClient = (HttpClient) ctx.channel().attr(AttributeKey.valueOf("http_connect")).get();
        System.out.println(msg);
        try {
            httpInception.checkRule(msg);
        } catch (InceptionException e) {
            e.printStackTrace();
            LastHttpContent trailer = (LastHttpContent) msg;
            responseData.append(RequestUtils.prepareLastResponse(request, trailer));
            writeResponse(ctx, trailer, responseData);
            return;
        }

        upstreamClient.write(msg);

        if (msg instanceof LastHttpContent) {
            upstreamClient.flush();
        }
    }

    private void writeResponse(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE, Unpooled.EMPTY_BUFFER);
        ctx.write(response);
    }

    private void writeResponse(ChannelHandlerContext ctx, LastHttpContent trailer, StringBuilder responseData) {
        boolean keepAlive = HttpUtil.isKeepAlive(request);

        FullHttpResponse httpResponse = new DefaultFullHttpResponse(HTTP_1_1, ((HttpObject) trailer).decoderResult()
                .isSuccess() ? OK : BAD_REQUEST, Unpooled.copiedBuffer(responseData.toString(), CharsetUtil.UTF_8));

        httpResponse.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");

        if (keepAlive) {
            httpResponse.headers()
                    .setInt(HttpHeaderNames.CONTENT_LENGTH, httpResponse.content()
                            .readableBytes());
            httpResponse.headers()
                    .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        ctx.write(httpResponse);

        if (!keepAlive) {
            ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                    .addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}