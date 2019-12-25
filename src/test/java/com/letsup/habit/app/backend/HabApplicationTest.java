package com.letsup.habit.app.backend;

import com.alibaba.fastjson.JSONObject;
import com.letsup.habit.app.backend.vo.TokenVo;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
public class HabApplicationTest {
    @Value("${jwt.token}")
    protected String tokenHeader;

    @Autowired
    protected TestRestTemplate restTemplate;

    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }

    protected String username = "13346167123";
    protected String password = "123456";
    protected String imei = "3DC81A43-5C61-4570-B8B5-D040F42128C2";

    protected String accessToken;
    /**
     * 获得access_token
     * @return
     */
    protected String getAccessToken() {
        if(StringUtils.isBlank(this.accessToken)) {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.setContentType(MediaType.APPLICATION_JSON);
            JSONObject jsonObject = new JSONObject(1);
            jsonObject.put("imei", imei);
            jsonObject.put("password", password);
            HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toJSONString(), requestHeaders);
            ResponseEntity<JSONObject> map = this.restTemplate.postForEntity("/login", requestEntity, JSONObject.class);
            JSONObject response = map.getBody();
            TokenVo token = response.getObject("data", TokenVo.class);
            this.accessToken = "Bearer " + token.getAccess_token();
        }

        return this.accessToken;
    }
}