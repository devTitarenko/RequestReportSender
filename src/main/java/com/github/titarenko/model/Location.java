package com.github.titarenko.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Titarenko on 19.06.2017 at 23:14.
 */
@Entity(name = "location")
public class Location extends BaseObject{

    @NotNull
    @Column(name = "location_name")
    private String locationName;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    private Double latitude;

    private Double longitude;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location", cascade = CascadeType.ALL)
    private List<User> userList;

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locationName='" + locationName + '\'' +
                ", country=" + country +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
