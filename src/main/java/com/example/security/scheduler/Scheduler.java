package com.example.security.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Scheduler {


    @Bean
    @Scheduled(cron = "0 */1 * * * *")
    public void delete() {
        log.info("Scheduler is done");
    }

}
