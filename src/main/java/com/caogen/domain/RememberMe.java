package com.caogen.domain;

import java.util.Date;

/**
 * Created by neal on 9/16/16.
 */
public class RememberMe{

    private String username;
    private  String series;
    private  String tokenValue;
    private  Date date;

//    public RememberMe(String username, String series, String tokenValue, Date date) {
//        this.username = username;
//        this.series = series;
//        this.tokenValue = tokenValue;
//        this.date = date;
//    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getSeries() {
        return series;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public Date getDate() {
        return date;
    }
}
