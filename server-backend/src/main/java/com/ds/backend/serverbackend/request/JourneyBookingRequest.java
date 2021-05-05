package com.ds.backend.serverbackend.request;

import java.time.LocalDate;
import java.util.Date;

public class JourneyBookingRequest {

    private String username;
    private String starting_point;
    private String ending_point;
    private Date journey_date_time;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStarting_point() {
        return starting_point;
    }

    public void setStarting_point(String starting_point) {
        this.starting_point = starting_point;
    }

    public String getEnding_point() {
        return ending_point;
    }

    public void setEnding_point(String ending_point) {
        this.ending_point = ending_point;
    }

    public Date getJourney_date_time() {
        return journey_date_time;
    }

    public void setJourney_date_time(Date journey_date_time) {
        this.journey_date_time = journey_date_time;
    }
}
