package com.example.hqadministrationapi.repository;

import com.example.hqadministrationapi.domain.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
