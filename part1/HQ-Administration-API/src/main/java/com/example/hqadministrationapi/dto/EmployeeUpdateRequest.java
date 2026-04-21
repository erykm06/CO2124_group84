package com.example.hqadministrationapi.dto;

import com.example.hqadministrationapi.domain.ContractType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeUpdateRequest(
        String name,
        @Email String email,
        ContractType contractType,
        LocalDate startDate,
        @Positive BigDecimal salary,
        String address
) {}