package com.wj.updatecenter.softwaremanagementservice.testhelper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.*;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;

import java.time.LocalDateTime;

public class ApplicationTestsHelper {

    public static final long DUMMY_APPLICATION_ID = 1L;
    public static final String DUMMY_APPLICATION_NAME = "Dummy Application";
    public static final String DUMMY_APPLICATION_DESCRIPTION = "Dummy Description";
    public static final String DUMMY_APPLICATION_REPOSITORY_URL = "https://github.com/user/repo";
    public static final String DUMMY_APPLICATION_DOCUMENTATION_URL = "ftp://192.168.0.1";
    public static final long DUMMY_APPLICATION_BUSINESS_OWNER_ID = 11L;
    public static final long DUMMY_APPLICATION_ASSIGNEE_ID = 21L;
    public static final String DUMMY_APPLICATION_CURRENT_VERSION = "1.2.3.4";
    public static final long DUMMY_APPLICATION_CREATED_BY = 1L;
    public static final long DUMMY_APPLICATION_UPDATED_BY = 2L;
    public static final long DUMMY_APPLICATION_ARCHIVED_BY = 3L;
    public static final boolean DUMMY_APPLICATION_ARCHIVED = true;
    public static final LocalDateTime DUMMY_APPLICATION_CREATED_AT = LocalDateTime.of(2024, 1, 23, 22, 22, 22);
    public static final LocalDateTime DUMMY_APPLICATION_UPDATED_AT = LocalDateTime.of(2024, 1, 26, 23, 23, 23);
    public static final LocalDateTime DUMMY_APPLICATION_ARCHIVED_AT = LocalDateTime.of(2024, 1, 26, 23, 23, 23);

    public static final int DUMMY_VERSION_MAJOR = 1;
    public static final int DUMMY_VERSION_MINOR = 2;
    public static final int DUMMY_VERSION_PATCH = 3;
    public static final int DUMMY_VERSION_BUILD = 4;
    public static final String DUMMY_VERSION_FULL = DUMMY_APPLICATION_CURRENT_VERSION;
    public static final boolean DUMMY_VERSION_CURRENT = true;
    public static final String DUMMY_VERSION_CHANGELOG = "changelog";
    public static final long DUMMY_VERSION_CREATED_BY = 1L;
    public static final LocalDateTime DUMMY_VERSION_CREATED_AT = LocalDateTime.of(2024, 1, 27, 22, 22, 22);
    public static final long DUMMY_VERSION_UPDATED_BY = 2L;
    public static final LocalDateTime DUMMY_VERSION_UPDATED_AT = LocalDateTime.of(2024, 1, 27, 23, 23, 23);

    public static UpdateApplicationRequestDto createDummyUpdateApplicationRequestDto() {
        return new UpdateApplicationRequestDto(
                DUMMY_APPLICATION_NAME,
                DUMMY_APPLICATION_DESCRIPTION,
                DUMMY_APPLICATION_REPOSITORY_URL,
                DUMMY_APPLICATION_DOCUMENTATION_URL,
                DUMMY_APPLICATION_BUSINESS_OWNER_ID,
                DUMMY_APPLICATION_ASSIGNEE_ID,
                DUMMY_APPLICATION_CURRENT_VERSION,
                DUMMY_APPLICATION_ARCHIVED
        );
    }

    public static CreateApplicationResponseDto createDummyCreateApplicationResponseDto(long id) {
        return new CreateApplicationResponseDto(
                id,
                DUMMY_APPLICATION_NAME,
                DUMMY_APPLICATION_DESCRIPTION,
                DUMMY_APPLICATION_REPOSITORY_URL,
                DUMMY_APPLICATION_DOCUMENTATION_URL,
                DUMMY_APPLICATION_BUSINESS_OWNER_ID,
                DUMMY_APPLICATION_ASSIGNEE_ID
        );
    }

    public static CreateApplicationRequestDto createDummyCreateApplicationRequestDto() {
        return new CreateApplicationRequestDto(
                DUMMY_APPLICATION_NAME,
                DUMMY_APPLICATION_DESCRIPTION,
                DUMMY_APPLICATION_REPOSITORY_URL,
                DUMMY_APPLICATION_DOCUMENTATION_URL,
                DUMMY_APPLICATION_BUSINESS_OWNER_ID,
                DUMMY_APPLICATION_ASSIGNEE_ID
        );
    }

    public static GetSimplifiedApplicationResponseDto createDummyGetSimplifiedApplicationResponseDtoWithoutCurrentVersion(
            long applicationId) {
        return new GetSimplifiedApplicationResponseDto(
                applicationId,
                DUMMY_APPLICATION_NAME,
                "");
    }

    public static GetSimplifiedApplicationResponseDto createDummyGetSimplifiedApplicationResponseDtoWithCurrentVersion(
            long applicationId) {
        return new GetSimplifiedApplicationResponseDto(
                applicationId,
                DUMMY_APPLICATION_NAME,
                DUMMY_VERSION_FULL);
    }

