package com.github.titarenko.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Titarenko on 19.06.2017 at 23:14.
 */
@Entity(name = "country" )
public class Country extends BaseObject{

    @NotNull
    @Column(name = "country_name")
    private String countryName;

    private String language;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country", cascade = CascadeType.ALL)
    private List<Location> locationList;

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }
}
