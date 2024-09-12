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

    public Specification<Application> build(
            String name, long productOwnerId, long assigneeId, boolean archived) {
        Specification<Application> applicationSpecification = Specification.where(null);
        return applicationSpecification
                .and(byName(name))
                .and(byProductOwner(productOwnerId))
                .and(byAssigneeId(assigneeId))
                .and(byArchived(archived));
    }

    private Specification<Application> byName(String name) {
        return applicationSpecBuilder.build("name", name);
    }

    private Specification<Application> byProductOwner(long productOwnerId) {
        return applicationSpecBuilder.build("productOwnerId", String.valueOf(productOwnerId));
    }

    private Specification<Application> byAssigneeId(long assigneeId) {
        return applicationSpecBuilder.build("assigneeId", String.valueOf(assigneeId));
    }

    private Specification<Application> byArchived(boolean archived) {
        return applicationSpecBuilder.build("archived", String.valueOf(archived));
    }
}
