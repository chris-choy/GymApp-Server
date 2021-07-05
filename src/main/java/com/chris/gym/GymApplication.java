package com.chris.gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
//@ComponentScan("com.chris.gym.config")
public class GymApplication {

	public static void main(String[] args) {
		SpringApplication.run(GymApplication.class, args);
//		final ApplicationContext applicationContext = SpringApplication.run(GymApplication.class, args);
//		Plan.setApplicationContext(applicationContext);
	}

}
