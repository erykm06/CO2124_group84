package com.example.hqadministrationapi.dto;

import com.example.hqadministrationapi.domain.ContractType;
import com.example.hqadministrationapi.domain.Employee;
import com.example.hqadministrationapi.domain.Rank;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String name,
        String email,
        ContractType contractType,
        LocalDate startDate,
        BigDecimal salary,
        String address,
        Rank rank
) {
    public static EmployeeResponse from(Employee e) {
        return new EmployeeResponse(
                e.getId(), e.getName(), e.getEmail(),
                e.getContractType(), e.getStartDate(), e.getSalary(),
                e.getAddress(), e.getRank()
        );
    }
}