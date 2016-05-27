package com.yusufsoysal.slack.hungry.controller;

import com.yusufsoysal.slack.hungry.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class LunchPlaceControllerTest {

    private MockMvc mockMvc;

    @Value("${slack.token}")
    private String slackToken;

    @Autowired
    private LunchPlaceController controller;

    private MultiValueMap<String, String> defaultParameters;

    @Before
    public void before(){
        mockMvc = MockMvcBuilders.standaloneSetup( controller ).build();

        defaultParameters = new LinkedMultiValueMap();
        defaultParameters.add("token", slackToken);
        defaultParameters.add("team_id", "T0001");
        defaultParameters.add("team_domain", "example");
        defaultParameters.add("channel_id", "C2147483705");
        defaultParameters.add("channel_name", "test");
        defaultParameters.add("user_id", "U2147483697");
        defaultParameters.add("user_name", "Steve");
        defaultParameters.add("command", "/lunch");
        defaultParameters.add("text", "12345");
        defaultParameters.add("response_url", "https://hooks.slack.com/commands/1234/5678");
    }

    @Test
    public void shouldReturnHttpOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lunch").params(defaultParameters))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void shouldReturnHttpBadRequestWhenTokenIsIncorrect() throws Exception {
        defaultParameters.set("token", "___incorrect___");

        mockMvc.perform(MockMvcRequestBuilders.post("/lunch").params(defaultParameters))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

}