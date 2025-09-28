package com.example.security.ingress.async;

import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTesterService {


    @SneakyThrows
    @Async("asyncExecutor")
    public void test() {
        Thread.sleep(500);
        System.out.println("test");
        //Async yazmaq cemi 1 eded yeni thread yaratmaq demekdir
    }
}
