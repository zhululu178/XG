package com.letsup.habit.app.backend.util;

import com.letsup.habit.app.backend.constants.HabitFrequencyEnum;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HabitEndDateUtil {

    /**
     * 根据今天获得习惯的结束日期
     * @param freqType
     * @param freqDays
     * @param stickDays
     * @return
     */
    public static Date generateEndDateFromToday(String freqType, String freqDays, int stickDays) {
        return generateEndDate(new Date(), freqType, freqDays, stickDays);
    }

    public static void main1(String[] args) {
        Date endDate = generateEndDateFromToday(HabitFrequencyEnum.F1.name(), "1,2,4", 7);
        System.out.printf("%1$tF %1$tT\n", endDate);
        endDate = generateEndDateFromToday(HabitFrequencyEnum.F7.name(), "2", 7);
        System.out.printf("%1$tF %1$tT\n", endDate);
        endDate = generateEndDateFromToday(HabitFrequencyEnum.FX.name(), "2", 7);
        System.out.printf("%1$tF %1$tT\n", endDate);
    }

    /**
     * 根据指定的日期获得习惯的结束日期
     * @param startDateStr
     * @param freqType
     * @param freqDays
     * @param stickDays
     * @return
     */
    public static String generateEndDate(String startDateStr, String freqType, String freqDays, int stickDays) {
        Date startDate = DateTimeUtil.shortStrToDate(startDateStr);
        Date endDate = generateEndDate(startDate, freqType, freqDays, stickDays);
        if(endDate != null) {
            return DateTimeUtil.dateToStr(endDate, DateTimeUtil.default_short_pattern);
        }
        return null;
    }

    /**
     * 根据指定的日期获得习惯的结束日期
     * @param startDate
     * @param freqType
     * @param freqDays
     * @param stickDays
     * @return
     */
    public static Date generateEndDate(Date startDate, String freqType, String freqDays, int stickDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if(HabitFrequencyEnum.F1.name().equals(freqType)) {//每天
            String[] daysArr = freqDays.split(",");
            List<String> daysList = Arrays.asList(daysArr);
            while (stickDays > 0) {
                int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                if(day == 0) {//星期天
                    day = 7;
                }
                if(daysList.contains(day + "")) {
                    stickDays--;
                }
                if(stickDays > 0) {
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }
            }
        } else {
            int cycle = Integer.parseInt(freqDays);
            double frequency = 1;
            if(HabitFrequencyEnum.F7.name().equals(freqType)) {
                cycle = 7;
                frequency = Double.parseDouble(freqDays);
            } else if(HabitFrequencyEnum.F31.name().equals(freqType)) {
                cycle = 30;
                frequency = Double.parseDouble(freqDays);
            }
            if(frequency >= stickDays) {//如果周期比坚持的天数大
                calendar.add(Calendar.DAY_OF_MONTH, cycle - 1);
            } else {
                double aa = Math.ceil(stickDays/frequency);
                calendar.add(Calendar.DAY_OF_MONTH, (int) (cycle*(aa)) - 1);
            }
        }
        return calendar.getTime();
    }
}
