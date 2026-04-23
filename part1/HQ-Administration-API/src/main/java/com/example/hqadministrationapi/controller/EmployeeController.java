package com.example.hqadministrationapi.controller;

import com.example.hqadministrationapi.domain.Employee;
import com.example.hqadministrationapi.dto.EmployeeCreateRequest;
import com.example.hqadministrationapi.dto.EmployeeResponse;
import com.example.hqadministrationapi.dto.EmployeeUpdateRequest;
import com.example.hqadministrationapi.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }


    @GetMapping
    public List<EmployeeResponse> list(@RequestParam(required = false) String department) {
        List<Employee> employees = service.list(department);
        List<EmployeeResponse> result = new ArrayList<>();
        for (Employee e : employees) {
            result.add(EmployeeResponse.from(e));
        }
        return result;
    }


    @GetMapping("/{id}")
    public EmployeeResponse get(@PathVariable Long id) {
        return EmployeeResponse.from(service.get(id));
    }


    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody EmployeeCreateRequest req) {
        var created = service.create(req);
        return ResponseEntity
                .created(URI.create("/api/employees/" + created.getId()))
                .body(EmployeeResponse.from(created));
    }


    @PutMapping("/{id}")
    public EmployeeResponse update(@PathVariable Long id,
                                   @Valid @RequestBody EmployeeUpdateRequest req) {
        return EmployeeResponse.from(service.update(id, req));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}/promote")
    public EmployeeResponse promote(@PathVariable Long id) {
        return EmployeeResponse.from(service.promote(id));
    }
}