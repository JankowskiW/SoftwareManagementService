package com.wj.updatecenter.softwaremanagementservice.domain.application;

import com.wj.shared.definition.RequestValidationException;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationMerger;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationSpecificationBuilder;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.CommonApplicationValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.application.mapper.ApplicationMapper;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.*;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.ApplicationVersionService;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionResponseDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetSimplifiedApplicationVersionResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ApplicationSpecificationBuilder applicationSpecificationBuilder;
    private final ApplicationValidator applicationValidator;
    private final ApplicationMerger applicationMerger;
    private final ApplicationVersionService applicationVersionService;

    public GetApplicationDetailsDto getApplicationDetails(long id) {
        Application application = findApplicationById(id);
        Optional<GetApplicationVersionDetailsDto> getApplicationVersionDetailsDto =
                applicationVersionService.getCurrentApplicationVersionDetails(id);
        return applicationMapper.toGetApplicationDetailsDto(application, getApplicationVersionDetailsDto.orElse(null));
    }

    public Page<GetSimplifiedApplicationResponseDto> getApplications(
            Pageable pageable, String name, Long businessOwnerId, Long assigneeId, boolean archived) {
        Specification<Application> specification =
                applicationSpecificationBuilder.build(name, businessOwnerId, assigneeId, archived);
        Page<Application> applicationsPage = applicationRepository.findAll(specification, pageable);
        return applicationsPage.map(applicationMapper::toGetSimplifiedApplicationResponseDto);
    }

    public CreateApplicationResponseDto createApplication(CreateApplicationRequestDto createApplicationRequestDto) {
        applicationValidator.validateCreateRequest(createApplicationRequestDto);
        Application application = applicationMapper.toApplication(createApplicationRequestDto);
        application = applicationRepository.save(application);
        return applicationMapper.toCreateApplicationResponseDto(application);
    }

    public UpdateApplicationResponseDto fullyUpdateApplication(
            UpdateApplicationRequestDto updateApplicationRequestDto, long id) {
        applicationValidator.validateUpdateRequest(updateApplicationRequestDto, id);
        Application application = applicationMapper.toApplication(updateApplicationRequestDto, id);
        application = applicationRepository.save(application);
        return applicationMapper.toUpdateApplicationResponseDto(application);
    }

    public UpdateApplicationResponseDto partiallyUpdateApplication(
            UpdateApplicationRequestDto updateApplicationRequestDto, long id) {
        Application originalApplication = findApplicationById(id);
        applicationValidator.validateUpdateRequest(updateApplicationRequestDto, originalApplication.getId());
        Application applicationToUpdate = applicationMapper.toApplication(updateApplicationRequestDto, id);
        Application mergedApplication = applicationMerger.merge(originalApplication, applicationToUpdate);
        Application application = applicationRepository.save(mergedApplication);
        return applicationMapper.toUpdateApplicationResponseDto(application);
    }

    @Transactional
    public void deleteApplication(long id) {
        applicationValidator.validateDeleteRequest(id);
        applicationVersionService.deleteApplicationVersionsByApplicationId(id);
        applicationRepository.deleteById(id);
    }

    @Transactional
    public CreateApplicationVersionResponseDto addVersionToApplication(
            CreateApplicationVersionRequestDto createApplicationVersionRequestDto, long id) {
        Application application = findApplicationById(id);
        CreateApplicationVersionResponseDto createApplicationVersionResponseDto =
                applicationVersionService.createApplicationVersion(createApplicationVersionRequestDto, id);
        application.setCurrentVersion(createApplicationVersionResponseDto.getFullVersion());
        applicationRepository.save(application);
        return createApplicationVersionResponseDto;
    }

    public Page<GetSimplifiedApplicationVersionResponseDto> getApplicationVersions(Pageable pageable, long id) {
        return applicationVersionService.getApplicationVersions(pageable, id);
    }

    private Application findApplicationById(long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> RequestValidationException
                        .notFound(CommonApplicationValidator.ENTITY_NAME,
                                 CommonApplicationValidator.ID_FIELD_NAME, id));
    }
}
