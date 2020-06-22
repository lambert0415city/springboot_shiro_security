package com.cs.controller;

import com.cs.service.AsynService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: szh
 * @since:
 */

@RestController
public class AsyncController {

    @Autowired
    private AsynService asynService;

    @GetMapping("/hello")
    public String hello(){
        asynService.hello();
        return "ok";
    }
}
