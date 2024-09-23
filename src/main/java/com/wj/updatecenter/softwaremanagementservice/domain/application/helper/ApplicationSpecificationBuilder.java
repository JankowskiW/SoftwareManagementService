package com.wj.updatecenter.softwaremanagementservice.domain.application.helper;

import com.wj.updatecenter.shared.CommonSpecificationBuilder;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationSpecificationBuilder {
    private final CommonSpecificationBuilder<Application> applicationSpecBuilder;

    public Specification<Application> build(String name, Long businessOwnerId, Long assigneeId, boolean archived) {
        Specification<Application> applicationSpecification = Specification.where(null);

        if (name != null) {
            applicationSpecification.and(byName(name));
        }
        if (businessOwnerId != null) {
            applicationSpecification.and(byBusinessOwnerId(businessOwnerId));
        }
        if (assigneeId != null) {
            applicationSpecification.and(byAssigneeId(assigneeId));
        }
        applicationSpecification.and(byArchived(archived));
        return applicationSpecification;
    }

    private Specification<Application> byName(String name) {
        return applicationSpecBuilder.build("name", name);
    }

    private Specification<Application> byBusinessOwnerId(long businessOwnerId) {
        return applicationSpecBuilder.build("businessOwnerId", String.valueOf(businessOwnerId));
    }

    private Specification<Application> byAssigneeId(long assigneeId) {
        return applicationSpecBuilder.build("assigneeId", String.valueOf(assigneeId));
    }

    private Specification<Application> byArchived(boolean archived) {
        return applicationSpecBuilder.build("archived", String.valueOf(archived));
    }
}
