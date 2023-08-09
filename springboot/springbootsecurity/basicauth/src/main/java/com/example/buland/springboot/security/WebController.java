package com.example.buland.springboot.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class WebController {
    
    @RequestMapping("/")
    @ResponseBody //@ResponseBody annotation is what allows this method to directly return the string.
    public String index() {
        return "Its Basic Auth Example!";
    }

}