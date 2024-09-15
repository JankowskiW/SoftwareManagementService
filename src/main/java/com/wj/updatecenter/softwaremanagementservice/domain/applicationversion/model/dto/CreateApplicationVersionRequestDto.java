package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto;

public record CreateApplicationVersionRequestDto(
        int major,
        int minor,
        int patch,
        int build,
        boolean current,
        String changelog
) { }
