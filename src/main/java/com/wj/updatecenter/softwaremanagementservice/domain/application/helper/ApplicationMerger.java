package com.wj.updatecenter.softwaremanagementservice.domain.application.helper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ApplicationMerger {
    public Application merge(Application originalApplication, Application applicationToUpdate) {
        if (originalApplication == null || applicationToUpdate == null) {
            throw new IllegalArgumentException("Applications cannot be null");
        }
        if (StringUtils.hasText(applicationToUpdate.getName())) {
            originalApplication.setName(applicationToUpdate.getName());
        }
        if (StringUtils.hasText(applicationToUpdate.getDescription())) {
            originalApplication.setDescription(applicationToUpdate.getDescription());
        }
        if (StringUtils.hasText(applicationToUpdate.getRepositoryUrl())) {
            originalApplication.setRepositoryUrl(applicationToUpdate.getRepositoryUrl());
        }
        if (applicationToUpdate.getDocumentationUrl() != null) {
            // TODO: If documentation url is empty it means that we do not have documentation
            // and that is why we check if it is null and not if it has text
            originalApplication.setDocumentationUrl(applicationToUpdate.getDocumentationUrl());
        }
        if (applicationToUpdate.getBusinessOwnerId() != null) {
            // TODO: If businessOwnerId is 0 it means that there should be no assigned business owner
            originalApplication.setBusinessOwnerId(applicationToUpdate.getBusinessOwnerId());
        }
        if (applicationToUpdate.getAssigneeId() != null) {
            // TODO: Same as businessOwnerId
            originalApplication.setAssigneeId(applicationToUpdate.getAssigneeId());
        }
        if (applicationToUpdate.getCurrentVersion() != null) {
            // TODO: If currentVersion is empty it means that this application has no current version yet
            originalApplication.setCurrentVersion(applicationToUpdate.getCurrentVersion());
        }
        if (applicationToUpdate.getArchived() != null) {
            // TODO: You can set archived as true or false (it means that you can bring back application to life)
            originalApplication.setArchived(applicationToUpdate.getArchived());
        }
        return originalApplication;
    }
}
