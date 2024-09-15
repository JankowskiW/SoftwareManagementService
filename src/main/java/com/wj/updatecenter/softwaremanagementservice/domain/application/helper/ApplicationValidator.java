package com.wj.updatecenter.softwaremanagementservice.domain.application.helper;

import com.wj.shared.definition.RequestValidationException;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationRepository;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.CreateApplicationRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.UpdateApplicationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationValidator {

    public static final String ENTITY_NAME = "Application";
    public static final String NAME_FIELD_NAME = "name";
    public static final String ID_FIELD_NAME = "id";

    private final ApplicationRepository applicationRepository;

    public void validateCreateRequest(CreateApplicationRequestDto createApplicationRequestDto) {
        validateIfNotExistsByName(createApplicationRequestDto.name());
    }

    public void validateUpdateRequest(UpdateApplicationRequestDto updateApplicationRequestDto, long id) {
        validateIfExistsById(id);
        validateIfNotArchived(id);
        validateIfNotExistsByNameWithDifferentId(updateApplicationRequestDto.name(), id);
    }

    public void validateDeleteRequest(long id) {
        validateIfExistsById(id);
    }

    private void validateIfNotArchived(long id) {
        if (applicationRepository.existsByIdAndArchived(id, true)) {
            String message = String.format("Application with id %s is archived", id);
            throw new RequestValidationException(message);
        }
    }

    private void validateIfNotExistsByNameWithDifferentId(String name, long id) {
        if (applicationRepository.existsByNameAndIdNot(name, id)) {
            throw RequestValidationException.alreadyExists(
                    ENTITY_NAME,
                    NAME_FIELD_NAME,
                    name);
        }
    }

    private void validateIfNotExistsByName(String name) {
        if (applicationRepository.existsByName(name)) {
            throw RequestValidationException.alreadyExists(
                    ENTITY_NAME,
                    NAME_FIELD_NAME,
                    name);
        }
    }

    private void validateIfExistsById(long id) {
        if (!applicationRepository.existsById(id)) {
            throw RequestValidationException.notFound(
                    ENTITY_NAME,
                    ID_FIELD_NAME,
                    id);
        }
    }
}
