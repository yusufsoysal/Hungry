package com.yusufsoysal.slack.hungry.service;

import com.yusufsoysal.slack.hungry.model.LunchModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RequestServiceTest {

    @InjectMocks
    private RequestService service;

    @Test
    public void shouldParseDateAndRestaurantsText() {
        String text = "option1, option2, option3, option4 at 3:00pm";

        LunchModel model = service.processText(text);

        assertThat(model.getPlaces(), equalTo(Arrays.asList("option1", "option2", "option3", "option4")));
        assertThat(model.getDate(), equalTo("3:00pm"));
        assertThat(model.isValid(), equalTo(true));
    }

    @Test
    public void shouldParseRestaurantsOnly() {
        String text = "option1, option2, option3, option4";

        LunchModel model = service.processText(text);

        assertThat(model.getPlaces(), equalTo(Arrays.asList("option1", "option2", "option3", "option4")));
        assertThat(model.getDate(), nullValue());
        assertThat(model.isValid(), equalTo(true));
    }

    @Test
    public void shouldParseOnlyOneRestaurant() {
        String text = "option1";

        LunchModel model = service.processText(text);

        assertThat(model.getPlaces(), equalTo(Arrays.asList("option1")));
        assertThat(model.getDate(), nullValue());
        assertThat(model.isValid(), equalTo(true));
    }

    @Test
    public void shouldParseNullTextButReturnInvalidModel() {
        String text = null;

        LunchModel model = service.processText(text);

        assertThat(model.getPlaces(), equalTo(Collections.EMPTY_LIST));
        assertThat(model.getDate(), nullValue());
        assertThat(model.isValid(), equalTo(false));
    }

    @Test
    public void shouldParseEmptyTextButReturnInvalidModel() {
        String text = "";

        LunchModel model = service.processText(text);

        assertThat(model.getPlaces(), equalTo(Collections.EMPTY_LIST));
        assertThat(model.getDate(), nullValue());
        assertThat(model.isValid(), equalTo(false));
    }

    @Test
    public void shouldParseWhitespaceTextButReturnInvalidModel() {
        String text = "         ";

        LunchModel model = service.processText(text);

        assertThat(model.getPlaces(), equalTo(Collections.EMPTY_LIST));
        assertThat(model.getDate(), nullValue());
        assertThat(model.isValid(), equalTo(false));
    }

    @Test
    public void shouldParseDateAndRestaurantsTextWithQuotedElements() {
        String text = "option1, option2, \"option3, option4\", option5 at 3:00pm";

        LunchModel model = service.processText(text);

        assertThat(model.getPlaces(), equalTo(Arrays.asList("option1", "option2", "option3, option4", "option5")));
        assertThat(model.getDate(), equalTo("3:00pm"));
        assertThat(model.isValid(), equalTo(true));
    }

    @Test
    public void shouldParseEmptyRestaurantsTextWithinValidNames() {
        String text = "option1, option2, ,   ,, option3 at 3:00pm";

        LunchModel model = service.processText(text);

        assertThat(model.getPlaces(), equalTo(Arrays.asList("option1", "option2", "option3")));
        assertThat(model.getDate(), equalTo("3:00pm"));
        assertThat(model.isValid(), equalTo(true));
    }

}