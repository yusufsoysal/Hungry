package com.yusufsoysal.slack.hungry.model;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

public class LunchResponseTest {

    @Test
    public void shouldAddTextAndMarkdownInfoIntoAttachments(){
        LunchResponse response = new LunchResponse("MainText", "AttachmentText");
        Map<String, Object> attachments = response.getAttachments();

        Assert.assertThat(attachments, Matchers.hasEntry("text", "AttachmentText"));
        Assert.assertThat(attachments, Matchers.hasEntry("mrkdwn_in", Arrays.asList("text")));
    }

}