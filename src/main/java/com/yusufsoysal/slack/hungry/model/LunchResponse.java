package com.yusufsoysal.slack.hungry.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LunchResponse implements Serializable {

    public static final long serialVersionUID = 1L;
    private static final String RESPONSE_IN_CHANNEL = "in_channel";
    private static final String MARKDOWN_PLACES = "mrkdwn_in";
    private static final String ATTACHMENT_TEXT = "text";

    private String responseType = RESPONSE_IN_CHANNEL;
    private final String text;

    private final Map<String, Object> attachments = new HashMap<>();

    public LunchResponse(String mainText, String attachmentText) {
        this.text = mainText;

        attachments.put(ATTACHMENT_TEXT, attachmentText);
        attachments.put(MARKDOWN_PLACES, Arrays.asList("text"));
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getResponse_type(){
        return responseType;
    }

    public String getText(){
        return text;
    }

    public Map<String, Object> getAttachments(){
        return attachments;
    }

}
