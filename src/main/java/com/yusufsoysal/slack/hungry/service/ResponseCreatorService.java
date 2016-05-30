package com.yusufsoysal.slack.hungry.service;

import com.yusufsoysal.slack.hungry.model.LunchModel;
import com.yusufsoysal.slack.hungry.model.LunchResponse;
import com.yusufsoysal.slack.hungry.model.SlackRequest;
import org.springframework.stereotype.Service;

@Service
public class ResponseCreatorService {

    private static String[] possibleEmoticons = new String[]{
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

    public LunchResponse buildLunchResponse(SlackRequest slackRequest, LunchModel lunchModel) {
        String attachmentText = buildAttachmentText(slackRequest, lunchModel);
        return new LunchResponse("bla", attachmentText);
    }

    private String buildAttachmentText(SlackRequest slackRequest, LunchModel lunchModel) {
        StringBuilder attachmentText = new StringBuilder();
        attachmentText.append("*Offered by*  : @").append(slackRequest.getUserName()).append("\n");
        attachmentText.append("*Where*  : ").append(getPlacesText(lunchModel)).append("\n");
        if (lunchModel.getDate() != null) {
            attachmentText.append("*When*  : ").append(lunchModel.getDate()).append("\n");
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
