package com.fleet.dao.interfaces;

import com.fleet.models.Request;

import java.util.List;

public interface RequestDAO {
    void saveRequest(Request request);

    Request getRequestById(Long id);

    List<Request> getAllRequests();

    void updateRequest(Request request);

    void deleteRequest(Request request);
}
