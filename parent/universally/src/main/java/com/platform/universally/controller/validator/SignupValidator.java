package com.platform.universally.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.platform.universally.controller.vo.SignupCommand;

public class SignupValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return SignupCommand.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username.empty", "Please specify a username.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.empty", "Please specify a password.");
	}

}
