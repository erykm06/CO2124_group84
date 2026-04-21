package com.example.hqadministrationapi.dto;

import com.example.hqadministrationapi.domain.ContractType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeCreateRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotNull ContractType contractType,
        @NotNull LocalDate startDate,
        @NotNull @Positive BigDecimal salary,
        String address
) {}