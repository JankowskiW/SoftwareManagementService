package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto;

public record GetSimplifiedApplicationVersionResponseDto(
        long id,
        String fullVersion,
        boolean current
) { }
