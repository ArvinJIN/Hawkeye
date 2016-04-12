package com.king.hawkeye.web.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;

/**
 * Created by King on 16/3/18.
 */
@Controller
@EnableAutoConfiguration
public class HelloController {

    @RequestMapping("/hello/{name}")
    @ResponseBody
    public String hello(@PathParam("name") String name) {
        return "Hello " + name + " ! ^_^";
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloController.class, args);
    }
}
