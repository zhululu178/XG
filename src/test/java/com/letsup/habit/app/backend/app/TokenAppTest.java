package com.letsup.habit.app.backend.app;

import com.alibaba.fastjson.JSONObject;
import com.letsup.habit.app.backend.HabApplicationTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;

import javax.servlet.http.HttpServletResponse;

public class TokenAppTest extends HabApplicationTest {
    //刷token
    @Test
    public void testRefreshTokenSuccess() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

        ResponseEntity map = this.restTemplate.exchange("/app/token/refresh", HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(map.getStatusCode().value(), HttpServletResponse.SC_OK);
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf("access_token") > 0);
        Assert.assertTrue(map.getBody().toString().indexOf("refresh_token") > 0);
    }

    //錯誤的token不能刷
    @Test
    public void testRefreshTokenWithWrongToken() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, "wrong token value");
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

        ResponseEntity map = this.restTemplate.exchange("/app/token/refresh", HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(map.getStatusCode().value(), HttpServletResponse.SC_UNAUTHORIZED);
    }

    //沒有登錄不能進行刷token
    @Test
    public void testRefreshTokenWithOutLogin() throws Exception {
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, null);
        ResponseEntity map = this.restTemplate.exchange("/app/token/refresh", HttpMethod.GET, requestEntity, String.class);
        Assert.assertEquals(map.getStatusCode().value(), HttpServletResponse.SC_UNAUTHORIZED);
    }
}