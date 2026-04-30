package com.example.hqadministrationapi.dto;

import java.math.BigDecimal;

public record DepartmentResponse(
        Long id,
        String name,
        BigDecimal budget,
        String location
) {}