package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto;

import java.time.LocalDateTime;

public record GetApplicationVersionDetailsDto(
        long id,
        long applicationId,
        int major,
        int minor,
        int patch,
        int build,
        String fullVersion,
        boolean current,
        String changelog,
        long createdBy,
        LocalDateTime createdAt,
        long updatedBy,
        LocalDateTime updatedAt
) { }
