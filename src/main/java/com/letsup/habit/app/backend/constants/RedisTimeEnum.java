package com.letsup.habit.app.backend.constants;

import java.util.concurrent.TimeUnit;

/**
 * redis消息时间常量类
 */
public class RedisTimeEnum {
    /**
     * 过期时间相关枚举
     */
    public static enum ExpireEnum{
        //打卡记录的有效期为1天
        CLOCKIN_RECORD(86400l, TimeUnit.SECONDS)//1天
        ;

        /**
         * 过期时间
         */
        private Long time;
        /**
         * 时间单位
         */
        private TimeUnit timeUnit;

        ExpireEnum(Long time, TimeUnit timeUnit) {
            this.time = time;
            this.timeUnit = timeUnit;
        }

        public Long getTime() {
            return time;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }
    }
}
