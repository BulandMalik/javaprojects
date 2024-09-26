package com.buland.springboot.emailfunction.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Builder
@Getter
public class MyError implements Serializable {
    private Integer id;
    private Integer unparssedMessageId;
    private String errorNumber;
    private String errorDetails;
    private String tid;
    private String link;

}
