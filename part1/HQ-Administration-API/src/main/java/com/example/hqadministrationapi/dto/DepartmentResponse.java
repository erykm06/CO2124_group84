package com.example.hqadministrationapi.dto;

import com.example.hqadministrationapi.domain.Department;

import java.math.BigDecimal;

public record DepartmentResponse(
        Long id,
        String name,
        BigDecimal budget,
        String location
) {
    public static DepartmentResponse from(Department d) {
        return new DepartmentResponse(d.getId(), d.getName(), d.getBudget(), d.getLocation());
    }
}