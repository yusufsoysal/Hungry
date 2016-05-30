package com.yusufsoysal.slack.hungry.service;

import com.yusufsoysal.slack.hungry.model.LunchModel;
import com.yusufsoysal.slack.hungry.model.RequestTextTuple;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RequestService {

    private static final String DATETIME_SEPARATOR = " at ";

    public LunchModel processText(String text) {
        RequestTextTuple requestTextTuple = parseText(text);
        return new LunchModel(getPlaces(requestTextTuple), requestTextTuple.getDate());
    }

    private RequestTextTuple parseText(String text){
        if( !StringUtils.hasText(text) ){
            return new RequestTextTuple("", null);
        }

        int indexOfSeparator = text.lastIndexOf(DATETIME_SEPARATOR);

        String placesText = text;
        String dateText = null;
        if( indexOfSeparator > 0 ){
            placesText = text.substring(0, indexOfSeparator);
            dateText = text.substring(indexOfSeparator + DATETIME_SEPARATOR.length());
        }

        return new RequestTextTuple(placesText, dateText);
    }

    private List<String> getPlaces(RequestTextTuple requestTextTuple) {
        if( !StringUtils.hasText(requestTextTuple.getPlaces()) ){
            return Collections.EMPTY_LIST;
        }

        Predicate<String> empty = String::isEmpty;

        return Stream.of(requestTextTuple.getPlaces())
                .map(theText -> theText.replaceAll("^\"", ""))
                .map(theText -> theText.split("\"?(,|$)(?=(([^\"]*\"){2})*[^\"]*$) *\"?"))
                .flatMap(Arrays::stream)
                .map(String::trim)
                .filter(empty.negate())
                .collect(Collectors.toList());
    }
}
