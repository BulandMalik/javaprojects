package com.baeldung.springbootreact;

import com.prescribenow.ffsdk.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(value = {ApplicationConfig.class})
public class SpringBootReactApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactApplication.class, args);
    }
}
