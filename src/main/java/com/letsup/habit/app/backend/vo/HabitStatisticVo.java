package com.letsup.habit.app.backend.vo;

import java.util.List;

public class HabitStatisticVo {
    private String name;
    private Integer stickDays;//目标坚持天数
    private Integer stickedDays;//已坚持天数
    private List<HabitDailyStatusVo> days;
}