package com.fleet.servlets;

import com.fleet.services.RideRequestService    ;
import com.fleet.models.Request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RequestServlet", urlPatterns = "/requests")
public class RequestServlet extends HttpServlet {
    private RideRequestService     requestService;

    @Override
    public void init() throws ServletException {
        requestService = new RideRequestService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Request> requests = requestService.getRideRequests();
        // Convert the list of requests to JSON and send it as the response
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle POST request to create a new request
        Request requestObj = new Request(/* parse request parameters */);
        requestService.saveRideRequest(requestObj);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle PUT request to update an existing request
        Request updatedRequest = new Request(/* parse request parameters */);
        requestService.updateRideRequest(updatedRequest);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Handle DELETE request to delete an existing request
        Long requestId = Long.parseLong(request.getParameter("id"));
        requestService.deleteRideRequest(requestId);
    }
}