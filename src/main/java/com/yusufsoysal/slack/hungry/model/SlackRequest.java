package com.yusufsoysal.slack.hungry.model;

public class SlackRequest {
    private String token;
    private String teamId;
    private String teamDomain;
    private String channelId;
    private String channelName;
    private String userId;
    private String userName;
    private String command;
    private String text;
    private String responseUrl;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeam_id(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamDomain() {
        return teamDomain;
    }

    public void setTeam_domain(String teamDomain) {
        this.teamDomain = teamDomain;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannel_id(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannel_name(String channelName) {
        this.channelName = channelName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUser_id(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUser_name(String userName) {
        this.userName = userName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResponseUrl() {
        return responseUrl;
    }

    public void setResponse_url(String responseUrl) {
        this.responseUrl = responseUrl;
    }
}
