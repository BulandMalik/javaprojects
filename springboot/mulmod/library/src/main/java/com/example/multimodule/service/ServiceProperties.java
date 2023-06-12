package com.example.multimodule.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import org.apache.commons.lang3.Validate;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties("service")
/*@PropertySource(name = "props", value = {
		"classpath:/myservice.yaml",
		"classpath:/myservice-*.properties",
}, ignoreResourceNotFound = true)*/
@PropertySource(value = "classpath:myservice.yaml", factory = YamlPropertySourceFactory.class)

@Slf4j
public class ServiceProperties {

	/**
	 * A message for the service.
	 */
	//@Value("service.message")
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@PostConstruct
	private void printAllProps(){
		log.info("message:[{}]",message);
	}

	@PostConstruct
	public void validate(){
		Validate.notEmpty(message, "Configure message");
	}

}
