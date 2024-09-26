package com.buland.example.springboot.banner.SpringBootBannerExamples;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

public class StringBanner implements Banner {

    @java.lang.Override
    public void printBanner(Environment environment, java.lang.Class<?> sourceClass, java.io.PrintStream out) {
        out.println("================================");
        out.println("---------- Learn Tech Verse ----------");
        out.println("================================");
    }
}
