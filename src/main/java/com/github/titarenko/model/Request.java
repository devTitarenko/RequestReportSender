package com.github.titarenko.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NamedQueries({
        @NamedQuery(name = "RequestByDate",
                query = "SELECT r FROM request r JOIN r.session s " +
                        "WHERE :date BETWEEN s.dateOpened AND s.dateClosed")
})
@Entity(name = "request")
public class Request extends BaseObject {
    public static final String FIND_BY_DATE = "RequestByDate";

    @NotNull
    private String url;

    private String method;

    private String params;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private Session session;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + getId() +
                ", url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}
