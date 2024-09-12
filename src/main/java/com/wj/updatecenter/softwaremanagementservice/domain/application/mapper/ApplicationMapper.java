package com.wj.updatecenter.softwaremanagementservice.domain.application.mapper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.GetApplicationDetailsDto;
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
                application.isArchived(),
                currentApplicationVersion.fullVersion(),
                currentApplicationVersion.createdBy(),
                currentApplicationVersion.createdAt()
        );
    }
}
