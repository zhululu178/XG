package com.letsup.habit.app.backend.app;

import com.alibaba.fastjson.JSONObject;
import com.letsup.habit.app.backend.HabApplicationTest;
import com.letsup.habit.app.backend.constants.HabitFrequencyEnum;
import com.letsup.habit.app.backend.constants.HabitTypeEnum;
import com.letsup.habit.app.backend.vo.HabitRemindVo;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HabitAppTest extends HabApplicationTest {
    @Test
    public void testCreateByTemplate() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        JSONObject jsonObject = new JSONObject(3);
        jsonObject.put("memberId", 26l);
        jsonObject.put("stickDays", 14);
        List<Long> templates = new ArrayList<>();
        templates.add(1l);
        templates.add(2l);
        jsonObject.put("templates", templates);
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/habit/createByTemplate", HttpMethod.POST, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }

    @Test
    public void testCreate() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "测试习惯" + new Date().hashCode());
        jsonObject.put("executor", 26l);
        jsonObject.put("type", HabitTypeEnum.TIME.name());
        jsonObject.put("stickDays", 7);
        jsonObject.put("startDate", "2019-11-25 00:00:00");
        jsonObject.put("endDate", "2019-12-05 00:00:00");
        jsonObject.put("dailyCount", 3);
        jsonObject.put("eachTime", 90);
        jsonObject.put("class1", 2l);
        jsonObject.put("class2", 15l);
        jsonObject.put("freqType", HabitFrequencyEnum.F1.name());
        List<HabitRemindVo> remindVos = new ArrayList<>();
        HabitRemindVo remindVo1 = new HabitRemindVo();
        remindVo1.setId(111111l);
        remindVo1.setLabel("13:30");
        remindVo1.setBody("body内容");
        remindVo1.setTitle("标题内容");
        remindVo1.setStatus(true);
        remindVos.add(remindVo1);
        jsonObject.put("reminds", remindVos);
        List<String> freqDays = new ArrayList<>();
        freqDays.add("1");
        freqDays.add("3");
        freqDays.add("5");
        jsonObject.put("freqDays", freqDays);
        jsonObject.put("timer", "60");
        List<String> counters = new ArrayList<>();
        counters.add("50");
        counters.add("100");
        counters.add("300");
        jsonObject.put("counter", counters);
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/habit/create", HttpMethod.POST, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }

    @Test
    public void testGetByFamilyIdAndDate() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        String urlParam = "?familyId=32&date=2019-12-08";
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/habit/getByFamilyIdAndDate" + urlParam, HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }

    @Test
    public void testGetById() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/habit/152", HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }

    @Test
    public void testGetPageByCond() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        String urlParam = "?familyId=15&status=PROCESSING&pageIndex=1&pageCount=10";
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/habit/getPageByCond" + urlParam, HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }

    @Test
    public void testGetHabitEndDate() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("startDate", "2019-12-07");
        jsonObject.put("freqType", HabitFrequencyEnum.F1.name());
        jsonObject.put("freqDays", "1,3,5");
        jsonObject.put("stickDays", 7);
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/habit/getHabitEndDate", HttpMethod.POST, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }

    @Test
    public void testCopyHabit() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());

        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/habit/copy/96", HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }
}