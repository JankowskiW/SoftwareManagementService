package com.wj.updatecenter.softwaremanagementservice.domain.application.mapper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.*;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public GetApplicationDetailsDto toGetApplicationDetailsDto(
            Application application,
            GetApplicationVersionDetailsDto currentApplicationVersion) {
        return new GetApplicationDetailsDto(
                application.getId(),
                application.getName(),
                application.getDescription(),
                application.getRepositoryUrl(),
                application.getDocumentationUrl(),
                application.getBusinessOwnerId(),
                application.getAssigneeId(),
                application.getCreatedBy(),
                application.getCreatedAt(),
                application.getUpdatedBy(),
                application.getUpdatedAt(),
                application.getArchivedBy(),
                application.getArchivedAt(),
                application.getArchived(),
                currentApplicationVersion.fullVersion(),
                currentApplicationVersion.createdBy(),
                currentApplicationVersion.createdAt()
        );
    }

    public GetSimplifiedApplicationResponseDto toGetSimplifiedApplicationResponseDto(Application application) {
        String currentVersion = application.getCurrentVersion() == null ? "" : application.getCurrentVersion();
        return new GetSimplifiedApplicationResponseDto(
                application.getId(),
                application.getName(),
                currentVersion
        );
    }

    public Application toApplication(CreateApplicationRequestDto createApplicationRequestDto) {
        Application application = new Application();
        application.setName(createApplicationRequestDto.name());
        application.setDescription(createApplicationRequestDto.description());
        application.setRepositoryUrl(createApplicationRequestDto.repositoryUrl());
        application.setDocumentationUrl(createApplicationRequestDto.documentationUrl());
        application.setBusinessOwnerId(createApplicationRequestDto.businessOwnerId());
        application.setAssigneeId(createApplicationRequestDto.assigneeId());
        return application;
    }

    public CreateApplicationResponseDto toCreateApplicationResponseDto(Application application) {
        return new CreateApplicationResponseDto(
                application.getId(),
                application.getName(),
                application.getDescription(),
                application.getRepositoryUrl(),
                application.getDocumentationUrl(),
                application.getBusinessOwnerId(),
                application.getAssigneeId()
        );
    }

    public Application toApplication(UpdateApplicationRequestDto updateApplicationRequestDto, long id) {
        Application application = new Application();
        application.setId(id);
        application.setName(updateApplicationRequestDto.name());
        application.setDescription(updateApplicationRequestDto.description());
        application.setRepositoryUrl(updateApplicationRequestDto.repositoryUrl());
        application.setDocumentationUrl(updateApplicationRequestDto.documentationUrl());
        application.setBusinessOwnerId(updateApplicationRequestDto.businessOwnerId());
        application.setAssigneeId(updateApplicationRequestDto.assigneeId());
        application.setCurrentVersion(updateApplicationRequestDto.currentVersion());
        return application;
    }

    public UpdateApplicationResponseDto toUpdateApplicationResponseDto(Application application) {
        return new UpdateApplicationResponseDto(
                application.getId(),
                application.getName(),
                application.getDescription(),
                application.getRepositoryUrl(),
                application.getDocumentationUrl(),
                application.getBusinessOwnerId(),
                application.getAssigneeId(),
                application.getCreatedBy(),
                application.getCreatedAt(),
                application.getUpdatedBy(),
                application.getUpdatedAt()
        );
    }
}
