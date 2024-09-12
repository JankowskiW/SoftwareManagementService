package com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto;

import java.time.LocalDateTime;

public record UpdateApplicationResponseDto(
        long id,
        String name,
        String description,
        String repositoryUrl,
        String documentationUrl,
        String businessOwner,
        String assignee,
        String createdBy,
        LocalDateTime createdAt,
        String updatedBy,
        LocalDateTime updatedAt,
        boolean archived,
        String archivedBy,
        LocalDateTime archivedAt
) { }