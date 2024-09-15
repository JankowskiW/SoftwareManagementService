package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto;

import java.time.LocalDateTime;

public record CreateApplicationVersionResponseDto(
    long id,
    long applicationId,
    int major,
    int minor,
    int patch,
    int build,
    boolean current,
    String changelog,
    long createdBy,
    LocalDateTime createdAt,
    long updatedBy,
    LocalDateTime updatedAt
) { }
