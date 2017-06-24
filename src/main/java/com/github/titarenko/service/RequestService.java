package com.github.titarenko.service;

import com.github.titarenko.model.Request;

import java.util.Date;
import java.util.List;

public interface RequestService {
    List<Request> getAllRequests();

    List<Request> getRequestsByDate(Date date);
}
