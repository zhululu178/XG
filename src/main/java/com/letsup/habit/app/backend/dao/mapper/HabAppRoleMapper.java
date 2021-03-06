package com.letsup.habit.app.backend.dao.mapper;

import com.letsup.habit.app.backend.dao.entity.HabAppRole;
import com.letsup.habit.app.backend.dao.entity.HabAppRoleExample;
import java.util.List;

public interface HabAppRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hab_app_role
     *
     * @mbggenerated Mon Nov 11 16:54:27 CST 2019
     */
    int countByExample(HabAppRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hab_app_role
     *
     * @mbggenerated Mon Nov 11 16:54:27 CST 2019
     */
    int deleteByPrimaryKey(HabAppRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hab_app_role
     *
     * @mbggenerated Mon Nov 11 16:54:27 CST 2019
     */
    int insertSelective(HabAppRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hab_app_role
     *
     * @mbggenerated Mon Nov 11 16:54:27 CST 2019
     */
    List<HabAppRole> selectByExample(HabAppRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hab_app_role
     *
     * @mbggenerated Mon Nov 11 16:54:27 CST 2019
     */
    HabAppRole selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hab_app_role
     *
     * @mbggenerated Mon Nov 11 16:54:27 CST 2019
     */
    int updateByPrimaryKeySelective(HabAppRole record);
}