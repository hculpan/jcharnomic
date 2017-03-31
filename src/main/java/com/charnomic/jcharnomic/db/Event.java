package com.charnomic.jcharnomic.db;

import java.util.Date;

/**
 * Created by harryculpan on 3/30/17.
 */
public class Event {
    Integer id;

    String event;

    Date eventDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
