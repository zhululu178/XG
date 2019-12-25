package com.letsup.habit.app.backend.constants;

/**
 * API接口返回值常量
 *
 * @author: caidchen
 * @create: 2019-06-17 17:08
 * To change this template use File | Settings | File Templates.
 */
public class ApiResultConstants {
    public static final int SUCCESS = 1000;  //接口请求成功
    public static final int ERROR = 1001;  //接口请求成功
    public static final int OPTIMISTIC_LOCK_ERROR = 1002;//乐观锁更新失败

    public static final int ACCESS_TOKEN_EXPIRED = 412;//访问token过期

    //报错信息
    public enum ExceptionMessage {
        //乐观锁更新失败
        OPTIMISTIC_LOCK_ERROR(1002, "乐观锁更新失败"),

        //登录
        LOGIN_USERNAME_REQUIRED(2001, "请输入用户名"),
        LOGIN_PASSWORD_REQUIRED(2002, "请输入密码"),
        LOGIN_INPUT_ERROR(2003, "用户名或密码不正确"),

        //token
        TOKEN_PARSER_ERROR(2101, "Token解析失败"),

        //家庭成员
        FAMILY_MEMBER_ROLE_EXISTS(3101, "家庭角色已经存在"),
        FAMILY_MEMBER_NAME_EXISTS(3102, "家庭成员名字已经存在"),
        FAMILY_KIDS_NUM_EXCEED(3103, "儿童成员超过" + HabConstants.KID_NUM + "人"),

        //习惯
        HABIT_NOT_EXISTS(4001, "习惯不存在"),
        HABIT_NUM_EXCEED(4002, "习惯数量超过" + HabConstants.HABIT_NUM + "个"),
        HABIT_STICK_DAYS_EXCEED(4003, "已坚持习惯天数超过坚持习惯天数设置"),

        //习惯打卡
        CLOCKIN_BONUS_EXCEED(5001, "星星奖励超过" + HabConstants.STAR_NUM + "颗"),
        CLOCKIN_DATE_EXPIRED(5002, "不在打卡期限内"),

        //收集星星
        COLLECTED_BONUS_NUM_EXCEED(6001, "同一个习惯可收集的星星超过" + HabConstants.HABIT_NUM + "颗"),
        COLLECTED_BONUS_ERROR(6002, "星星收集有误，请重试");

        private int code;
        private String message;

        ExceptionMessage(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}