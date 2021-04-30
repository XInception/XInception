package com.sat.redis.target;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class GetTest extends ClientTest {
    @Test
    void get() throws IOException, InterruptedException {
        //发送get 请求
        XIncRedisClient.run("get aa");
    }

    @Test
    void set() throws IOException ,InterruptedException{
        XIncRedisClient.run("set aa 222222");
        //发送get 请求
        XIncRedisClient.run("se111t aa 222222");
    }

    @Test
    void ttl() throws IOException ,InterruptedException{
        XIncRedisClient.run("ttl aa");
    }
}