package com.letsup.habit.app.backend.dao.entity;

import java.util.Date;

public class HabAppHabitColor {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_color.id
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_color.name
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_color.color
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    private String color;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_color.color_order
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    private Integer colorOrder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_color.creator
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    private String creator;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_color.gmt_create
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    private Date gmtCreate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_color.modifier
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    private String modifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_color.gmt_modified
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    private Date gmtModified;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column hab_app_habit_color.is_deleted
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    private String isDeleted;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_color.id
     *
     * @return the value of hab_app_habit_color.id
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_color.id
     *
     * @param id the value for hab_app_habit_color.id
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_color.name
     *
     * @return the value of hab_app_habit_color.name
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_color.name
     *
     * @param name the value for hab_app_habit_color.name
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_color.color
     *
     * @return the value of hab_app_habit_color.color
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public String getColor() {
        return color;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_color.color
     *
     * @param color the value for hab_app_habit_color.color
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_color.color_order
     *
     * @return the value of hab_app_habit_color.color_order
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public Integer getColorOrder() {
        return colorOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_color.color_order
     *
     * @param colorOrder the value for hab_app_habit_color.color_order
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public void setColorOrder(Integer colorOrder) {
        this.colorOrder = colorOrder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_color.creator
     *
     * @return the value of hab_app_habit_color.creator
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public String getCreator() {
        return creator;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_color.creator
     *
     * @param creator the value for hab_app_habit_color.creator
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_color.gmt_create
     *
     * @return the value of hab_app_habit_color.gmt_create
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_color.gmt_create
     *
     * @param gmtCreate the value for hab_app_habit_color.gmt_create
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_color.modifier
     *
     * @return the value of hab_app_habit_color.modifier
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_color.modifier
     *
     * @param modifier the value for hab_app_habit_color.modifier
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_color.gmt_modified
     *
     * @return the value of hab_app_habit_color.gmt_modified
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_color.gmt_modified
     *
     * @param gmtModified the value for hab_app_habit_color.gmt_modified
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column hab_app_habit_color.is_deleted
     *
     * @return the value of hab_app_habit_color.is_deleted
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column hab_app_habit_color.is_deleted
     *
     * @param isDeleted the value for hab_app_habit_color.is_deleted
     *
     * @mbggenerated Fri Nov 22 15:24:41 CST 2019
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted == null ? null : isDeleted.trim();
    }
}