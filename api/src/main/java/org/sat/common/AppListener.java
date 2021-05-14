package org.xinc.common;


import lombok.extern.slf4j.Slf4j;
import org.xinc.common.utils.CtxUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        CtxUtils.applicationContext=applicationReadyEvent.getApplicationContext();
    }
}
