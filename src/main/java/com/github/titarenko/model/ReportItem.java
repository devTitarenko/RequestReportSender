package com.github.titarenko.model;

import java.lang.reflect.Array;
import java.util.*;

public class ReportItem implements Iterable, Comparable<ReportItem> {

    private String countryName;
    private String locationName;
    private Long userName;
    private String groupName;
    private Date dateOpened;
    private Date dateClosed;
    private String url;
    private String method;
    private String params;

    private Object[] objectsArray;
    private int cursor;

    public ReportItem(String countryName, String locationName, Long userName,
                      String groupName, Date dateOpened, Date dateClosed,
                      String url, String method, String params) {
        this.countryName = countryName;
        this.locationName = locationName;
        this.userName = userName;
        this.groupName = groupName;
        this.dateOpened = dateOpened;
        this.dateClosed = dateClosed;
        this.url = url;
        this.method = method;
        this.params = params;
        objectsArray = new Object[]{
                this.countryName,
                this.locationName,
                this.userName,
                this.groupName,
                this.dateOpened,
                this.dateClosed,
                this.url,
                this.method,
                this.params};
        System.out.println(Arrays.toString(objectsArray));
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            @Override
            public boolean hasNext() {
                return cursor < objectsArray.length;
            }

            @Override
            public Object next() {
                return objectsArray[cursor++];
            }
        };
    }

    @Override
    public int compareTo(ReportItem that) {
        int countryCompare = countryName.compareTo(that.getCountryName());
        int locationCompare = locationName.compareTo(that.getLocationName());
        int dateCompare = dateOpened.compareTo(that.getDateOpened());
        return countryCompare == 0 ? (locationCompare == 0 ? dateCompare : locationCompare) : countryCompare;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getLocationName() {
        return locationName;
    }

    public Long getUserName() {
        return userName;
    }

    public String getGroupName() {
        return groupName;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public Date getDateClosed() {
        return dateClosed;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getParams() {
        return params;
    }
}