package com.example.luhn;

import com.example.luhn.controller.LuhnValidationController;
import com.example.luhn.exceptions.ValidationFailureException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static com.example.luhn.constants.Constants.*;

@SpringBootTest
class LuhnApplicationTests {

	@Autowired
	private LuhnValidationController luhnValidationController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(luhnValidationController).isNotNull();
	}

	@Test
	public void passesValidationWhenValidCreditCardNumberWhichHasOddNumberOfDigitsIsValidated() throws ValidationFailureException{
		Assert.assertEquals(IS_VALID, luhnValidationController.validate("49927398716"));
	}

	@Test
	public void passesValidationWhenValidCreditCardNumberWhichHasEvenNumberOfDigitsIsValidated() throws ValidationFailureException {
		Assert.assertEquals(IS_VALID, luhnValidationController.validate("5420113841119649"));
	}

	@Test
	public void failsValidationWhenNullIsValidated() {
		Assertions.assertThrows(ValidationFailureException.class, () -> {
			luhnValidationController.validate(null);
		});
	}

	@Test
	public void failsValidationWhenCreditCardNumberContainingCharactersIsValidated() {
		Assertions.assertThrows(ValidationFailureException.class, () -> {
			luhnValidationController.validate("1234X1234");
		});
	}

	@Test
	public void failsValidationWhenInvalidCreditCardNumberIsValidated() {
		Assertions.assertThrows(ValidationFailureException.class, () -> {
			luhnValidationController.validate("99927398716");
		});
	}

}
