package lab2.fleet.back.controller;

import lab2.fleet.back.entity.Request;
import lab2.fleet.back.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/requests")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173/")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    public ResponseEntity<List<Request>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable String id) {
        return requestService.getRequestById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Request> createRequest(@RequestBody Request request) {
        return ResponseEntity.ok(requestService.createRequest(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Request> updateRequest(@PathVariable String id, @RequestBody Request request) {
        return ResponseEntity.ok(requestService.updateRequest(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable String id) {
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}