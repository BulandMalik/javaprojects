package com.example.springboot.LoggingAndKPIExample.controllers;

import com.example.springboot.LoggingAndKPIExample.services.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@Slf4j //you get log object than you can go log.debug/info/warn etc...
@RestController
@RequestMapping("/hello")
public class HelloController {

    private HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> sayMyName(@PathVariable String name) {
        log.info("starting with sayMyName with name={}",name);
        String message = helloService.sayHello(name);
        return new ResponseEntity<>("{message: "+message+"}", HttpStatus.OK);
    }
}
