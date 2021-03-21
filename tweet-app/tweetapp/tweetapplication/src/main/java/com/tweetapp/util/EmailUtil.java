package com.tweetapp.util;

import java.util.regex.Matcher;


import com.tweetapp.constants.BatchConstants;

public class EmailUtil {
	public static boolean emailValidate(String emailStr) {
		Matcher matcher = BatchConstants.VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
		return matcher.find();
	}
}