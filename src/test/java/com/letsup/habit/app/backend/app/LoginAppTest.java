package com.letsup.habit.app.backend.app;

import com.alibaba.fastjson.JSONObject;
import com.letsup.habit.app.backend.HabApplicationTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public class LoginAppTest extends HabApplicationTest {
    //登录
//    @Test
    public void testLogin() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);

        ResponseEntity map = this.restTemplate.postForEntity("/login", requestEntity, String.class);
        Assert.assertEquals(map.getStatusCode().value(), HttpServletResponse.SC_OK);
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf("access_token") > 0);
        Assert.assertTrue(map.getBody().toString().indexOf("refresh_token") > 0);
    }

    //登录
    @Test
    public void testLoginWithIMEI() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonObject = new JSONObject(2);
        jsonObject.put("imei", this.imei);
        jsonObject.put("password", this.password);

        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);

        ResponseEntity map = this.restTemplate.postForEntity("/login", requestEntity, String.class);
        Assert.assertEquals(map.getStatusCode().value(), HttpServletResponse.SC_OK);
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf("access_token") > 0);
        Assert.assertTrue(map.getBody().toString().indexOf("refresh_token") > 0);
    }
}
