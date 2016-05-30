package com.yusufsoysal.slack.hungry.model;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;

public class LunchResponseTest {

    @Test
    public void shouldAddTextAndMarkdownInfoIntoAttachments(){
        LunchResponse response = new LunchResponse("MainText", "AttachmentText");
        List<Map<String, Object>> attachments = response.getAttachments();

        assertThat(attachments.size(), equalTo(1));
        assertThat(attachments.get(0), hasEntry("text", "AttachmentText"));
        assertThat(attachments.get(0), hasEntry("mrkdwn_in", Arrays.asList("text")));
    }

}