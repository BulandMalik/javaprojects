package com.thisara.myfleet;

import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;

@Configuration
public class ModelMapperClass {

	private Logger logger = Logger.getLogger(ModelMapperClass.class.getName());

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@EventListener
	public void showBeansRegistered(ApplicationReadyEvent event) {
		String[] beanNames = event.getApplicationContext().getBeanDefinitionNames();

		for (String beanName : beanNames) {
			logger.info("{context - " + beanName + " }");
		}
	}
}
