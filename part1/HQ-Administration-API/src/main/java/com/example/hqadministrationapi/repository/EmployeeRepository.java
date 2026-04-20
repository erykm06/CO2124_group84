package com.example.hqadministrationapi.repository;

import com.example.hqadministrationapi.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
