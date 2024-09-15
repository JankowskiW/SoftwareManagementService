package com.wj.updatecenter.softwaremanagementservice.domain.application.helper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.CreateApplicationRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.UpdateApplicationRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.helper.CommonApplicationVersionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationValidator {

    private final CommonApplicationValidator commonApplicationValidator;
    private final CommonApplicationVersionValidator commonApplicationVersionValidator;

    public void validateCreateRequest(CreateApplicationRequestDto createApplicationRequestDto) {
        commonApplicationValidator.validateIfNotExistsByName(createApplicationRequestDto.name());
    }

    public void validateUpdateRequest(UpdateApplicationRequestDto updateApplicationRequestDto, long id) {
        commonApplicationValidator.validateIfExistsById(id);
        commonApplicationValidator.validateIfNotArchived(id);
        commonApplicationValidator.validateIfNotExistsByNameWithDifferentId(updateApplicationRequestDto.name(), id);
        commonApplicationVersionValidator.validateIfCurrentVersionsMatches(updateApplicationRequestDto.currentVersion(), id);
    }

    public void validateDeleteRequest(long id) {
        commonApplicationValidator.validateIfExistsById(id);
    }
}
