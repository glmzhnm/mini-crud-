package com.example.demo.controller;

import com.example.demo.entities.ApplicationRequest;
import com.example.demo.service.ApplicationRequestService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {
    private final ApplicationRequestService service;
    public RequestController(ApplicationRequestService service) { this.service = service; }

    @GetMapping public List<ApplicationRequest> findAll() { return service.findAll(); }
    @GetMapping("/{id}") public ApplicationRequest findById(@PathVariable Long id) { return service.findById(id); }
    @PostMapping public ResponseEntity<ApplicationRequest> add(@RequestBody ApplicationRequest r) {
        return new ResponseEntity<>(service.add(r), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationRequest> updateRequest(
            @PathVariable Long id,
            @RequestBody ApplicationRequest updatedRequest) {
        ApplicationRequest existing = service.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }

        existing.setUserName(updatedRequest.getUserName());
        existing.setPhone(updatedRequest.getPhone());
        existing.setCommentary(updatedRequest.getCommentary());
        existing.setHandled(updatedRequest.isHandled());
        existing.setCourse(updatedRequest.getCourse());
        existing.setOperators(updatedRequest.getOperators());
        ApplicationRequest saved = service.add(existing);
        return ResponseEntity.ok(saved);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
