package com.letsup.habit.app.backend.util.aop;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 自定义尝试切面接口
 */
// 注解会在class字节码文件中存在，在运行时可以通过反射获取到
@Retention(RetentionPolicy.RUNTIME)
//@interface定义一个注解
public @interface IsTryAgain {
    int value() default 3; // 重试次数
}