package com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto;

import java.time.LocalDateTime;

public record GetApplicationDetailsDto(
    long id,
    String name,
    String description,
    String repositoryUrl,
    String documentationUrl,
    long businessOwnerId,
    long assigneeId,
    long createdBy,
    LocalDateTime createdAt,
    long updatedBy,
    LocalDateTime updatedAt,
    long archivedBy,
    LocalDateTime archivedAt,
    boolean archived,
    String currentVersion,
    long currentVersionReleasedBy,
    LocalDateTime currentVersionReleasedAt
) { }
