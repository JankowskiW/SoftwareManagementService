package com.wj.updatecenter.softwaremanagementservice.testhelper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;

public class ApplicationTestsHelper {

    public static final long DUMMY_APPLICATION_ID = 1L;
    public static final String DUMMY_APPLICATION_NAME = "Dummy Application";
    public static final String DUMMY_APPLICATION_DESCRIPTION = "Dummy Description";
    public static final String DUMMY_APPLICATION_REPOSITORY_URL = "https://github.com/user/repo";
    public static final String DUMMY_APPLICATION_DOCUMENTATION_URL = "ftp://192.168.0.1";
    public static final long DUMMY_APPLICATION_BUSINESS_OWNER_ID = 11L;
    public static final long DUMMY_APPLICATION_ASSIGNEE_ID = 21L;
    public static final String DUMMY_APPLICATION_CURRENT_VERSION = "0.0.0.1";
    public static final long DUMMY_APPLICATION_CREATED_BY = 1L;
    public static final long DUMMY_APPLICATION_UPDATED_BY = 2L;
    public static final long DUMMY_APPLICATION_ARCHIVED_BY = 3L;
    public static final boolean DUMMY_APPLICATION_ARCHIVED = true;

    public static Application createDummyApplication(long id) {
        Application application = new Application();
        application.setId(id);
        application.setName(DUMMY_APPLICATION_NAME);
        application.setDescription(DUMMY_APPLICATION_DESCRIPTION);
        application.setRepositoryUrl(DUMMY_APPLICATION_REPOSITORY_URL);
        application.setDocumentationUrl(DUMMY_APPLICATION_DOCUMENTATION_URL);
        application.setBusinessOwnerId(DUMMY_APPLICATION_BUSINESS_OWNER_ID);
        application.setAssigneeId(DUMMY_APPLICATION_ASSIGNEE_ID);
        application.setCurrentVersion(DUMMY_APPLICATION_CURRENT_VERSION);
        application.setCreatedBy(DUMMY_APPLICATION_CREATED_BY);
        application.setUpdatedBy(DUMMY_APPLICATION_UPDATED_BY);
        application.setArchived(DUMMY_APPLICATION_ARCHIVED);
        application.setArchivedBy(DUMMY_APPLICATION_ARCHIVED_BY);
        return application;
    }
}
