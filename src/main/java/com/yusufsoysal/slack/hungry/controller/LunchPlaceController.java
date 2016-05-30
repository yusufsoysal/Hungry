package com.yusufsoysal.slack.hungry.controller;

import com.yusufsoysal.slack.hungry.model.LunchModel;
import com.yusufsoysal.slack.hungry.model.LunchResponse;
import com.yusufsoysal.slack.hungry.model.SlackRequest;
import com.yusufsoysal.slack.hungry.service.RequestService;
import com.yusufsoysal.slack.hungry.service.ResponseCreatorService;
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

    @Autowired
    private ResponseCreatorService responseCreatorService;

    @RequestMapping(path = "/lunch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<LunchResponse> lunchPlaceOptions(SlackRequest slackRequest){
        if( !slackToken.equals(slackRequest.getToken()) ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LunchModel lunchModel = requestService.processText(slackRequest.getText());
        if( lunchModel.isValid() ){
            return new ResponseEntity<>(responseCreatorService.buildLunchResponse(slackRequest, lunchModel), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
