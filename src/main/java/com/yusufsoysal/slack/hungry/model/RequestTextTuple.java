package com.yusufsoysal.slack.hungry.model;

public class RequestTextTuple {
    private final String places;
    private final String date;

    public RequestTextTuple(String places, String date) {
        this.places = places;
        this.date = date;
    }

    public String getPlaces() {
        return places;
    }

    public String getDate() {
        return date;
    }
}
