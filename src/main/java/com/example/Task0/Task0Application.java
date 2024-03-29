package com.example.Task0;
import com.example.Task0.dto.CustomnMapper;
import com.example.Task0.security.AuthenticationFilter;
import org.modelmapper.ModelMapper;
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
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}



}
