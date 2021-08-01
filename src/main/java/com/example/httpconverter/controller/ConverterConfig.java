package com.example.httpconverter.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class ConverterConfig {

	@Bean
	public HttpMessageConverter<Object> genericHttpMessageConverter() {
		return new GenericHttpMessageConverter();
	}
}
