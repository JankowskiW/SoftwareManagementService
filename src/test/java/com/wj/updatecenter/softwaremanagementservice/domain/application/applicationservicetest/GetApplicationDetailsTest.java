package com.wj.updatecenter.softwaremanagementservice.domain.application.applicationservicetest;

import com.wj.shared.definition.ResourceNotFoundException;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.GetApplicationDetailsDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

public class GetApplicationDetailsTest extends ApplicationServiceTest {

    private Application application;
    private GetApplicationDetailsDto getApplicationDetailsDto;

    @BeforeEach
    void setUp() {
        application = createDummyApplication(DUMMY_APPLICATION_ID);
        getApplicationDetailsDto = createDummyGetApplicationDetailsDtoWithCurrentVersionFields(DUMMY_APPLICATION_ID);
    }


    @Test
    void shouldThrowExceptionWhenApplicationDoesNotExist() {
        // given
        given(applicationRepository.findById(anyLong())).willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> applicationService.getApplicationDetails(DUMMY_APPLICATION_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Application does not exist (id: " + DUMMY_APPLICATION_ID + ")");
    }

    @Test
    void shouldReturnGetApplicationDetailsDtoWhenApplicationExistsAndCurrentVersionExists() {
        // given
        GetApplicationVersionDetailsDto getApplicationVersionDetailsDto =
                createDummyGetApplicationVersionDetailsDto(1L, DUMMY_APPLICATION_ID);
        GetApplicationDetailsDto expectedResult =
                createDummyGetApplicationDetailsDtoWithCurrentVersionFields(DUMMY_APPLICATION_ID);
        given(applicationRepository.findById(DUMMY_APPLICATION_ID))
                .willReturn(Optional.of(application));
        given(applicationVersionService.getCurrentApplicationVersionDetails(DUMMY_APPLICATION_ID))
                .willReturn(Optional.of(getApplicationVersionDetailsDto));
        given(applicationMapper.toGetApplicationDetailsDto(application, getApplicationVersionDetailsDto))
                .willReturn(getApplicationDetailsDto);

        // when
        GetApplicationDetailsDto result = applicationService.getApplicationDetails(DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnGetApplicationDetailsDtoWhenApplicationExistsAndCurrentVersionDoesNotExist() {
        // given
        GetApplicationDetailsDto expectedResult =
                createDummyGetApplicationDetailsDtoWithCurrentVersionFields(DUMMY_APPLICATION_ID);
        given(applicationRepository.findById(DUMMY_APPLICATION_ID))
                .willReturn(Optional.of(application));
        given(applicationVersionService.getCurrentApplicationVersionDetails(DUMMY_APPLICATION_ID))
                .willReturn(Optional.empty());
        given(applicationMapper.toGetApplicationDetailsDto(application, null))
                .willReturn(getApplicationDetailsDto);

        // when
        GetApplicationDetailsDto result = applicationService.getApplicationDetails(DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }
}
