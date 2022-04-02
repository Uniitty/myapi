/**
 * Created by : Alan Nascimento on 4/1/2022
 */
package com.myapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import springfox.documentation.spring.web.*;

@SpringBootApplication
@ComponentScan(excludeFilters =
		{@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SpringfoxWebMvcConfiguration.class)})
public class SpringBootSecurityJwtApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityJwtApplication.class, args);
	}

}
