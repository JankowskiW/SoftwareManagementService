package com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto;

public record CreateApplicationRequestDto(
        String name,
        String description,
        String repositoryUrl,
        String documentationUrl,
        long businessOwnerId,
        long assignee
) { }
