package com.example.luhn.validator;

import com.example.luhn.exceptions.ValidationFailureException;
import com.example.luhn.validator.support.CharacterIdentifier;
import com.example.luhn.validator.support.StringSpaceRemover;
import org.springframework.stereotype.Service;
import static com.example.luhn.constants.Constants.*;

@Service
public class LuhnValidator {

	public String validateChecksum(long result) throws ValidationFailureException {
		if (result % 10 != 0){
			throw new ValidationFailureException();
		} else {
			return IS_VALID;
		}
	}

	public long evaluateChecksum(String creditCardNumber) {
		long luhnValue = 0;
		creditCardNumber = StringSpaceRemover.removeSpaces(creditCardNumber);
		int i = creditCardNumber.length() - 1;
		
		while(i>=0){
			String singleDigit = Character.toString(creditCardNumber.charAt(i));
			if (characterAtIndexShouldBeDoubled(creditCardNumber, i)){
				luhnValue += calculateDoubledValue(singleDigit);
			} else {
				luhnValue += Integer.parseInt(singleDigit);
			}
			i--;
		}
		return luhnValue;
	}

	public int calculateDoubledValue(String input) {
		int value = Integer.parseInt(input);
		int result = value * 2;
		if (result > 9){
			result = 1 + result % 10;
		} 
		return result;
	}

	public boolean characterAtIndexShouldBeDoubled(String creditCardNumber, int index) {
		if ((creditCardNumber.length() - index) % 2 == 1){
			return false;
		}
		return true;
	}

	public void checkForIncorrectEntry(String creditCardNumber) throws ValidationFailureException {
		if (creditCardNumber == null  ||
			creditCardNumber.equals("") ||
		    CharacterIdentifier.containsCharacters(creditCardNumber)){
			throw new ValidationFailureException();
		}
		
	}
}
