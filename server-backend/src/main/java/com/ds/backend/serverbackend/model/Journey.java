package com.ds.backend.serverbackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "Journeys")
public class Journey {
    @Id
    private String id;
    private String username;
    private String startingPoint;
    private String endingPoint;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date journey_date_time;

    private String status;

    public Journey() {
    }

    public Journey(String username, String startingPoint, String endingPoint, Date journey_date_time, String status) {
        this.username = username;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
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

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
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
