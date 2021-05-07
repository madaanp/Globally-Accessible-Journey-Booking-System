package com.roadpass.authorization.request;

import java.util.Set;
import javax.validation.constraints.*;
 
public class RegisterRequest {
    private String username;
    private String email;
    private String password;    
    private String country;  
    private String countryCode;
    
    public RegisterRequest() {
	}

	public RegisterRequest(String username, String email, String password, String country, String countryCode) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.country = country;
		this.countryCode = countryCode;
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
