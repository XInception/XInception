package org.xinc.http;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.xinc.http.client.HttpClient;
import org.xinc.http.client.HttpClientProperty;


import java.util.Map;

@Slf4j
public class HttpUpstreamPool extends BaseKeyedPooledObjectFactory<Map<String, Object>, HttpClient> {

    @Override
    public HttpClient create(Map<String, Object> stringObjectMap) throws Exception {
        log.info("获取客户端");
        return new HttpClient(new HttpClientProperty("/application-client.properties"),(Channel) stringObjectMap.get("downStream"));
    }

    @Override
    public PooledObject<HttpClient> wrap(HttpClient client) {
        return new DefaultPooledObject<>(client);
    }
}
