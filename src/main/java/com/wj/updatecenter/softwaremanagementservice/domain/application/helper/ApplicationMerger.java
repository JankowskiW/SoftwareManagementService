package com.wj.updatecenter.softwaremanagementservice.domain.application.helper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.function.Consumer;

@Component
public class ApplicationMerger {
    public Application merge(Application originalApplication, Application applicationToUpdate) {
        validateApplications(originalApplication, applicationToUpdate);

        updateIfHasText(originalApplication::setName, applicationToUpdate.getName());
        updateIfHasText(originalApplication::setDescription, applicationToUpdate.getDescription());
        updateIfHasText(originalApplication::setRepositoryUrl, applicationToUpdate.getRepositoryUrl());

        updateIfNotNull(originalApplication::setDocumentationUrl, applicationToUpdate.getDocumentationUrl());
        updateIfNotNull(originalApplication::setCurrentVersion, applicationToUpdate.getCurrentVersion());

        updateIfNotNull(originalApplication::setBusinessOwnerId, applicationToUpdate.getBusinessOwnerId());
        updateIfNotNull(originalApplication::setAssigneeId, applicationToUpdate.getAssigneeId());

        updateIfNotNull(originalApplication::setArchived, applicationToUpdate.getArchived());

        return originalApplication;
    }

    private void validateApplications(Application originalApplication, Application applicationToUpdate) {
        if (originalApplication == null || applicationToUpdate == null) {
            throw new IllegalArgumentException("Applications cannot be null");
        }
    }

    private void updateIfHasText(Consumer<String> setter, String value) {
        if (StringUtils.hasText(value)) {
            setter.accept(value);
        }
    }

    private <T> void updateIfNotNull(Consumer<T> setter, T value) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
