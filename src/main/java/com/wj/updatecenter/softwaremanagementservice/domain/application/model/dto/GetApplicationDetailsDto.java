package com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto;

import java.time.LocalDateTime;

public record GetApplicationDetailsDto(
    long id,
    String name,
    String description,
    String repositoryUrl,
    String documentationUrl,
    String businessOwner,
    String assignedTo,
    String createdBy,
    LocalDateTime createdAt,
    String updatedBy,
    LocalDateTime updatedAt,
    String archivedBy,
    LocalDateTime archivedAt,
    String currentVersion,
    LocalDateTime currentVersionReleasedAt,
    String currentVersionReleasedBy
) { }
