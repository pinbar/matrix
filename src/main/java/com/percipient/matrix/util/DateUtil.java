package com.percipient.matrix.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	private final Format formatter = new SimpleDateFormat("MM-dd-yyyy");;

	public String getAsString(Date date) {

		return formatter.format(date);
	}

	public Date getAsDate(String dateString) {

		Date date = null;
		try {

			date = (Date) formatter.parseObject(dateString);
		} catch (ParseException e) {
			System.out.println("error parsing to date for String: "
					+ dateString);
		}
		return date;
	}

	public int getCurrentDay() {
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public  Date getCurrentWeekEndingDate() {
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		return getWeekEndingDate(getAsDate(getAsString(cal.getTime())));
	}

	public  Date getWeekEndingDate(Date today) {
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.setTime(today);
		int dow = getCurrentDay();
		while (dow != Calendar.SUNDAY) {
			int date = cal.get(Calendar.DATE);
			int month = cal.get(Calendar.MONTH);
			int year = cal.get(Calendar.YEAR);
			if (date == getMonthLastDate(month, year)) {
				if (month == Calendar.DECEMBER) {
					month = Calendar.JANUARY;
					cal.set(Calendar.YEAR, year + 1);
				} else {
					month++;
				}
				cal.set(Calendar.MONTH, month);
				date = 1;
			} else {
				date++;
			}
			cal.set(Calendar.DATE, date);
			dow = cal.get(Calendar.DAY_OF_WEEK);
		}
		return cal.getTime();
	}

	public  int getMonthLastDate(int month, int year) {
		switch (month) {
		case Calendar.JANUARY:
		case Calendar.MARCH:
		case Calendar.MAY:
		case Calendar.JULY:
		case Calendar.AUGUST:
		case Calendar.OCTOBER:
		case Calendar.DECEMBER:
			return 31;

		case Calendar.APRIL:
		case Calendar.JUNE:
		case Calendar.SEPTEMBER:
		case Calendar.NOVEMBER:
			return 30;

		default: // Calendar.FEBRUARY
			return year % 4 == 0 ? 29 : 28;
		}
	}

}
