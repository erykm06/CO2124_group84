package com.example.hqadministrationapi.controller;

import com.example.hqadministrationapi.dto.AssignmentRequest;
import com.example.hqadministrationapi.dto.AssignmentResponse;
import com.example.hqadministrationapi.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService service;

    public AssignmentController(AssignmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AssignmentResponse> create(@Valid @RequestBody AssignmentRequest req) {
        var created = service.create(req);
        return ResponseEntity
                .created(URI.create("/api/assignments/" + created.getId()))
                .body(AssignmentResponse.from(created));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}