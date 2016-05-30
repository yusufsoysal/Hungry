package com.yusufsoysal.slack.hungry.controller;

import com.yusufsoysal.slack.hungry.Application;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

        defaultParameters = new LinkedMultiValueMap<>();
        defaultParameters.add("token", slackToken);
        defaultParameters.add("team_id", "T0001");
        defaultParameters.add("team_domain", "example");
        defaultParameters.add("channel_id", "C2147483705");
        defaultParameters.add("channel_name", "test");
        defaultParameters.add("user_id", "U2147483697");
        defaultParameters.add("user_name", "Steve");
        defaultParameters.add("command", "/lunch");
        defaultParameters.add("text", "Option1, Option2, Option3 at 12:00pm");
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

    @Test
    public void shouldReturnJsonMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/lunch").params(defaultParameters))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.text").exists())
                .andExpect(jsonPath("$.text").value( Matchers.equalTo("Ready for Lunch?") ))
                .andExpect(jsonPath("$.attachments[0]").exists())
                .andExpect(jsonPath("$.attachments[0].text").exists())
                .andExpect(jsonPath("$.attachments[0].text").value( Matchers.equalTo("*Offered by*  : @Steve\n*Where*  : :earth_africa: Option1  :full_moon: Option2  :zap: Option3  \n*When*  : 12:00pm\n") ));
    }

    @Test
    public void shouldReturnHttpBadRequestWhenNoValidPlaceIsFound() throws Exception {
        defaultParameters.set("text", "  ");

        mockMvc.perform(MockMvcRequestBuilders.post("/lunch").params(defaultParameters))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}