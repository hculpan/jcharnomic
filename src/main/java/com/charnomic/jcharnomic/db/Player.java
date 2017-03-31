package com.charnomic.jcharnomic.db;

import java.util.Date;

/**
 * Created by harryculpan on 3/26/17.
 */
public class Player {
    public  enum updateablefields { firstname, lastname, turn, onleave, points, gold, vetoes, totalvetoes }

    Integer id;

    String firstname;

    String lastname;

    Date joined;

    Boolean turn;

    Boolean onLeave;

    Integer points;

    Integer level;

    Integer gold;

    Integer vetoes;

    Integer totalVetoes;

    Date leftGame;

    Boolean passwordExpired;

    Boolean monitor;

    String uuid;

    String email;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getJoined() {
        return joined;
    }

    public void setJoined(Date joined) {
        this.joined = joined;
    }

    public Boolean getTurn() {
        return turn;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

    public Boolean getOnLeave() {
        return onLeave;
    }

    public void setOnLeave(Boolean onLeave) {
        this.onLeave = onLeave;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getLevel() {
        return (int)Math.floor(1.725 * Math.sqrt(getPoints()) + 1);
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getGold() {
        return gold;
    }

    public void setGold(Integer gold) {
        this.gold = gold;
    }

    public Date getLeftGame() {
        return leftGame;
    }

    public void setLeftGame(Date leftGame) {
        this.leftGame = leftGame;
    }

    public Boolean getMonitor() {
        return monitor;
    }

    public void setMonitor(Boolean monitor) {
        this.monitor = monitor;
    }

    public Integer getVetoes() {
        return vetoes;
    }

    public void setVetoes(Integer vetoes) {
        this.vetoes = vetoes;
    }

    public Integer getTotalVetoes() {
        return totalVetoes;
    }

    public void setTotalVetoes(Integer totalVetoes) {
        this.totalVetoes = totalVetoes;
    }

    public Boolean getPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(Boolean passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
