package com.example.hqadministrationapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssignmentRequest(
        @NotNull Long employeeId,
        @NotNull Long departmentId,
        @NotBlank String role,
        @NotBlank String accessLevel
) {}