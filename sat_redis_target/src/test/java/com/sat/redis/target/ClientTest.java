package com.sat.redis.target;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class  ClientTest {
    XIncRedisClient XIncRedisClient =null;

    @BeforeAll
    void start() {
        XIncRedisClient =new XIncRedisClient();
        XIncRedisClient.start();
    }
}