package com.letsup.habit.app.backend.dao.entity;

import java.util.Date;

public class HabAppHabitDailyRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.id
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.habit_id
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private Long habitId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.family_id
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private Long familyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.executor
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private Long executor;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.is_sticked
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private String isSticked;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.bonus
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private Integer bonus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.has_log
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private String hasLog;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.collected_bonus
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private Integer collectedBonus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.actual_daily_count
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private Integer actualDailyCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.clockin_date
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private String clockinDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.serial_start_date
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private String serialStartDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.not_do_today
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private String notDoToday;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.version
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private Long version;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.creator
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.gmt_create
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.modifier
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.gmt_modified
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private Date gmtModified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_daily_record.is_deleted
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    private String isDeleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.id
     *
     * @return the value of hab_app_habit_daily_record.id
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.id
     *
     * @param id the value for hab_app_habit_daily_record.id
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.habit_id
     *
     * @return the value of hab_app_habit_daily_record.habit_id
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public Long getHabitId() {
        return habitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.habit_id
     *
     * @param habitId the value for hab_app_habit_daily_record.habit_id
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setHabitId(Long habitId) {
        this.habitId = habitId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.family_id
     *
     * @return the value of hab_app_habit_daily_record.family_id
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public Long getFamilyId() {
        return familyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.family_id
     *
     * @param familyId the value for hab_app_habit_daily_record.family_id
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setFamilyId(Long familyId) {
        this.familyId = familyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.executor
     *
     * @return the value of hab_app_habit_daily_record.executor
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public Long getExecutor() {
        return executor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.executor
     *
     * @param executor the value for hab_app_habit_daily_record.executor
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setExecutor(Long executor) {
        this.executor = executor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.is_sticked
     *
     * @return the value of hab_app_habit_daily_record.is_sticked
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public String getIsSticked() {
        return isSticked;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.is_sticked
     *
     * @param isSticked the value for hab_app_habit_daily_record.is_sticked
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setIsSticked(String isSticked) {
        this.isSticked = isSticked == null ? null : isSticked.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.bonus
     *
     * @return the value of hab_app_habit_daily_record.bonus
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public Integer getBonus() {
        return bonus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.bonus
     *
     * @param bonus the value for hab_app_habit_daily_record.bonus
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.has_log
     *
     * @return the value of hab_app_habit_daily_record.has_log
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public String getHasLog() {
        return hasLog;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.has_log
     *
     * @param hasLog the value for hab_app_habit_daily_record.has_log
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setHasLog(String hasLog) {
        this.hasLog = hasLog == null ? null : hasLog.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.collected_bonus
     *
     * @return the value of hab_app_habit_daily_record.collected_bonus
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public Integer getCollectedBonus() {
        return collectedBonus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.collected_bonus
     *
     * @param collectedBonus the value for hab_app_habit_daily_record.collected_bonus
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setCollectedBonus(Integer collectedBonus) {
        this.collectedBonus = collectedBonus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.actual_daily_count
     *
     * @return the value of hab_app_habit_daily_record.actual_daily_count
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public Integer getActualDailyCount() {
        return actualDailyCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.actual_daily_count
     *
     * @param actualDailyCount the value for hab_app_habit_daily_record.actual_daily_count
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setActualDailyCount(Integer actualDailyCount) {
        this.actualDailyCount = actualDailyCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.clockin_date
     *
     * @return the value of hab_app_habit_daily_record.clockin_date
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public String getClockinDate() {
        return clockinDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.clockin_date
     *
     * @param clockinDate the value for hab_app_habit_daily_record.clockin_date
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setClockinDate(String clockinDate) {
        this.clockinDate = clockinDate == null ? null : clockinDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.serial_start_date
     *
     * @return the value of hab_app_habit_daily_record.serial_start_date
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public String getSerialStartDate() {
        return serialStartDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.serial_start_date
     *
     * @param serialStartDate the value for hab_app_habit_daily_record.serial_start_date
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setSerialStartDate(String serialStartDate) {
        this.serialStartDate = serialStartDate == null ? null : serialStartDate.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.not_do_today
     *
     * @return the value of hab_app_habit_daily_record.not_do_today
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public String getNotDoToday() {
        return notDoToday;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.not_do_today
     *
     * @param notDoToday the value for hab_app_habit_daily_record.not_do_today
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setNotDoToday(String notDoToday) {
        this.notDoToday = notDoToday == null ? null : notDoToday.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.version
     *
     * @return the value of hab_app_habit_daily_record.version
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public Long getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.version
     *
     * @param version the value for hab_app_habit_daily_record.version
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.creator
     *
     * @return the value of hab_app_habit_daily_record.creator
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.creator
     *
     * @param creator the value for hab_app_habit_daily_record.creator
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.gmt_create
     *
     * @return the value of hab_app_habit_daily_record.gmt_create
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.gmt_create
     *
     * @param gmtCreate the value for hab_app_habit_daily_record.gmt_create
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.modifier
     *
     * @return the value of hab_app_habit_daily_record.modifier
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.modifier
     *
     * @param modifier the value for hab_app_habit_daily_record.modifier
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.gmt_modified
     *
     * @return the value of hab_app_habit_daily_record.gmt_modified
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.gmt_modified
     *
     * @param gmtModified the value for hab_app_habit_daily_record.gmt_modified
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_daily_record.is_deleted
     *
     * @return the value of hab_app_habit_daily_record.is_deleted
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_daily_record.is_deleted
     *
     * @param isDeleted the value for hab_app_habit_daily_record.is_deleted
     *
     * @mbggenerated Mon Dec 09 17:35:19 CST 2019
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }
}