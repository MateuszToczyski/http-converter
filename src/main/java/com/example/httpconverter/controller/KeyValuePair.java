package com.example.httpconverter.controller;

public class KeyValuePair {
	private final String key;
	private final String value;

	public KeyValuePair(final String key, final String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}
}
