package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.helper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.CommonApplicationValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationVersionValidator {
    private final CommonApplicationVersionValidator commonApplicationVersionValidator;
    private final CommonApplicationValidator commonApplicationValidator;

    public void validateCreateRequest(CreateApplicationVersionRequestDto createApplicationVersionRequestDto,
                                      long applicationId) {
        commonApplicationValidator.validateIfExistsById(applicationId);
        commonApplicationVersionValidator.validateIfDoesNotExistByApplicationIdAndFullVersion(
                createApplicationVersionRequestDto, applicationId);
    }

    public void validateDeleteRequest(long id) {
        commonApplicationVersionValidator.validateIfExistsById(id);
    }
}
