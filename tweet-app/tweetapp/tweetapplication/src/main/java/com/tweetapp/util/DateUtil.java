package com.tweetapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tweetapp.constants.BatchConstants;

public class DateUtil {
	public static Date convertToDate(String date) {
		Date parsedDate = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(BatchConstants.DOB_FORMAT);
			parsedDate = dateFormat.parse(date);
			dateFormat.setLenient(false);
		} catch (ParseException parseException) {
			System.err.println("Invalid Date format. Please Enter "
					+ BatchConstants.DOB_FORMAT);
		}
		return parsedDate;
	}
}