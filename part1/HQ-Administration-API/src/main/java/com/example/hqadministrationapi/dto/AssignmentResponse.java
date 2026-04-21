package com.example.hqadministrationapi.dto;

import com.example.hqadministrationapi.domain.Assignment;

public record AssignmentResponse(
        Long id,
        Long employeeId,
        Long departmentId,
        String role,
        String accessLevel
) {
    public static AssignmentResponse from(Assignment a) {
        return new AssignmentResponse(
                a.getId(),
                a.getEmployee().getId(),
                a.getDepartment().getId(),
                a.getRole(),
                a.getAccessLevel()
        );
    }
}