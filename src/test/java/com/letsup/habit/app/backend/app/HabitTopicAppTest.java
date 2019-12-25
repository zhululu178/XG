package com.letsup.habit.app.backend.app;

import com.alibaba.fastjson.JSONObject;
import com.letsup.habit.app.backend.HabApplicationTest;
import com.letsup.habit.app.backend.cond.CreateMemberCond;
import com.letsup.habit.app.backend.constants.FamilyMemberRoleEnum;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class HabitTopicAppTest extends HabApplicationTest {
    @Test
    public void testGetTopics() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

        ResponseEntity map = this.restTemplate.exchange("/app/topic/getTopics", HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf("\"id\"") > 0);
    }

    @Test
    public void testGetTopicById() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

        ResponseEntity map = this.restTemplate.exchange("/app/topic/1", HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf("\"id\"") > 0);
    }
}