package com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto;

public record CreateApplicationResponseDto(
        long id,
        String name,
        String description,
        String repositoryUrl,
        String documentationUrl,
        String businessOwner,
        String assignee
) { }
