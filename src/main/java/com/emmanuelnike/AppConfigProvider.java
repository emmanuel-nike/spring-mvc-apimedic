package com.emmanuelnike;

//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan({"com.emmanuelnike.controllers", "com.emmanuelnike.services"})
public class AppConfigProvider {
	
//	@Bean
//	public InternalResourceViewResolver viewResolver(){
//	   InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//	   viewResolver.setPrefix("/WEB-INF/views/");
//	   viewResolver.setSuffix(".jsp");
//	   return viewResolver;
//	}

}
