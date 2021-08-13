package com.example.luhn.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.luhn.exceptions.ValidationFailureException;
import com.example.luhn.validator.LuhnValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LuhnValidationController {
	
	@Autowired
	private LuhnValidator validator;

	@RequestMapping("/luhn/{creditCardNumber}")
	public String validate(@PathVariable String creditCardNumber) throws ValidationFailureException {
		validator.checkForIncorrectEntry(creditCardNumber);
		long result = validator.evaluateChecksum(creditCardNumber);
		return validator.validateChecksum(result);
	}
}
