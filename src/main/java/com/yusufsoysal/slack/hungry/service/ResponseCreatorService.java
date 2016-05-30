package com.yusufsoysal.slack.hungry.service;

import com.yusufsoysal.slack.hungry.model.LunchModel;
import com.yusufsoysal.slack.hungry.model.LunchResponse;
import com.yusufsoysal.slack.hungry.model.SlackRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseCreatorService {

    private static final String[] possibleEmoticons = new String[]{
            ":earth_africa:",
            ":full_moon:",
            ":zap:",
            ":shell:",
            ":sunny:",
            ":mushroom:",
            ":dizzy:",
            ":tornado:",
            ":snowman:",
            ":cloud:"
    };

    @Autowired
    private MessageService messageService;

    public LunchResponse buildLunchResponse(SlackRequest slackRequest, LunchModel lunchModel) {
        String attachmentText = buildAttachmentText(slackRequest, lunchModel);
        String text = messageService.getMessage("response.defaultText");

        return new LunchResponse(text, attachmentText);
    }

    private String buildAttachmentText(SlackRequest slackRequest, LunchModel lunchModel) {
        StringBuilder attachmentText = new StringBuilder();
        attachmentText.append( messageService.getMessage("response.attachment.offeredBy", slackRequest.getUserName()) );
        attachmentText.append( messageService.getMessage("response.attachment.places", getPlacesText(lunchModel)) );
        if (lunchModel.getDate() != null) {
            attachmentText.append( messageService.getMessage("response.attachment.time", lunchModel.getDate()) );
        }
        return attachmentText.toString();
    }

    private String getPlacesText(LunchModel lunchModel) {
        StringBuilder placesText = new StringBuilder();
        int index = 0;
        for (String place : lunchModel.getPlaces()) {
            placesText.append(getNextEmoticon(index)).append(" ")
                    .append(place).append("  ");
            index++;
        }

        return placesText.toString();
    }

    private String getNextEmoticon(int rowNumber) {
        return possibleEmoticons[rowNumber % possibleEmoticons.length];
    }
}
