package com.letsup.habit.app.backend.constants;

/**
 * reids的key常量
 */
public class RedisConstant {
    //打卡记录的起始key
    public static final String CLOCKIN_RECORD ="CLOCKIN_RECORD_";
    //家庭今日习惯
    public static final String FAMILY_HABIT_TODAY_RECORD ="FAMILY_HABIT_TODAY_RECORD_";
    //家庭明日习惯
    public static final String FAMILY_HABIT_TOMORROW_RECORD ="FAMILY_HABIT_TOMORROW_RECORD_";
    //家庭昨天习惯
    public static final String FAMILY_HABIT_YESTERDAY_RECORD ="FAMILY_HABIT_YESTERDAY_RECORD_";
    //家庭昨天之前的习惯
    public static final String FAMILY_HABIT_BEFORE_YESTERDAY_RECORD ="FAMILY_HABIT_BEFORE_YESTERDAY_RECORD_";
}
