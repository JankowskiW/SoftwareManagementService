package com.wj.updatecenter.softwaremanagementservice.domain.application.helper;

import com.wj.shared.definition.RequestValidationException;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ApplicationValidator {

    public static final String ENTITY_NAME = "Application";
    public static final String NAME_FIELD_NAME = "name";
    public static final String ID_FIELD_NAME = "id";

    private final ApplicationRepository applicationRepository;


    public void validate(String name) {
        if (applicationRepository.existsByName(name)) {
            throw RequestValidationException.alreadyExists(
                    ENTITY_NAME,
                    NAME_FIELD_NAME,
                    name);
        }
    }
}
