package com.roadpass.loadbalancer.response;

public class ApiResponse {
    private int responseCode;
    private String message;
    private String username;
    private String emailId;
    private String country;
    private String countryCode;
    
    public ApiResponse() {
	}

	public ApiResponse(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }  

    public ApiResponse(int responseCode, String message, String username, String emailId, String country, String countryCode) {
		super();
		this.responseCode = responseCode;
		this.message = message;
		this.username = username;
		this.emailId = emailId;
		this.country = country;
		this.countryCode = countryCode;
	}

	public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
