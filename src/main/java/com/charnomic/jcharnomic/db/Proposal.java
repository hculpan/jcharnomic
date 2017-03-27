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

    Integer proposedBy;

    Date proposedDate;

    Date voteOpened;

    Date voteClosed;

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

    public Integer getProposedBy() {
        return proposedBy;
    }

    public void setProposedBy(Integer proposedBy) {
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
}
