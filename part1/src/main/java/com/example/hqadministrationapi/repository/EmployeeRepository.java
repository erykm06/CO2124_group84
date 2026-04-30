package com.example.hqadministrationapi.repository;

import com.example.hqadministrationapi.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Returns employees who have at least one Assignment whose Department.name
     * matches the given name (case-insensitive). Used by the ?department= filter.
     */
    List<Employee> findDistinctByAssignments_Department_NameIgnoreCase(String name);
}