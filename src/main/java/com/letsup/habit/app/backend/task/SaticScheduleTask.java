package com.letsup.habit.app.backend.task;

import com.letsup.habit.app.backend.service.HabHabitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 定时任务控制器
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HabHabitService habHabitService;

    //1.添加定时任务
//    @Scheduled(cron = "0/59 * * * * ?")//指定时间间隔，例如：5秒
    @Scheduled(cron = "0 0 1,2 * * ?")//每天凌晨1点和2点执行
    /**
     * 如果结束日期已经到，但是还没有结束的习惯需要结束掉
     * 结束条件：只能结束1天前还正在进行的习惯
     */
    private void finishExpiredHabit() {
        logger.info("执行习惯结束定时任务开始时间: " + LocalDateTime.now());
        habHabitService.finishExpiredHabit();
        logger.info("执行习惯结束定时任务结束时间: " + LocalDateTime.now());
    }
}
