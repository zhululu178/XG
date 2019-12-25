package com.letsup.habit.app.backend.app;

import com.letsup.habit.app.backend.HabApplicationTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.*;

import javax.servlet.http.HttpServletResponse;

public class HabitLogAppTest extends HabApplicationTest {
    @Test
    public void testGetLastHabitLog() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        String urlParam = "?habitId=65&date=2019-12-06";
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        ResponseEntity map = this.restTemplate.exchange("/app/log/last" + urlParam, HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf(":1000") > 0);
    }
}
