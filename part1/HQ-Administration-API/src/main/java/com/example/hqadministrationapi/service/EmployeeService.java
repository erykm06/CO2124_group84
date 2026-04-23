package com.example.hqadministrationapi.service;

import com.example.hqadministrationapi.domain.Employee;
import com.example.hqadministrationapi.domain.Rank;
import com.example.hqadministrationapi.dto.EmployeeCreateRequest;
import com.example.hqadministrationapi.dto.EmployeeUpdateRequest;
import com.example.hqadministrationapi.exception.NotEligibleException;
import com.example.hqadministrationapi.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employees;

    public EmployeeService(EmployeeRepository employees) {
        this.employees = employees;
    }

    @Transactional(readOnly = true)
    public List<Employee> list(String departmentFilter) {
        if (departmentFilter == null || departmentFilter.isBlank()) {
            return employees.findAll();
        }
        return employees.findDistinctByAssignments_Department_NameIgnoreCase(departmentFilter);
    }

    @Transactional(readOnly = true)
    public Employee get(Long id) {
        return employees.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee " + id + " not found"));
    }

    @Transactional
    public Employee create(EmployeeCreateRequest req) {
        Employee e = new Employee();
        e.setName(req.name());
        e.setEmail(req.email());
        e.setAddress(req.address());
        e.setContractType(req.contractType());
        e.setStartDate(req.startDate());
        e.setSalary(req.salary());
        return employees.save(e);
    }

    @Transactional
    public Employee update(Long id, EmployeeUpdateRequest req) {
        Employee e = get(id);
        if (req.name() != null)         e.setName(req.name());
        if (req.email() != null)        e.setEmail(req.email());
        if (req.address() != null)      e.setAddress(req.address());
        if (req.contractType() != null) e.setContractType(req.contractType());
        if (req.startDate() != null)    e.setStartDate(req.startDate());
        if (req.salary() != null)       e.setSalary(req.salary());
        return e;
    }

    @Transactional
    public void delete(Long id) {
        if (!employees.existsById(id)) {
            throw new EntityNotFoundException("There is no employee with id " + id);
        }
        employees.deleteById(id);
    }

    //Promotes an employee from junior to senior
    @Transactional
    public Employee promote(Long id) {
        Employee e = get(id);

        if (e.getStartDate() == null
                || e.getStartDate().isAfter(LocalDate.now().minusMonths(6))) {
            throw new NotEligibleException("Employee started working less than 6 months ago");
        }
        if (e.getRank() == Rank.SENIOR) {
            throw new NotEligibleException("Employee is already a Senior");
        }

        e.setRank(e.getRank() == Rank.JUNIOR ? Rank.INTERMEDIATE : Rank.SENIOR);

        BigDecimal newSalary = e.getSalary()
                .multiply(BigDecimal.valueOf(1.10))
                .setScale(2, RoundingMode.HALF_UP);
        e.setSalary(newSalary);

        return e;
    }
}