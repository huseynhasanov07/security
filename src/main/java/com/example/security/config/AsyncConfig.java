package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "asyncExecutor")
    public Executor asyncExecutor() {
        var cores = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cores * 2);
        executor.setMaxPoolSize(cores * 5);
        executor.setQueueCapacity(cores * 10);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
        //highload applarda tenzimleme ucun que kapasiti dolanda bezi userlere exception atir digerlerinde prosess davam edir
    }
}
