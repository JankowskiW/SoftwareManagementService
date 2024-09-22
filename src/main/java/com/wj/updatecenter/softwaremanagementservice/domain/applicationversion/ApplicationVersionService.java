package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion;

import com.wj.shared.definition.ResourceNotFoundException;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.helper.ApplicationVersionValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.helper.CommonApplicationVersionValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.mapper.ApplicationVersionMapper;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.ApplicationVersion;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionResponseDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetSimplifiedApplicationVersionResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationVersionService {
    private final ApplicationVersionRepository applicationVersionRepository;
    private final ApplicationVersionMapper applicationVersionMapper;
    private final ApplicationVersionValidator applicationVersionValidator;

    public Page<GetSimplifiedApplicationVersionResponseDto> getApplicationVersions(
            Pageable pageable, long applicationId) {
        Page<ApplicationVersion> applicationVersionsPage =
                applicationVersionRepository.findAllByApplicationId(pageable, applicationId);
        return applicationVersionsPage.map(applicationVersionMapper::toGetSimplifiedApplicationVersionResponseDto);
    }

    @Transactional
    public CreateApplicationVersionResponseDto createApplicationVersion(
            CreateApplicationVersionRequestDto createApplicationVersionRequestDto,
            long applicationId) {
        applicationVersionValidator.validateCreateRequest(createApplicationVersionRequestDto, applicationId);
        Optional<ApplicationVersion> currentApplicationVersion =
                applicationVersionRepository.findByApplicationIdAndCurrent(applicationId, true);
        if (currentApplicationVersion.isPresent()) {
            ApplicationVersion applicationVersion = currentApplicationVersion.get();
            applicationVersion.setCurrent(false);
            applicationVersionRepository.save(applicationVersion);
        }
        ApplicationVersion applicationVersion =
                applicationVersionMapper.toApplicationVersion(createApplicationVersionRequestDto, applicationId);
        applicationVersion = applicationVersionRepository.save(applicationVersion);
        return applicationVersionMapper.toCreateApplicationVersionResponseDto(applicationVersion);
    }

    public Optional<GetApplicationVersionDetailsDto> getCurrentApplicationVersionDetails(long applicationId) {
        Optional<ApplicationVersion> applicationVersion =
                applicationVersionRepository.findByApplicationIdAndCurrent(applicationId, true);
        return applicationVersion.map(applicationVersionMapper::toGetApplicationVersionDetailsDto);
    }

    public GetApplicationVersionDetailsDto getApplicationVersionDetails(long id) {
        ApplicationVersion applicationVersion = applicationVersionRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFound(
                        CommonApplicationVersionValidator.ENTITY_NAME,
                        CommonApplicationVersionValidator.ID_FIELD_NAME,
                        id));
        return applicationVersionMapper.toGetApplicationVersionDetailsDto(applicationVersion);
    }

    public void deleteApplicationVersion(long id) {
        applicationVersionValidator.validateDeleteRequest(id);
        applicationVersionRepository.deleteById(id);
    }

    public void deleteApplicationVersionsByApplicationId(long applicationId) {
        applicationVersionRepository.deleteByApplicationId(applicationId);
    }
}
