package com.example.security.ingress.async;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/async")
public class AsyncTesterController {

    private final AsyncTesterService asyncTesterService;


    @GetMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void test() {
        asyncTesterService.test();
    }

}