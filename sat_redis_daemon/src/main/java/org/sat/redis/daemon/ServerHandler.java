package org.sat.redis.daemon;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.SimpleStringRedisMessage;
import lombok.extern.slf4j.Slf4j;
import org.sat.xinception.InceptionException;
import org.sat.inception.RedisInception;

@Slf4j
public class ServerHandler extends ChannelDuplexHandler {
    RedisInception redisInception = new RedisInception();

    public ServerHandler(RedisInception redisInception) {
        this.redisInception = redisInception;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端已经离线");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端已经上线");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            redisInception.checkRule("111");
        } catch (InceptionException e) {
            System.out.println(e.getMessage());
            ctx.writeAndFlush(new SimpleStringRedisMessage(e.getMessage()));
        }
        log.info("转发请求给后端");
        try {
            redisInception.checkRule("");
        } catch (InceptionException e) {
            //返回结果或者预处理
            //邮件警告 ====
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
