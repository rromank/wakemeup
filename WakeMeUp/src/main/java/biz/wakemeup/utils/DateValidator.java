package biz.wakemeup.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidator {
	public DateValidator() {
	}

	public boolean validate(String date, Format df) {
		if (df == Format.BIRTHDAY) {
			return validateBirthday(date);
		}
		if (df == Format.ALARM_DATE) {
			return validateAlarmDate(date);
		}
		return false;
	}

	public boolean validateBirthday(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(Format.BIRTHDAY.value());
		if (date == null) {
			return false;
		}
		try {
			sdf.setLenient(false);
			sdf.parse(date);
		} catch (ParseException ex) {
			return false;
		}
		return true;
	}

	public boolean validateAlarmDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat(
				Format.ALARM_DATE.value());
		if (date == null) {
			return false;
		}
		try {
			sdf.setLenient(false);
			sdf.parse(date);
		} catch (ParseException ex) {
			return false;
		}
		return true;
	}

	public static enum Format {
		BIRTHDAY("yyyy-MM-dd"), ALARM_DATE("yyyy-MM-dd HH:mm");

		private String value;

		Format(String value) {
			this.value = value;
		}

		public String value() {
			return value;
		}
	}
}
