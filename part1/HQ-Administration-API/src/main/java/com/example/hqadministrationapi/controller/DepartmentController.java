package com.example.hqadministrationapi.controller;

import com.example.hqadministrationapi.domain.Department;
import com.example.hqadministrationapi.dto.DepartmentRequest;
import com.example.hqadministrationapi.dto.DepartmentResponse;
import com.example.hqadministrationapi.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping
    public List<DepartmentResponse> list() {
        List<DepartmentResponse> result = new ArrayList<>();
        for (Department d : service.list()) {
            result.add(new DepartmentResponse(d));
        }
        return result;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponse> create(@Valid @RequestBody DepartmentRequest req) {
        var created = service.create(req);
        return ResponseEntity
                .created(URI.create("/api/departments/" + created.getId()))
                .body(DepartmentResponse.from(created));
    }
}