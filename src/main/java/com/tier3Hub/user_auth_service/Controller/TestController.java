package com.tier3Hub.user_auth_service.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    //access by all
    @GetMapping("/public/hello")
    public String hello()
    {
        return "Hello";
    }

    //access by authenticated
    @GetMapping("/private/hello")
    public String helloPrivate()
    {
        return "Hello Private";
    }
}
