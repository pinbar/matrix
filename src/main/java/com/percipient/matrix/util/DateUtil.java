package com.percipient.matrix.util;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class DateUtil {

    public static enum DAYS_OF_WEEK {
        MONDAY("monday"), TUESDAY("tuesday"), WEDNESDAY("wednesday"), THURSDAY(
                "thursday"), FRIDAY("friday"), SATURDAY("saturday"), SUNDAY(
                "sunday");

        private String day;
        private static final Map<String, DAYS_OF_WEEK> lookup = new HashMap<String, DAYS_OF_WEEK>();
        private static final Map<DAYS_OF_WEEK, Integer> lookupInteger = new HashMap<DAYS_OF_WEEK, Integer>();

        static {
            for (DAYS_OF_WEEK s : EnumSet.allOf(DAYS_OF_WEEK.class))
                lookup.put(s.getDay(), s);
            Integer i = 1;
            for (DAYS_OF_WEEK s : EnumSet.allOf(DAYS_OF_WEEK.class))
                lookupInteger.put(s, i);
            i++;

        }

        private DAYS_OF_WEEK(String val) {
            day = val;
        }

        public String getDay() {
            return day;
        }

        public Integer getDayAsInteger() {
            return lookupInteger.get(this);
        }

        public static DAYS_OF_WEEK get(String val) {
            return lookup.get(val);
        }
    };

    private final static Format formatter = new SimpleDateFormat("MM-dd-yyyy");

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

    public Date getCurrentWeekEndingDate() {
        Calendar cal = Calendar.getInstance(LocaleContextHolder.getLocale());
        return getWeekEndingDate(getAsDate(getAsString(cal.getTime())));
    }

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance(LocaleContextHolder.getLocale());
        return formatter.format(cal.getTime());
    }
    
    public Date getWeekEndingDate(Date today) {
        Calendar cal = Calendar.getInstance(LocaleContextHolder.getLocale());
        cal.setTime(today);
        int dow = cal.get(Calendar.DAY_OF_WEEK);
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

    public int getMonthLastDate(int month, int year) {
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
