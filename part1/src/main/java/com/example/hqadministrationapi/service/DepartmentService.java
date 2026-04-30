package com.example.hqadministrationapi.service;

import com.example.hqadministrationapi.domain.Department;
import com.example.hqadministrationapi.dto.DepartmentRequest;
import com.example.hqadministrationapi.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departments;

    public DepartmentService(DepartmentRepository departments) {
        this.departments = departments;
    }

    @Transactional(readOnly = true)
    public List<Department> list() {
        return departments.findAll();
    }

    @Transactional
    public Department create(DepartmentRequest req) {
        Department d = new Department();
        d.setName(req.name());
        d.setBudget(req.budget());
        d.setLocation(req.location());
        return departments.save(d);
    }
}