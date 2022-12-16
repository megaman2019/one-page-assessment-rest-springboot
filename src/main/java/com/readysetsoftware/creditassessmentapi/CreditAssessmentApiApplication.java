package com.readysetsoftware.creditassessmentapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("com.readysetsoftware.creditassessmentapi.data.repository")
//@EntityScan("com.readysetsoftware.creditassessmentapi.data.model")
//@ComponentScan("com.readysetsoftware.creditassessmentapi")
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class CreditAssessmentApiApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CreditAssessmentApiApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CreditAssessmentApiApplication.class);
	}
}
