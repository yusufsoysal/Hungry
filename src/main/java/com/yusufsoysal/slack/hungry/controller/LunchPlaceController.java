package com.yusufsoysal.slack.hungry.controller;

import com.yusufsoysal.slack.hungry.model.LunchModel;
import com.yusufsoysal.slack.hungry.model.SlackRequest;
import com.yusufsoysal.slack.hungry.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LunchPlaceController {

    @Value("${slack.token}")
    private String slackToken;

    @Autowired
    private RequestService requestService;

    private String[] possibleEmoticons = new String[] {
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

    @RequestMapping(path = "/lunch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> lunchPlaceOptions(SlackRequest slackRequest){
        if( !slackToken.equals(slackRequest.getToken()) ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LunchModel lunchModel = requestService.processText(slackRequest.getText());
        if( lunchModel.isValid() ){
            StringBuilder places = new StringBuilder();
            int index = 0;
            for (String place : lunchModel.getPlaces()) {
                places.append( possibleEmoticons[ index++%possibleEmoticons.length ] )
                        .append(" ")
                        .append(place)
                        .append("  ");
            }

            if( lunchModel.getDate() != null ){
//                places.append("at " + lunchModel.getDate());
            }

            String response = "{" +
                    "    \"response_type\": \"in_channel\"," +
                    "    \"text\": \"Choose a place for lunch\"," +
                    "    \"attachments\": [" +
                    "        {" +
                    "            \"text\":\"*Offered by*  : @" + slackRequest.getUserName() + "\n*Where*  :" + places.toString() + "\n*When*  :" + lunchModel.getDate() + "\"," +
                    "            \"mrkdwn_in\": [\"text\"]" +
                    "        }" +
                    "    ]" +
                    "}";
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

}
