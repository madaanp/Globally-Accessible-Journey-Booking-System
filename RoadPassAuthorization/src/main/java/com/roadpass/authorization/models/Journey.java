package com.roadpass.authorization.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Sharded;

import java.util.Date;

@Document(collection = "Journeys")
@Sharded(shardKey = {"username"})
public class Journey {
    @Id
    private String id;
    private String username;
    private String starting_point;
    private String ending_point;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date journey_date_time;

    private String status;

    public Journey() {
    }

    public Journey(String username, String starting_point, String ending_point, Date journey_date_time, String status) {
        this.username = username;
        this.starting_point = starting_point;
        this.ending_point = ending_point;
        this.journey_date_time = journey_date_time;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
