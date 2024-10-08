package com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto;

public record UpdateApplicationRequestDto(
        String name,
        String description,
        String repositoryUrl,
        String documentationUrl,
        Long businessOwnerId,
        Long assigneeId,
        String currentVersion,
        Boolean archived
) { }
