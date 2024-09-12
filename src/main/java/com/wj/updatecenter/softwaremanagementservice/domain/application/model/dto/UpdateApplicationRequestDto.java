package com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto;

public record UpdateApplicationRequestDto(
        String name,
        String description,
        String repositoryUrl,
        String documentationUrl,
        long businessOwnerId,
        long assigneeId
) { }
