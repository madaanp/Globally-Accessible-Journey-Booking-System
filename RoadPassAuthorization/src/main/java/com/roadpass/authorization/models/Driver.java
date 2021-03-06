package com.roadpass.authorization.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

@Document(collection = "Drivers")
@Sharded(shardKey = {"country"})
public class Driver {
	@Id
	private String id;
	private String username;
	private String email;
	private String password;
	private String country;
	private String countryCode;

	public Driver() {
	}

	public Driver(String username, String email, String password, String country, String countryCode) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.country = country;
		this.countryCode = countryCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}