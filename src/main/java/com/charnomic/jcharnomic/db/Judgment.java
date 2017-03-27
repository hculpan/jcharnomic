package com.charnomic.jcharnomic.db;

import java.util.Date;

/**
 * Created by USUCUHA on 3/27/2017.
 */
public class Judgment {
    Integer id;

    String request;

    String judgment;

    Player requestedBy;

    Date requestedDate;

    Player judgedBy;

    Date judgedDate;

    Boolean overruled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getJudgment() {
        return judgment;
    }

    public void setJudgment(String judgment) {
        this.judgment = judgment;
    }

    public Player getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Player requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Player getJudgedBy() {
        return judgedBy;
    }

    public void setJudgedBy(Player judgedBy) {
        this.judgedBy = judgedBy;
    }

    public Boolean getOverruled() {
        return overruled;
    }

    public void setOverruled(Boolean overruled) {
        this.overruled = overruled;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Date getJudgedDate() {
        return judgedDate;
    }

    public void setJudgedDate(Date judgedDate) {
        this.judgedDate = judgedDate;
    }
}
