package com.yusufsoysal.slack.hungry.controller;

import com.yusufsoysal.slack.hungry.model.SlackRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LunchPlaceController {

    @Value("${slack.token}")
    private String slackToken;

    @RequestMapping(path = "/lunch", method = RequestMethod.POST)
    public ResponseEntity<String> lunchPlaceOptions(SlackRequest slackRequest){
        if( !slackToken.equals(slackRequest.getToken()) ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
