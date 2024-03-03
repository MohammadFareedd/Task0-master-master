package com.example.Task0;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class Task0Application {

	public static void main(String[] args) {
		SpringApplication.run(Task0Application.class, args);




	}
	@Bean
	public AntPathMatcher pathMatcher(){
		return new AntPathMatcher();
	}

}
