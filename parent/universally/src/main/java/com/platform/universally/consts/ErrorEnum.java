package com.platform.universally.consts;

public enum ErrorEnum {
	
	LOGIN_ERROR("error.invalidLogin", "The username or password was not correct"),
	SIGNUP_ERROR("error.signup", "Save user information failed");

	
	private String errorCode;
	private String errorMsg;
	
	private ErrorEnum(String errorCode, String errorMsg){
		this.setErrorCode(errorCode);
		this.setErrorMsg(errorMsg);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
