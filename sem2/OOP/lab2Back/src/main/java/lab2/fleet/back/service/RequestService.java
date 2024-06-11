package lab2.fleet.back.service;

import lab2.fleet.back.entity.Request;
import lab2.fleet.back.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public Optional<Request> getRequestById(String id) {
        return requestRepository.findById(id);
    }

    public Request createRequest(Request request) {
        return requestRepository.save(request);
    }

    public Request updateRequest(String id, Request requestDetails) {
        Optional<Request> optionalRequest = requestRepository.findById(id);
        if(optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            // Update the request details here
            // For example, if Request has a name field, you can do:
            // request.setName(requestDetails.getName());
            return requestRepository.save(request);
        } else {
            throw new RuntimeException("Request not found with id: " + id);
        }
    }

    public void deleteRequest(String id) {
        if(requestRepository.existsById(id)) {
            requestRepository.deleteById(id);
        } else {
            throw new RuntimeException("Request not found with id: " + id);
        }
    }
}