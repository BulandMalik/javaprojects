package com.example.springboot.LoggingAndKPIExample.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j //you get log object than you can go log.debug/info/warn etc...
@Service // It is used to mark the class as a service provider. So overall @Service annotation is used with classes that provide some business functionalities. Spring context will autodetect these classes when annotation-based configuration and classpath scanning is used.
public class HelloService {

    public HelloService() {

    }

    public String sayHello(String name) {
        log.info("inside Service->sayHello with name={}",name);
        log.info("inside Service->sayHello ... calling IDMe");
        log.info("inside Service->sayHello ... calling Agora");
        log.info("inside Service->sayHello ... calling the Repo");
        log.info("inside Service->sayHello with name={}",name);
        return ("Hello, "+name);
    }
}
