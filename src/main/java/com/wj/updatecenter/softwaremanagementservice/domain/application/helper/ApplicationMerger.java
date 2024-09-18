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
            originalApplication.setDocumentationUrl(applicationToUpdate.getDocumentationUrl());
        }
        if (applicationToUpdate.getBusinessOwnerId() != null) {
            originalApplication.setBusinessOwnerId(applicationToUpdate.getBusinessOwnerId());
        }
        if (applicationToUpdate.getAssigneeId() != null) {
            originalApplication.setAssigneeId(applicationToUpdate.getAssigneeId());
        }
        if (applicationToUpdate.getCurrentVersion() != null) {
            originalApplication.setCurrentVersion(applicationToUpdate.getCurrentVersion());
        }
        if (applicationToUpdate.getArchived() != null) {
            originalApplication.setArchived(applicationToUpdate.getArchived());
        }
        return originalApplication;
    }
}
