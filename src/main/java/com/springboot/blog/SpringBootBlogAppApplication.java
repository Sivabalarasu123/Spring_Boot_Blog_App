package com.springboot.blog;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@EnableWebMvc
@SpringBootApplication
public class SpringBootBlogAppApplication
{

	@Bean
	ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	//DOcket Bean - responsible to define customized behaviour
	@Bean
	Docket SwaggerConfig() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/api/posts/*"))
				.apis(RequestHandlerSelectors.basePackage("com.springboot.blog"))
				.build()
				.apiInfo(apiCustomerData());
				
	}
	
	private ApiInfo apiCustomerData() {
		return new ApiInfo(
				"Realtime Blog Application",
				"Blog Application with CRUD Operations and posts and comments Controller",
				"1.1",
				"Blog App Service Terms",
				new Contact("Siva","","sivabalarasu99@gmail.com"),
				"siva.com" , "", 
				Collections.emptyList()
				);
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBlogAppApplication.class, args);
	}


}
