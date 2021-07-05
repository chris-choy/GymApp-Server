package com.chris.gym.Login;

import com.chris.gym.controller.AuthenticationRestController;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.http.parser.MediaType;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.io.StringReader;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationRestController.class)
public class LoginTest {

//    @Autowired
//    AuthenticationRestController authenticationRestController;

//    private MockMvc mockMvc;
//
//    @Before
//    public void setup() {
//        this.mockMvc = standaloneSetup(new AuthenticationRestController()).build();
//    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void authenticationTest() throws Exception {

        StringReader stringReader = new StringReader("application/json;charset=UTF-8");

        UrlEncodedFormEntity form = new UrlEncodedFormEntity(Arrays.asList(
                new BasicNameValuePair("username", "uuuuuuu"),
                new BasicNameValuePair("password", "uuuuuuu")
                ), "utf-8");

        this.mockMvc.perform(
                post("/api/login")
                    .accept(String.valueOf(MediaType.parseMediaType(stringReader)))
                    .content(EntityUtils.toString(form))
                    .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isOk())
                .andDo(print());
//                .andExpect(content().contentType(ResultMatcher.))
//                .andExpect(content().contentType("application/json"));
    }

}
