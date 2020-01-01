package com.kmh.models;


/**
 * Handles requests for the application home page.
 */

public class RequestUserModel {
	private String userId, userLevel;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

}
