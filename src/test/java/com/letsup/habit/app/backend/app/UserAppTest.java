package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.HabApplicationTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;

import javax.servlet.http.HttpServletResponse;

public class UserAppTest extends HabApplicationTest {
    @Test
    public void testRefreshTokenSuccess() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

        ResponseEntity map = this.restTemplate.exchange("/app/user/getUserInfo", HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(map.getStatusCode().value(), HttpServletResponse.SC_OK);
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf("\"id\"") > 0);
    }
}