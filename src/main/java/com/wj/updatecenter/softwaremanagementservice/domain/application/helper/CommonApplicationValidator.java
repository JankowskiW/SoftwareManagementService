package com.wj.updatecenter.softwaremanagementservice.domain.application.helper;

import com.wj.shared.definition.RequestValidationException;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonApplicationValidator {

    public static final String ENTITY_NAME = "Application";
    public static final String NAME_FIELD_NAME = "name";
    public static final String ID_FIELD_NAME = "id";

    private final ApplicationRepository applicationRepository;


    public void validateIfNotArchived(long id) {
        if (applicationRepository.existsByIdAndArchived(id, true)) {
            String message = String.format("%s is archived (%s: %s)", ENTITY_NAME, ID_FIELD_NAME, id);
            throw new RequestValidationException(message);
        }
    }

    public void validateIfNotExistsByNameWithDifferentId(String name, long id) {
        if (applicationRepository.existsByNameAndIdNot(name, id)) {
            throw RequestValidationException.alreadyExists(
                    ENTITY_NAME,
                    NAME_FIELD_NAME,
                    name);
        }
    }

    public void validateIfNotExistsByName(String name) {
        if (applicationRepository.existsByName(name)) {
            throw RequestValidationException.alreadyExists(
                    ENTITY_NAME,
                    NAME_FIELD_NAME,
                    name);
        }
    }

    public void validateIfExistsById(long id) {
        if (!applicationRepository.existsById(id)) {
            throw RequestValidationException.notFound(
                    ENTITY_NAME,
                    ID_FIELD_NAME,
                    id);
        }
    }
}
