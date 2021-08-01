package com.example.httpconverter.domain;

public class User {
	private String id;
	private String name;
	private String login;

	public User() {
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLogin() {
		return login;
	}

	@Override
	public String toString() {
		return "User{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", login='" + login + '\'' + '}';
	}
}
