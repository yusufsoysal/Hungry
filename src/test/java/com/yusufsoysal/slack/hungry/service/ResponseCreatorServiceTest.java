package com.yusufsoysal.slack.hungry.service;

import com.yusufsoysal.slack.hungry.model.LunchModel;
import com.yusufsoysal.slack.hungry.model.LunchResponse;
import com.yusufsoysal.slack.hungry.model.SlackRequest;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ResponseCreatorServiceTest {

    @InjectMocks
    private ResponseCreatorService service;

    @Mock
    private MessageService messageService;

    @Test
    public void shouldBuildResponse(){
        SlackRequest slackRequest = new SlackRequest();
        slackRequest.setUser_name("MyUsername");

        LunchModel lunchModel = new LunchModel(Arrays.asList("place1", "place2"), "TheDate");

        String placesJoined = ":earth_africa: place1  :full_moon: place2  ";

        Mockito.when(messageService.getMessage("response.attachment.offeredBy", slackRequest.getUserName())).thenReturn("OfferedBy\n");
        Mockito.when(messageService.getMessage("response.attachment.places", placesJoined)).thenReturn("EventPlaces\n");
        Mockito.when(messageService.getMessage("response.attachment.time", lunchModel.getDate())).thenReturn("EventDate\n");

        LunchResponse lunchResponse = service.buildLunchResponse(slackRequest, lunchModel);

        assertThat(lunchResponse.getAttachments().get(0), hasEntry("text", "OfferedBy\nEventPlaces\nEventDate\n"));
    }

    @Test
    public void shouldBuildResponseEvenThereIsNoDate(){
        SlackRequest slackRequest = new SlackRequest();
        slackRequest.setUser_name("MyUsername");

        LunchModel lunchModel = new LunchModel(Arrays.asList("place1", "place2"), null);

        String placesJoined = ":earth_africa: place1  :full_moon: place2  ";

        Mockito.when(messageService.getMessage("response.attachment.offeredBy", slackRequest.getUserName())).thenReturn("OfferedBy\n");
        Mockito.when(messageService.getMessage("response.attachment.places", placesJoined)).thenReturn("EventPlaces\n");

        LunchResponse lunchResponse = service.buildLunchResponse(slackRequest, lunchModel);

        assertThat(lunchResponse.getAttachments().get(0), hasEntry("text", "OfferedBy\nEventPlaces\n"));
    }

    @Test
    public void shouldBuildResponseWithOnlyOnePlace(){
        SlackRequest slackRequest = new SlackRequest();
        slackRequest.setUser_name("MyUsername");

        LunchModel lunchModel = new LunchModel(Arrays.asList("place1"), null);

        String placesJoined = ":earth_africa: place1  ";

        Mockito.when(messageService.getMessage("response.attachment.offeredBy", slackRequest.getUserName())).thenReturn("OfferedBy\n");
        Mockito.when(messageService.getMessage("response.attachment.places", placesJoined)).thenReturn("EventPlaces\n");

        LunchResponse lunchResponse = service.buildLunchResponse(slackRequest, lunchModel);

        assertThat(lunchResponse.getAttachments().get(0), hasEntry("text", "OfferedBy\nEventPlaces\n"));
    }

}