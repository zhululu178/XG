package com.letsup.habit.app.backend.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtil {

    public static final String default_pattern = "yyyy-MM-dd HH:mm:ss";
    public static final String default_short_pattern = "yyyy-MM-dd";
    public static SimpleDateFormat formatter = new SimpleDateFormat(default_pattern);
    public static SimpleDateFormat short_formatter = new SimpleDateFormat(default_short_pattern);

    /**
     * 将短时间格式字符串转换为时间
     *
     * @param strDate
     * @return
     */
    public static Date strToDate(String strDate) {
        ParsePosition pos = new ParsePosition(0);
        return formatter.parse(strDate, pos);
    }

    /**
     * 将短时间格式字符串转换为时间
     *
     * @param strDate
     * @return
     */
    public static Date shortStrToDate(String strDate) {
        ParsePosition pos = new ParsePosition(0);
        return short_formatter.parse(strDate, pos);
    }

    /**
     * 将短时间格式字符串转换为时间
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        return formatter.format(date);
    }

    /**
     * 将短时间格式字符串转换为时间
     * @param date
     * @return
     */
    public static String dateToStr(Date date, String formatStr) {
        SimpleDateFormat formatter1 = new SimpleDateFormat(formatStr);
        return formatter1.format(date);
    }

    public static long getDaySub(String beginDateStr,String endDateStr) {
        long day = 0;
        Date beginDate;
        Date endDate;
        try {
            beginDate = short_formatter.parse(beginDateStr);
            endDate = short_formatter.parse(endDateStr);
            day = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }

    /**
     * 将短时间格式字符串转换为时间
     * @param date
     * @return
     */
    public static String addDays(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        return short_formatter.format(calendar.getTime());
    }

    /**
     * 获得昨天的时间字符串
     * @return
     */
    public static String getYesterdayStr() {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, -1);
        return short_formatter.format(calendar.getTime());
    }

    /**
     * 获得昨天的时间字符串
     * @return
     */
    public static String getTomorrowStr() {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return short_formatter.format(calendar.getTime());
    }

    /**
     * 获得今天的时间字符串
     * @return
     */
    public static String getTodayStr() {
        Calendar calendar=Calendar.getInstance();
        return short_formatter.format(calendar.getTime());
    }

    /**
     * 获得今天的时间，没有时分秒
     * @return
     */
    public static Date getToday() {
        Calendar cal1 = Calendar.getInstance();
        // 将时分秒,毫秒域清零
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        return cal1.getTime();
    }

    /**
     * 获得年龄
     * @param birthDay
     * @return
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) { //出生日期晚于当前时间，无法计算
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);  //当前年份
        int monthNow = cal.get(Calendar.MONTH);  //当前月份
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH); //当前日期
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;   //计算整岁数
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;//当前日期在生日之前，年龄减一
            } else {
                age--;//当前月份在生日之前，年龄减一
            }
        }
        return age;
    }
}