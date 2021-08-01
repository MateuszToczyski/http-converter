package com.example.httpconverter.controller;

import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.lang.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenericHttpMessageConverter extends AbstractGenericHttpMessageConverter<Object> {

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		return List.of(MediaType.ALL);
	}

	@Override
	public final Object read(Type type, @Nullable Class<?> contextClass, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		return readResolved(GenericTypeResolver.resolveType(type, contextClass), inputMessage);
	}

	@Override
	protected final Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		return readResolved(clazz, inputMessage);
	}

	private Object readResolved(Type resolvedType, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {

		StringBuilder builder = new StringBuilder();

		try (Reader reader = new BufferedReader(new InputStreamReader(inputMessage.getBody(),
				Charset.forName(StandardCharsets.UTF_8.name())))) {
			int c = 0;

			while ((c = reader.read()) != -1) {
				builder.append((char) c);
			}
		}

		String[] keyValuePairs = builder.toString().split(",");

		List<KeyValuePair> parsedKeyValuePairs = Arrays
				.stream(keyValuePairs)
				.map(this::parseKeyValuePair)
				.collect(Collectors.toList());

		try {
			return createObject(parsedKeyValuePairs, resolvedType);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}

	private KeyValuePair parseKeyValuePair(String pair) {
		String[] pairArray = pair.split(":");
		String key = pairArray[0];
		String value = pairArray[1];
		return new KeyValuePair(key, value);
	}

	private Object createObject(List<KeyValuePair> fieldKeyValuePairs, Type type)
			throws ReflectiveOperationException {
		final Class<?> clazz = Class.forName(type.getTypeName());
		final Object obj = clazz.getConstructor().newInstance();
		for (KeyValuePair pair : fieldKeyValuePairs) {
			Field field = clazz.getDeclaredField(pair.getKey());
			field.setAccessible(true);
			field.set(obj, pair.getValue());
		}
		return obj;
	}

	@Override
	protected void writeInternal(final Object o, final Type type, final HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
	}
}
