package com.wj.updatecenter.softwaremanagementservice.testhelper;

import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.ApplicationVersion;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionResponseDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetSimplifiedApplicationVersionResponseDto;

import java.time.LocalDateTime;

public class ApplicationVersionTestsHelper {
    private static final long DUMMY_VERSION_APPLICATION_ID = 1L;
    private static final int DUMMY_VERSION_MAJOR = 5;
    private static final int DUMMY_VERSION_MINOR = 5;
    private static final int DUMMY_VERSION_PATCH = 1;
    private static final int DUMMY_VERSION_BUILD = 1;
    private static final boolean DUMMY_VERSION_CURRENT = true;
    private static final String DUMMY_VERSION_CHANGELOG = "changelog";

    public static final long DUMMY_VERSION_ID = 1L;
    public static final long DUMMY_VERSION_CREATED_BY = 1L;
    public static final long DUMMY_VERSION_UPDATED_BY = 2L;
    public static final LocalDateTime DUMMY_VERSION_CREATED_AT = LocalDateTime.of(2024, 1, 23, 22, 22, 22);
    public static final LocalDateTime DUMMY_VERSION_UPDATED_AT = LocalDateTime.of(2024, 1, 26, 23, 23, 23);

    public static final String DUMMY_EXCEPTION_MESSAGE = "message-that-should-not-be-changed";

    public static GetApplicationVersionDetailsDto createDummyGetApplicationVersionDetailsDto(long id) {
        return new GetApplicationVersionDetailsDto(
                id,
                DUMMY_VERSION_APPLICATION_ID,
                DUMMY_VERSION_MAJOR,
                DUMMY_VERSION_MINOR,
                DUMMY_VERSION_PATCH,
                DUMMY_VERSION_BUILD,
                DUMMY_VERSION_MAJOR + "." +DUMMY_VERSION_MINOR + "." + DUMMY_VERSION_PATCH + "." + DUMMY_VERSION_BUILD,
                DUMMY_VERSION_CURRENT,
                DUMMY_VERSION_CHANGELOG,
                DUMMY_VERSION_CREATED_BY,
                DUMMY_VERSION_CREATED_AT,
                DUMMY_VERSION_UPDATED_BY,
                DUMMY_VERSION_UPDATED_AT
        );
    }

    public static ApplicationVersion createSimpleApplicationVersion() {
        ApplicationVersion applicationVersion = new ApplicationVersion();
        applicationVersion.setApplicationId(DUMMY_VERSION_APPLICATION_ID);
        applicationVersion.setMajor(DUMMY_VERSION_MAJOR);
        applicationVersion.setMinor(DUMMY_VERSION_MINOR);
        applicationVersion.setPatch(DUMMY_VERSION_PATCH);
        applicationVersion.setBuild(DUMMY_VERSION_BUILD);
        applicationVersion.setCurrent(DUMMY_VERSION_CURRENT);
        applicationVersion.setChangelog(DUMMY_VERSION_CHANGELOG);
        return applicationVersion;
    }

    public static ApplicationVersion createDummyApplicationVersion(long id) {
        ApplicationVersion applicationVersion = new ApplicationVersion();
        applicationVersion.setId(id);
        applicationVersion.setApplicationId(DUMMY_VERSION_APPLICATION_ID);
        applicationVersion.setMajor(DUMMY_VERSION_MAJOR);
        applicationVersion.setMinor(DUMMY_VERSION_MINOR);
        applicationVersion.setPatch(DUMMY_VERSION_PATCH);
        applicationVersion.setBuild(DUMMY_VERSION_BUILD);
        applicationVersion.setCurrent(DUMMY_VERSION_CURRENT);
        applicationVersion.setChangelog(DUMMY_VERSION_CHANGELOG);
        applicationVersion.setCreatedBy(DUMMY_VERSION_CREATED_BY);
        applicationVersion.setCreatedAt(DUMMY_VERSION_CREATED_AT);
        applicationVersion.setUpdatedBy(DUMMY_VERSION_UPDATED_BY);
        applicationVersion.setUpdatedAt(DUMMY_VERSION_UPDATED_AT);
        return applicationVersion;
    }

    public static GetSimplifiedApplicationVersionResponseDto createDummyGetSimplifiedApplicationVersionResponseDto(
            long id) {
        return new GetSimplifiedApplicationVersionResponseDto(
                id,
                DUMMY_VERSION_MAJOR + "." +
                        DUMMY_VERSION_MINOR + "." +
                        DUMMY_VERSION_PATCH + "." +
                        DUMMY_VERSION_BUILD,
                DUMMY_VERSION_CURRENT
        );
    }

    public static CreateApplicationVersionRequestDto createDummyCreateApplicationVersionRequestDto() {
        return new CreateApplicationVersionRequestDto(
                DUMMY_VERSION_MAJOR,
                DUMMY_VERSION_MINOR,
                DUMMY_VERSION_PATCH,
                DUMMY_VERSION_BUILD,
                DUMMY_VERSION_CURRENT,
                DUMMY_VERSION_CHANGELOG);
    }

    public static CreateApplicationVersionResponseDto createDummyCreateApplicationVersionResponseDto(long id) {
        return new CreateApplicationVersionResponseDto(
                id,
                DUMMY_VERSION_APPLICATION_ID,
                DUMMY_VERSION_MAJOR,
                DUMMY_VERSION_MINOR,
                DUMMY_VERSION_PATCH,
                DUMMY_VERSION_BUILD,
                DUMMY_VERSION_CURRENT,
                DUMMY_VERSION_CHANGELOG,
                DUMMY_VERSION_CREATED_BY,
                DUMMY_VERSION_CREATED_AT,
                DUMMY_VERSION_UPDATED_BY,
                DUMMY_VERSION_UPDATED_AT
        );
    }
}
