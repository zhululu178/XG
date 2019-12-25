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

public class FamilyMemberAppTest extends HabApplicationTest {
    @Test
    public void testCreateChild() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        JSONObject jsonObject = new JSONObject(1);
        List<CreateMemberCond> members = new ArrayList<>(1);
        CreateMemberCond member = new CreateMemberCond();
        member.setName("小朋友1");
        member.setBirthDay("2014-7-6");
        member.setFamilyRole(FamilyMemberRoleEnum.KID.name());
        member.setSex("M");
        members.add(member);
        jsonObject.put("members", members);
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);

        ResponseEntity map = this.restTemplate.exchange("/app/familymember/createChild", HttpMethod.POST, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf("\"id\"") > 0);
    }

    @Test
    public void testCreateAdult() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        JSONObject jsonObject = new JSONObject(1);
        jsonObject.put("familyRole", FamilyMemberRoleEnum.FATHER.name());
        HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);

        ResponseEntity map = this.restTemplate.exchange("/app/familymember/createAdult", HttpMethod.POST, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf("\"id\"") > 0);
    }

    @Test
    public void testSelectMembers() throws Exception {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.add(tokenHeader, this.getAccessToken());
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);

        ResponseEntity map = this.restTemplate.exchange("/app/familymember/getMembers", HttpMethod.GET, requestEntity, String.class);

        Assert.assertEquals(HttpServletResponse.SC_OK, map.getStatusCode().value());
        Assert.assertThat(map.getBody(), Matchers.notNullValue());
        Assert.assertTrue(map.getBody().toString().indexOf("\"id\"") > 0);
    }
}