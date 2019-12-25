package com.letsup.habit.app.backend.app;

import com.alibaba.fastjson.JSONObject;
import com.letsup.habit.app.backend.HabApplicationTest;
import com.letsup.habit.app.backend.util.DateTimeUtil;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class HabitBonusAppTest extends HabApplicationTest {
    @Test
    public void testGetPageByCond() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        String urlParam = "?memberId=26&pageIndex=0&pageCount=10";
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/bonus/getPageByCond" + urlParam, HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }

    @Test
    public void testCollectError() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
//        jsonObject.put("date", "2019-11-28");
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/bonus/collect/5", HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":6002") > 0);
    }

    @Test
    public void testCollect() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
//        jsonObject.put("date", "2019-11-28");
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/bonus/collect/11", HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }
}