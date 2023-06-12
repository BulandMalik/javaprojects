package com.example.multimodule.application;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.multimodule.service.MyService;

@SpringBootTest
@Slf4j //Logging is an important part of development that records the events that the system is performing at any given point in time.
public class DemoApplicationTest {

	//Logger log = LoggerFactory.getLogger(DemoApplicationTest.class);

	@Autowired
	private MyService myService;

	@Test
	public void contextLoads() {
		log.info("myService.message()::",myService.message());
		assertThat(myService.message()).isNotNull();
	}

}
