package com.platform.universally.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.platform.universally.controller.vo.LoginCommand;

public class LoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return LoginCommand.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username.empty", "Please specify a username.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.empty", "Please specify a password.");
	}

}
