package com.yusufsoysal.slack.hungry.model;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

public class LunchModel {
    private List<String> places;
    private String date;

    public LunchModel(List<String> places, String date) {
        this.places = places;
        this.date = date;
    }

    public List<String> getPlaces() {
        return places;
    }

    public String getDate() {
        return date;
    }

    public boolean isValid(){
        return CollectionUtils.isNotEmpty(places);
    }
}
