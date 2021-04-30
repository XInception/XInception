package org.sat.inception;

import lombok.extern.slf4j.Slf4j;
import org.sat.xinception.Inception;
import org.sat.xinception.InceptionException;

@Slf4j
public class RedisInception  implements Inception {
    @Override
    public void checkRule(Object source) throws InceptionException {
        log.info("开始redis规则检查 key分区不存在");
        throw new InceptionException("redis 规则检查异常 key分区不存在");
    }
}
