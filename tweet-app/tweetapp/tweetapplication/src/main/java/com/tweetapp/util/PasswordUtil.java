package com.tweetapp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordUtil {
	public static boolean pwdValidate(String pwd) {
		 String regex = "^(?=.*[0-9])"
                 + "(?=.*[a-z])(?=.*[A-Z])"
                 + "(?=.*[@#$%^&+=])"
                 + "(?=\\S+$).{8,20}$";
		Pattern p = Pattern.compile(regex);
		if (pwd == null) {
			return false;
		}
		Matcher m = p.matcher(pwd);
		return m.matches();
	}
}
