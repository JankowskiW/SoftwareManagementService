package com.wj.updatecenter.softwaremanagementservice.testhelper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.*;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class ApplicationTestsHelper {
    public static final long DUMMY_COMMON_ID = 1L;
    public static final long DUMMY_APPLICATION_ID = DUMMY_COMMON_ID;
    public static final String DUMMY_APPLICATION_NAME = "Dummy Application";
    public static final String DUMMY_APPLICATION_DESCRIPTION = "Dummy Description";
    public static final String DUMMY_APPLICATION_REPOSITORY_URL = "https://github.com/user/repo";
    public static final String DUMMY_APPLICATION_DOCUMENTATION_URL = "ftp://192.168.0.1";
    public static final long DUMMY_APPLICATION_BUSINESS_OWNER_ID = DUMMY_COMMON_ID;
    public static final long DUMMY_APPLICATION_ASSIGNEE_ID = DUMMY_COMMON_ID;
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

    public static final String OLD_STRING_VALUE = "old-value";
    public static final String NEW_STRING_VALUE = "new-value";

    public static final Map<String, BiConsumer<Application, Object>> APPLICATION_FIELD_SETTERS = new HashMap<>();
    public static final Map<String, Function<Application, Object>> APPLICATION_FIELD_GETTERS = new HashMap<>();


    static {
        APPLICATION_FIELD_SETTERS.put("name", (app, value) -> app.setName((String) value));
        APPLICATION_FIELD_SETTERS.put("description", (app, value) -> app.setDescription((String) value));
        APPLICATION_FIELD_SETTERS.put("repositoryUrl", (app, value) -> app.setRepositoryUrl((String) value));
        APPLICATION_FIELD_SETTERS.put("documentationUrl", (app, value) -> app.setDocumentationUrl((String) value));
        APPLICATION_FIELD_SETTERS.put("currentVersion", (app, value) -> app.setCurrentVersion((String) value));
        APPLICATION_FIELD_SETTERS.put("businessOwnerId", (app, value) -> app.setBusinessOwnerId((Long) value));
        APPLICATION_FIELD_SETTERS.put("assigneeId", (app, value) -> app.setAssigneeId((Long) value));

        APPLICATION_FIELD_GETTERS.put("name", Application::getName);
        APPLICATION_FIELD_GETTERS.put("description", Application::getDescription);
        APPLICATION_FIELD_GETTERS.put("repositoryUrl", Application::getRepositoryUrl);
        APPLICATION_FIELD_GETTERS.put("documentationUrl", Application::getDocumentationUrl);
        APPLICATION_FIELD_GETTERS.put("currentVersion", Application::getCurrentVersion);
        APPLICATION_FIELD_GETTERS.put("businessOwnerId", Application::getBusinessOwnerId);
        APPLICATION_FIELD_GETTERS.put("assigneeId", Application::getAssigneeId);
    }

    public static UpdateApplicationResponseDto createDummyUpdateApplicationResponseDto(long id, String name) {
        return new UpdateApplicationResponseDto(
                id,
                name,
                DUMMY_APPLICATION_DESCRIPTION,
                DUMMY_APPLICATION_REPOSITORY_URL,
                DUMMY_APPLICATION_DOCUMENTATION_URL,
                DUMMY_APPLICATION_BUSINESS_OWNER_ID,
                DUMMY_APPLICATION_ASSIGNEE_ID,
                DUMMY_APPLICATION_CREATED_BY,
                DUMMY_APPLICATION_CREATED_AT,
                DUMMY_APPLICATION_UPDATED_BY,
                DUMMY_APPLICATION_UPDATED_AT
        );
    }

    public static UpdateApplicationResponseDto createDummyUpdateApplicationResponseDto(long id) {
        return new UpdateApplicationResponseDto(
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
                DUMMY_APPLICATION_UPDATED_AT
        );
    }

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

    public static Application createSimpleDummyApplication() {
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

    public static Stream<Arguments> testDataForArgumentsValidation() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(new Application(), null),
                Arguments.of(null, new Application())
        );
    }

    public static Stream<Arguments> testDataForUpdateStringFields() {
        return Stream.concat(
                testDataForStringUpdateOnlyIfNotNull(),
                testDataForStringUpdateOnlyIfHasText());
    }

    private static Stream<Arguments> testDataForStringUpdateOnlyIfHasText() {
        List<String> fieldNames = List.of("documentationUrl", "currentVersion");
        return fieldNames.stream().flatMap(fieldName -> Stream.of(
                Arguments.of(null, OLD_STRING_VALUE, fieldName),
                Arguments.of("", "", fieldName),
                Arguments.of(" ", " ", fieldName),
                Arguments.of(NEW_STRING_VALUE, NEW_STRING_VALUE, fieldName)
        ));
    }

    private static Stream<Arguments> testDataForStringUpdateOnlyIfNotNull() {
        List<String> fieldNames = List.of("name", "description", "repositoryUrl");
        return fieldNames.stream().flatMap(fieldName -> Stream.of(
                Arguments.of(null, OLD_STRING_VALUE, fieldName),
                Arguments.of("", OLD_STRING_VALUE, fieldName),
                Arguments.of(" ", OLD_STRING_VALUE, fieldName),
                Arguments.of(NEW_STRING_VALUE, NEW_STRING_VALUE, fieldName)
        ));
    }

    public static Stream<Arguments> testDataForUpdateIds() {
        List<String> fieldNames = List.of("businessOwnerId", "assigneeId");
        return fieldNames.stream().flatMap(fieldName -> Stream.of(
                Arguments.of(0L, 0L, fieldName),
                Arguments.of(999L, 999L, fieldName),
                Arguments.of(null, DUMMY_COMMON_ID, fieldName)
        ));
    }

    public static Stream<Arguments> archivedTestData() {
        return Stream.of(
                Arguments.of(true, true),
                Arguments.of(false, false),
                Arguments.of(null, DUMMY_APPLICATION_ARCHIVED)
        );
    }

    public static Application createApplicationWithField(String fieldName, String value) {
        Application application = new Application();
        setFieldValue(application, fieldName, value);
        return application;
    }

    public static void setFieldValue(Application application, String fieldName, Object value) {
        BiConsumer<Application, Object> setter = APPLICATION_FIELD_SETTERS.get(fieldName);
        if (setter != null) {
            setter.accept(application, value);
        } else {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
