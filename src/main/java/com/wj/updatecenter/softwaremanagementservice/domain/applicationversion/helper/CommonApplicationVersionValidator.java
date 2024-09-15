package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.helper;

import com.wj.shared.definition.RequestValidationException;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.ApplicationVersionRepository;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.ApplicationVersion;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommonApplicationVersionValidator {
    public static final String ENTITY_NAME = "ApplicationVersion";
    public static final String ID_FIELD_NAME = "id";
    public static final String FULL_VERSION_FIELD_NAME = "fullVersion";

    private final ApplicationVersionRepository applicationVersionRepository;

    public void validateIfDoesNotExistByApplicationIdAndFullVersion(
            CreateApplicationVersionRequestDto createApplicationVersionRequestDto, long applicationId) {
        if (applicationVersionRepository.existsByApplicationIdAndMajorAndMinorAndPatchAndBuild(
                applicationId,
                createApplicationVersionRequestDto.major(),
                createApplicationVersionRequestDto.minor(),
                createApplicationVersionRequestDto.patch(),
                createApplicationVersionRequestDto.build())) {
            String fullVersion = createApplicationVersionRequestDto.major() + "." +
                    createApplicationVersionRequestDto.minor() + "." +
                    createApplicationVersionRequestDto.patch() + "." +
                    createApplicationVersionRequestDto.build();
            throw RequestValidationException.notFound(ENTITY_NAME, FULL_VERSION_FIELD_NAME, fullVersion);
        }
    }

    public void validateIfExistsById(long id) {
        if (!applicationVersionRepository.existsById(id)) {
            throw RequestValidationException.notFound(ENTITY_NAME, ID_FIELD_NAME, id);
        }
    }

    public void validateIfCurrentVersionsMatches(String currentVersion, long applicationId) {
        Optional<ApplicationVersion> currentApplicationVersion = applicationVersionRepository
                .findByApplicationIdAndCurrent(applicationId, true);
        if (currentApplicationVersion.isPresent() &&
                currentApplicationVersion.get().getFullVersion().equals(currentVersion)) {
            return;
        }
        throw new RequestValidationException("Given current version does not match current application version");
    }
}
