package com.charnomic.jcharnomic.db;

import java.util.Date;

/**
 * Created by harryculpan on 3/26/17.
 */
public class Proposal {
    Integer id;

    String proposal;

    String name;

    Integer num;

    Player proposedBy;

    Date proposedDate;

    Date voteOpened;

    Date voteClosed;

    Integer votesInFavor;

    Integer votesAgainst;

    Integer votesAbstained;

    Integer votesVeto;

    String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProposal() {
        return proposal;
    }

    public void setProposal(String proposal) {
        this.proposal = proposal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Player getProposedBy() {
        return proposedBy;
    }

    public void setProposedBy(Player proposedBy) {
        this.proposedBy = proposedBy;
    }

    public Date getProposedDate() {
        return proposedDate;
    }

    public void setProposedDate(Date proposedDate) {
        this.proposedDate = proposedDate;
    }

    public Date getVoteOpened() {
        return voteOpened;
    }

    public void setVoteOpened(Date voteOpened) {
        this.voteOpened = voteOpened;
    }

    public Date getVoteClosed() {
        return voteClosed;
    }

    public void setVoteClosed(Date voteClosed) {
        this.voteClosed = voteClosed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVotesInFavor() {
        return votesInFavor;
    }

    public void setVotesInFavor(Integer votesInFavor) {
        this.votesInFavor = votesInFavor;
    }

    public Integer getVotesAgainst() {
        return votesAgainst;
    }

    public void setVotesAgainst(Integer votesAgainst) {
        this.votesAgainst = votesAgainst;
    }

    public Integer getVotesAbstained() {
        return votesAbstained;
    }

    public void setVotesAbstained(Integer votesAbstained) {
        this.votesAbstained = votesAbstained;
    }

    public Integer getVotesVeto() {
        return votesVeto;
    }

    public void setVotesVeto(Integer votesVeto) {
        this.votesVeto = votesVeto;
    }
}
