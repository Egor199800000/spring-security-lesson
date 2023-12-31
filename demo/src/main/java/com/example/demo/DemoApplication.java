package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;


import java.util.Map;

//@EnableWebSecurity(debug = true)//вкл дебаг-для информативности ошибок
@SpringBootApplication
public class DemoApplication {

	private static final Logger LOGGER= LoggerFactory.getLogger(DemoApplication.class);

	//ctrl+alt+H-посмотреть какие классы реализовывают этот метод
	//ctrl+alt+F7(2 раза) -посмотреть кто вызывает метод

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

//	http://localhost:8080/hello.html заходим на этот адрес с вводом пароля
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http.
//				httpBasic().and()
//				.authorizeHttpRequests() // пользователь должен быть аутенфицирован
//				.anyRequest().authenticated().and() //ко всем запросом имеет доступ аутен-й пользователь
//				.build();
//	}


//		@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		return http
//				.httpBasic(httpBasic->{})
//				.authorizeHttpRequests(authorizeHttpRequests->
//					authorizeHttpRequests
//							.requestMatchers("/public/test","/error").permitAll()
//							.anyRequest().authenticated())//авторизация требуется для всех путей
//					.build();
//
//	}


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

LOGGER.info("AM in securityFilterChain",http.getSharedObject(AuthenticationManager.class));
				http.apply(new MyConfigurer())
						.realName("My custom realm name");
				return http.build();

	}


	//способ получения пользовательских данных в функциональном обработчике Http-запроса
	@Bean
	public RouterFunction<ServerResponse> routerFunction(){
	return RouterFunctions.route()
			.GET("/api/v4/greeting",request -> {
				//получаем из запроса авторизованного пользователя
				UserDetails userDetails=request.principal().map(Authentication.class::cast)
						.map(Authentication::getPrincipal)
						.map(UserDetails.class::cast)
						.orElseThrow();

				return ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(Map.of("greeting","Hello, %s (V4)!"
								.formatted(userDetails.getUsername())));
			})
			.build();
	}


}
