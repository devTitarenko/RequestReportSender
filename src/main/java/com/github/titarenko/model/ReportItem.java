package com.github.titarenko.model;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

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

    public static Object[] header;
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

        header = new Object[]{"Country", "Location", "User name",
                "User group", "Session date opened", "Session date closed",
                "Request URL", "Request method", "Request params"};
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportItem that = (ReportItem) o;

        return Arrays.equals(objectsArray, that.objectsArray);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(objectsArray);
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