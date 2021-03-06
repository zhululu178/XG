package com.letsup.habit.app.backend.dao.entity;

import java.util.Date;

public class HabAppHabitClockinRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_clockin_record.id
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_clockin_record.daily_record_id
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    private Long dailyRecordId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_clockin_record.bonus
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    private Integer bonus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_clockin_record.each_count
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    private Integer eachCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_clockin_record.creator
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_clockin_record.gmt_create
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_clockin_record.modifier
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_clockin_record.gmt_modified
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    private Date gmtModified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_clockin_record.is_deleted
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    private String isDeleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_clockin_record.id
     *
     * @return the value of hab_app_habit_clockin_record.id
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_clockin_record.id
     *
     * @param id the value for hab_app_habit_clockin_record.id
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_clockin_record.daily_record_id
     *
     * @return the value of hab_app_habit_clockin_record.daily_record_id
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public Long getDailyRecordId() {
        return dailyRecordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_clockin_record.daily_record_id
     *
     * @param dailyRecordId the value for hab_app_habit_clockin_record.daily_record_id
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public void setDailyRecordId(Long dailyRecordId) {
        this.dailyRecordId = dailyRecordId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_clockin_record.bonus
     *
     * @return the value of hab_app_habit_clockin_record.bonus
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public Integer getBonus() {
        return bonus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_clockin_record.bonus
     *
     * @param bonus the value for hab_app_habit_clockin_record.bonus
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_clockin_record.each_count
     *
     * @return the value of hab_app_habit_clockin_record.each_count
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public Integer getEachCount() {
        return eachCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_clockin_record.each_count
     *
     * @param eachCount the value for hab_app_habit_clockin_record.each_count
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public void setEachCount(Integer eachCount) {
        this.eachCount = eachCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_clockin_record.creator
     *
     * @return the value of hab_app_habit_clockin_record.creator
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_clockin_record.creator
     *
     * @param creator the value for hab_app_habit_clockin_record.creator
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_clockin_record.gmt_create
     *
     * @return the value of hab_app_habit_clockin_record.gmt_create
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_clockin_record.gmt_create
     *
     * @param gmtCreate the value for hab_app_habit_clockin_record.gmt_create
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_clockin_record.modifier
     *
     * @return the value of hab_app_habit_clockin_record.modifier
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_clockin_record.modifier
     *
     * @param modifier the value for hab_app_habit_clockin_record.modifier
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_clockin_record.gmt_modified
     *
     * @return the value of hab_app_habit_clockin_record.gmt_modified
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_clockin_record.gmt_modified
     *
     * @param gmtModified the value for hab_app_habit_clockin_record.gmt_modified
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_clockin_record.is_deleted
     *
     * @return the value of hab_app_habit_clockin_record.is_deleted
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_clockin_record.is_deleted
     *
     * @param isDeleted the value for hab_app_habit_clockin_record.is_deleted
     *
     * @mbggenerated Wed Nov 27 13:37:22 CST 2019
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }
}