package com.wj.updatecenter.softwaremanagementservice.domain.application;

import com.wj.shared.definition.ResourceNotFoundException;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationSpecificationBuilder;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.application.mapper.ApplicationMapper;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.*;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ApplicationSpecificationBuilder applicationSpecificationBuilder;

    public GetApplicationDetailsDto getApplicationDetails(long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFound(
                        ApplicationValidator.ENTITY_NAME, ApplicationValidator.ID_FIELD_NAME, id));
        GetApplicationVersionDetailsDto getApplicationVersionDetailsDto = null; // TODO: applicationVersionService.getApplicationVersion(currentVersionId);
        return applicationMapper.toGetApplicationDetailsDto(application, getApplicationVersionDetailsDto);
    }

    public Page<GetSimplifiedApplicationResponseDto> getApplications(
            Pageable pageable, String name, long productOwner, long assignedTo, boolean archived) {
        Specification<Application> specification =
                applicationSpecificationBuilder.build(name, productOwner, assignedTo, archived);
        Page<Application> applicationsPage = applicationRepository.findAll(specification, pageable);
        return applicationsPage.map(applicationMapper::toGetSimplifiedApplicationResponseDto);
    }

    public CreateApplicationResponseDto createApplication(CreateApplicationRequestDto createApplicationRequestDto) {
        return null;
    }

    public UpdateApplicationResponseDto updateApplication(UpdateApplicationRequestDto updateApplicationRequestDto) {
        return null;
    }

    public ArchiveApplicationResponseDto archiveApplication(long id) {
        return null;
    }

    public void deleteApplication(long id) {
    }
}
