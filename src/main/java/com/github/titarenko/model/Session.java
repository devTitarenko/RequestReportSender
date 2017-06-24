package com.github.titarenko.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity(name = "session")
public class Session extends BaseObject {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "date_opened")
    private Date dateOpened;

    @NotNull
    @Column(name = "date_closed")
    private Date dateClosed;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "session", cascade = CascadeType.ALL)
    private List<Request> requestList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public void setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(Date dateClosed) {
        this.dateClosed = dateClosed;
    }

    public List<Request> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<Request> requestList) {
        this.requestList = requestList;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + getId() +
                ", dateOpened=" + dateOpened +
                ", dateClosed=" + dateClosed +
                ", requestList=" + requestList +
                '}';
    }
}