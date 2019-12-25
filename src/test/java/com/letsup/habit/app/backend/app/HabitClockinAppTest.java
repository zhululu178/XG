package com.letsup.habit.app.backend.app;

import com.alibaba.fastjson.JSONObject;
import com.letsup.habit.app.backend.HabApplicationTest;
import com.letsup.habit.app.backend.constants.ApiResultConstants;
import com.letsup.habit.app.backend.constants.HabitFrequencyEnum;
import com.letsup.habit.app.backend.constants.HabitTypeEnum;
import com.letsup.habit.app.backend.util.DateTimeUtil;
import com.letsup.habit.app.backend.vo.HabitRemindVo;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HabitClockinAppTest extends HabApplicationTest {
    //星星超过数量
    @Test
    public void testClockinOverStepBonus() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        JSONObject jsonObject = new JSONObject(3);
        jsonObject.put("habitId", 5);
        jsonObject.put("bonus", 6);
        jsonObject.put("count", 1);
        jsonObject.put("date", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd"));
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/clockin/", HttpMethod.POST, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(ApiResultConstants.ExceptionMessage.CLOCKIN_BONUS_EXCEED.getMessage()) > 0);
    }

    //打卡超过期限
    @Test
    public void testClockinOverStepDate() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        JSONObject jsonObject = new JSONObject(3);
        jsonObject.put("habitId", 5);
        jsonObject.put("bonus", 6);
        jsonObject.put("count", 1);
        jsonObject.put("date", "2019-01-01");
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/clockin/", HttpMethod.POST, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(ApiResultConstants.ExceptionMessage.CLOCKIN_DATE_EXPIRED.getMessage()) > 0);
    }

    @Test
    public void testClockin() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        JSONObject jsonObject = new JSONObject(4);
        jsonObject.put("habitId", 6);
        jsonObject.put("bonus", 1);
        jsonObject.put("count", 1);
        jsonObject.put("date", DateTimeUtil.dateToStr(new Date(), "yyyy-MM-dd"));
//        jsonObject.put("date", "2019-11-28");
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/clockin/", HttpMethod.POST, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }

    @Test
    public void testCancelClockin() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

        ResponseEntity map = this.restTemplate.exchange("/app/clockin/cancel/6", HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }
}