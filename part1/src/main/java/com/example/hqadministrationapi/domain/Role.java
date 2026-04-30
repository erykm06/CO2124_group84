package com.example.hqadministrationapi.domain;

/**
 * Two-role RBAC model for the HQ Administration API.
 * Stored as STRING in the DB. When loaded by Spring Security the authority
 * is prefixed with "ROLE_" to become ROLE_HR / ROLE_MANAGER.
 */
public enum Role {
    HR,
    MANAGER
}