package com.example.demo.controller;

import com.example.demo.entities.ApplicationRequest;
import com.example.demo.entities.Operator;
import com.example.demo.service.OperatorService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/operators")
public class OperatorController {
    private final OperatorService service;
    public OperatorController(OperatorService service) { this.service = service; }

    @GetMapping
    public List<Operator> getAll() { return service.findAll(); }

    @PostMapping
    public ResponseEntity<Operator> add(@RequestBody Operator o) {
        return new ResponseEntity<>(service.addOperator(o), HttpStatus.CREATED);
    }

    @PutMapping("/{operatorId}/assign/{requestId}")
    public ResponseEntity<ApplicationRequest> assign(@PathVariable Long operatorId, @PathVariable Long requestId) {
        ApplicationRequest updated = service.assignOperator(operatorId, requestId);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperator(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
