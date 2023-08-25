package com.example.buland.springboot.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Application.java is the entry point into the app. It’s pretty simple in this case, and in many cases. The most important thing is
 * the @SpringBootApplication annotation, which tells your Spring dependencies to bootstrap the whole Spring Boot framework.
 * There is, of course, also the main() method, which is where the Application class is loaded and run by Spring.
 */
@SpringBootApplication
//@EnableWebMvc
//public class Application { //if running with Spring Boot with no container


/**
 * In fact, SpringBootServletInitializer implements the WebApplicationInitializer interface, which is new in Servlet 3.0+ (JSR 315),
 * and the implementation of this interface will automatically set the The implementation of this interface automatically configures
 * the ServletContext and communicates with the Servlet Container, allowing the application to mount smoothly to any Application Server
 * that supports the Servlet Container.
 *
 * This mechanism is only supported from Servlet 3.0 API onwards, while Apache Tomcat supports Servlet 3.0 specification from version 7.0
 * onwards. If you are using Servlet 2.5 or earlier, you still need to register ApplicationContext and DispatcherServlet through web.xml.
 * However, Apache Tomcat 7.0 is a deprecated version, so it should not be easy to encounter.
 */
public class Application extends SpringBootServletInitializer { //if running with a Servlet COntainer

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
