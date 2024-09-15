package com.wj.updatecenter.softwaremanagementservice.domain.application;

import com.wj.shared.definition.ResourceNotFoundException;
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
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFound(
                        CommonApplicationValidator.ENTITY_NAME, CommonApplicationValidator.ID_FIELD_NAME, id));
        Optional<GetApplicationVersionDetailsDto> getApplicationVersionDetailsDto =
                applicationVersionService.getCurrentApplicationVersionDetails(id);
        return applicationMapper.toGetApplicationDetailsDto(
                application,
                getApplicationVersionDetailsDto.orElse(null));
    }

    public Page<GetSimplifiedApplicationResponseDto> getApplications(
            Pageable pageable, String name, long productOwner, long assigneeId, boolean archived) {
        Specification<Application> specification =
                applicationSpecificationBuilder.build(name, productOwner, assigneeId, archived);
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
        Application originalApplication = applicationRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFound(
                        CommonApplicationValidator.ENTITY_NAME, CommonApplicationValidator.ID_FIELD_NAME, id));
        applicationValidator.validateUpdateRequest(updateApplicationRequestDto, originalApplication.getId());
        Application applicationToUpdate = applicationMapper.toApplication(updateApplicationRequestDto, id);
        Application application = applicationRepository.save(applicationMerger.merge(originalApplication, applicationToUpdate));
        return applicationMapper.toUpdateApplicationResponseDto(application);
    }

    public void deleteApplication(long id) {
        applicationValidator.validateDeleteRequest(id);
        applicationRepository.deleteById(id);
    }

    @Transactional
    public CreateApplicationVersionResponseDto addVersionToApplicationDto(
            CreateApplicationVersionRequestDto createApplicationVersionRequestDto,
            long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFound(
                        CommonApplicationValidator.ENTITY_NAME, CommonApplicationValidator.ID_FIELD_NAME, id));
        CreateApplicationVersionResponseDto createApplicationVersionResponseDto =
                applicationVersionService.createApplicationVersion(createApplicationVersionRequestDto, id);
        application.setCurrentVersion(createApplicationVersionResponseDto.getFullVersion());
        applicationRepository.save(application);
        return createApplicationVersionResponseDto;
    }
}
