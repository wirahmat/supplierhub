package com.supplierhub.supplierhub.persistence.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.supplierhub.supplierhub.persistence.filter.AuthorizationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthorizationFilter authorizationFilter)
			throws Exception {

		http.cors();
		http.csrf().disable();

		http.addFilterAt(authorizationFilter, BasicAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public List<RequestMatcher> matchers() {
		final List<RequestMatcher> matchers = new ArrayList<>();
		matchers.add(new AntPathRequestMatcher("/api/users", HttpMethod.POST.toString()));
		matchers.add(new AntPathRequestMatcher("/api/login", HttpMethod.POST.toString()));
		matchers.add(new AntPathRequestMatcher("/v3/api-docs/**"));
		matchers.add(new AntPathRequestMatcher("/swagger-ui/**"));
		matchers.add(new AntPathRequestMatcher("/swagger-ui.html"));
		return matchers;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> matchers().forEach(r -> {
			web.ignoring().requestMatchers(r);
		});
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer(){

		return new WebMvcConfigurer(){
			@Override
			public void addCorsMappings(CorsRegistry registry){
				registry.addMapping("/**")
					.allowedOrigins("http://localhost:4200")
					.allowedMethods(HttpMethod.GET.name(), 
							HttpMethod.POST.name(), 
							HttpMethod.PUT.name(), 
							HttpMethod.PATCH.name(), 
							HttpMethod.DELETE.name());
			}
		};
	}
	
}
