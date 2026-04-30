package com.example.hqadministrationapi.service;

import com.example.hqadministrationapi.domain.Assignment;
import com.example.hqadministrationapi.domain.Department;
import com.example.hqadministrationapi.domain.Employee;
import com.example.hqadministrationapi.dto.AssignmentRequest;
import com.example.hqadministrationapi.repository.AssignmentRepository;
import com.example.hqadministrationapi.repository.DepartmentRepository;
import com.example.hqadministrationapi.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssignmentService {

    private final AssignmentRepository assignments;
    private final EmployeeRepository employees;
    private final DepartmentRepository departments;

    public AssignmentService(AssignmentRepository assignments,
                             EmployeeRepository employees,
                             DepartmentRepository departments) {
        this.assignments = assignments;
        this.employees = employees;
        this.departments = departments;
    }

    @Transactional
    public Assignment create(AssignmentRequest req) {
        Employee emp = employees.findById(req.employeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee " + req.employeeId() + " not found"));
        Department dep = departments.findById(req.departmentId())
                .orElseThrow(() -> new EntityNotFoundException("Department " + req.departmentId() + " not found"));

        Assignment a = new Assignment();
        a.setEmployee(emp);
        a.setDepartment(dep);
        a.setRole(req.role());
        a.setAccessLevel(req.accessLevel());
        return assignments.save(a);
    }

    @Transactional
    public void delete(Long id) {
        if (!assignments.existsById(id)) {
            throw new EntityNotFoundException("Assignment " + id + " not found");
        }
        assignments.deleteById(id);
    }
}