    public static GetApplicationVersionDetailsDto createDummyGetApplicationVersionDetailsDto(long versionId, long applicationId) {
        return new GetApplicationVersionDetailsDto(
                versionId,
                applicationId,
                DUMMY_VERSION_MAJOR,
                DUMMY_VERSION_MINOR,
                DUMMY_VERSION_PATCH,
                DUMMY_VERSION_BUILD,
                DUMMY_VERSION_FULL,
                DUMMY_VERSION_CURRENT,
                DUMMY_VERSION_CHANGELOG,
                DUMMY_VERSION_CREATED_BY,
                DUMMY_VERSION_CREATED_AT,
                DUMMY_VERSION_UPDATED_BY,
                DUMMY_VERSION_UPDATED_AT
        );
    }

    public static Application createDummyApplication(long id) {
        Application application = new Application();
        application.setId(id);
        application.setName(DUMMY_APPLICATION_NAME);
        application.setDescription(DUMMY_APPLICATION_DESCRIPTION);
        application.setRepositoryUrl(DUMMY_APPLICATION_REPOSITORY_URL);
        application.setDocumentationUrl(DUMMY_APPLICATION_DOCUMENTATION_URL);
        application.setBusinessOwnerId(DUMMY_APPLICATION_BUSINESS_OWNER_ID);
        application.setAssigneeId(DUMMY_APPLICATION_ASSIGNEE_ID);
        application.setCurrentVersion(DUMMY_VERSION_FULL);
        application.setCreatedBy(DUMMY_APPLICATION_CREATED_BY);
        application.setCreatedAt(DUMMY_APPLICATION_CREATED_AT);
        application.setUpdatedBy(DUMMY_APPLICATION_UPDATED_BY);
        application.setUpdatedAt(DUMMY_APPLICATION_UPDATED_AT);
        application.setArchived(DUMMY_APPLICATION_ARCHIVED);
        application.setArchivedBy(DUMMY_APPLICATION_ARCHIVED_BY);
        application.setArchivedAt(DUMMY_APPLICATION_ARCHIVED_AT);
        return application;
    }

    public static Application createSimplyDummyApplication() {
        Application application = new Application();
        application.setName(DUMMY_APPLICATION_NAME);
        application.setDescription(DUMMY_APPLICATION_DESCRIPTION);
        application.setRepositoryUrl(DUMMY_APPLICATION_REPOSITORY_URL);
        application.setDocumentationUrl(DUMMY_APPLICATION_DOCUMENTATION_URL);
        application.setBusinessOwnerId(DUMMY_APPLICATION_BUSINESS_OWNER_ID);
        application.setAssigneeId(DUMMY_APPLICATION_ASSIGNEE_ID);
        return application;
    }

    public static GetApplicationDetailsDto createDummyGetApplicationDetailsDtoWithoutCurrentVersionFields(long id) {
        return new GetApplicationDetailsDto(
                id,
                DUMMY_APPLICATION_NAME,
                DUMMY_APPLICATION_DESCRIPTION,
                DUMMY_APPLICATION_REPOSITORY_URL,
                DUMMY_APPLICATION_DOCUMENTATION_URL,
                DUMMY_APPLICATION_BUSINESS_OWNER_ID,
                DUMMY_APPLICATION_ASSIGNEE_ID,
                DUMMY_APPLICATION_CREATED_BY,
                DUMMY_APPLICATION_CREATED_AT,
                DUMMY_APPLICATION_UPDATED_BY,
                DUMMY_APPLICATION_UPDATED_AT,
                DUMMY_APPLICATION_ARCHIVED_BY,
                DUMMY_APPLICATION_ARCHIVED_AT,
                DUMMY_APPLICATION_ARCHIVED,
                null,
                0L,
                null
        );
    }

    public static GetApplicationDetailsDto createDummyGetApplicationDetailsDtoWithCurrentVersionFields(long id) {
        return new GetApplicationDetailsDto(
                id,
                DUMMY_APPLICATION_NAME,
                DUMMY_APPLICATION_DESCRIPTION,
                DUMMY_APPLICATION_REPOSITORY_URL,
                DUMMY_APPLICATION_DOCUMENTATION_URL,
                DUMMY_APPLICATION_BUSINESS_OWNER_ID,
                DUMMY_APPLICATION_ASSIGNEE_ID,
                DUMMY_APPLICATION_CREATED_BY,
                DUMMY_APPLICATION_CREATED_AT,
                DUMMY_APPLICATION_UPDATED_BY,
                DUMMY_APPLICATION_UPDATED_AT,
                DUMMY_APPLICATION_ARCHIVED_BY,
                DUMMY_APPLICATION_ARCHIVED_AT,
                DUMMY_APPLICATION_ARCHIVED,
                DUMMY_VERSION_FULL,
                DUMMY_VERSION_CREATED_BY,
                DUMMY_VERSION_CREATED_AT
        );
    }
}
