package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.mapper;

import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.ApplicationVersion;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionResponseDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetSimplifiedApplicationVersionResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ApplicationVersionMapper {
    public GetApplicationVersionDetailsDto toGetApplicationVersionDetailsDto(ApplicationVersion applicationVersion) {
        return new GetApplicationVersionDetailsDto(
                applicationVersion.getId(),
                applicationVersion.getApplicationId(),
                applicationVersion.getMajor(),
                applicationVersion.getMinor(),
                applicationVersion.getPatch(),
                applicationVersion.getBuild(),
                applicationVersion.getFullVersion(),
                applicationVersion.isCurrent(),
                applicationVersion.getChangelog(),
                applicationVersion.getCreatedBy(),
                applicationVersion.getCreatedAt(),
                applicationVersion.getUpdatedBy(),
                applicationVersion.getUpdatedAt()
        );
    }

    public GetSimplifiedApplicationVersionResponseDto toGetSimplifiedApplicationVersionResponseDto(
            ApplicationVersion applicationVersion) {
        return new GetSimplifiedApplicationVersionResponseDto(
                applicationVersion.getId(),
                applicationVersion.getFullVersion(),
                applicationVersion.isCurrent()
        );
    }

    public ApplicationVersion toApplicationVersion(
            CreateApplicationVersionRequestDto createApplicationVersionRequestDto,
            long applicationId) {
        ApplicationVersion applicationVersion = new ApplicationVersion();
        applicationVersion.setApplicationId(applicationId);
        applicationVersion.setMajor(createApplicationVersionRequestDto.major());
        applicationVersion.setMinor(createApplicationVersionRequestDto.minor());
        applicationVersion.setPatch(createApplicationVersionRequestDto.patch());
        applicationVersion.setBuild(createApplicationVersionRequestDto.build());
        applicationVersion.setCurrent(createApplicationVersionRequestDto.current());
        applicationVersion.setChangelog(createApplicationVersionRequestDto.changelog());
        return applicationVersion;
    }

    public CreateApplicationVersionResponseDto toCreateApplicationVersionResponseDto(ApplicationVersion applicationVersion) {
        return new CreateApplicationVersionResponseDto(
          applicationVersion.getId(),
          applicationVersion.getApplicationId(),
          applicationVersion.getMajor(),
          applicationVersion.getMinor(),
          applicationVersion.getPatch(),
          applicationVersion.getBuild(),
          applicationVersion.isCurrent(),
          applicationVersion.getChangelog(),
          applicationVersion.getCreatedBy(),
          applicationVersion.getCreatedAt(),
          applicationVersion.getUpdatedBy(),
          applicationVersion.getUpdatedAt()
        );
    }
}
