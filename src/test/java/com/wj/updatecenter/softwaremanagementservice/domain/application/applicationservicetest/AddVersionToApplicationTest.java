package com.wj.updatecenter.softwaremanagementservice.domain.application.applicationservicetest;

import com.wj.shared.definition.RequestValidationException;
import com.wj.shared.definition.ResourceNotFoundException;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationRepository;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationService;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.CommonApplicationValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.ApplicationVersionService;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.DUMMY_APPLICATION_ID;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.createDummyApplication;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.*;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.DUMMY_VERSION_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AddVersionToApplicationTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private ApplicationVersionService applicationVersionService;
    @InjectMocks
    private ApplicationService applicationService;

    private CreateApplicationVersionRequestDto createApplicationVersionRequestDto;
    private Application application;

    @BeforeEach
    void setUp() {
        createApplicationVersionRequestDto = createDummyCreateApplicationVersionRequestDto();
        application = createDummyApplication(DUMMY_APPLICATION_ID);
    }


    @Test
    void shouldThrowExceptionWhenAddingVersionToNonExistentApplication() {
        // given
        given(applicationRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> applicationService.addVersionToApplication(createApplicationVersionRequestDto, DUMMY_APPLICATION_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContainingAll(
                        CommonApplicationValidator.ENTITY_NAME,
                        CommonApplicationValidator.ID_FIELD_NAME,
                        String.valueOf(DUMMY_APPLICATION_ID),
                        "does not exist");
    }

    @Test
    void shouldThrowExceptionWhenCreateApplicationVersionThrowsRequestValidationException() {
        // given
        String exceptionMessage = "some-message-that-should-not-be-changed";
        given(applicationRepository.findById(anyLong()))
                .willReturn(Optional.of(application));
        given(applicationVersionService.createApplicationVersion(any(CreateApplicationVersionRequestDto.class), anyLong()))
                .willThrow(new RequestValidationException(exceptionMessage));

        // when && then
        assertThatThrownBy(() -> applicationService.addVersionToApplication(createApplicationVersionRequestDto, DUMMY_APPLICATION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void shouldReturnCreateVersionResponseWhenApplicationExistsAndVersionIsSuccessfullyAdded() {
        // given
        CreateApplicationVersionResponseDto createApplicationVersionResponseDto =
                createDummyCreateApplicationVersionResponseDto(DUMMY_VERSION_ID);
        CreateApplicationVersionResponseDto expectedResult =
                createDummyCreateApplicationVersionResponseDto(DUMMY_VERSION_ID);
        given(applicationRepository.findById(anyLong()))
                .willReturn(Optional.of(application));
        given(applicationVersionService.createApplicationVersion(any(CreateApplicationVersionRequestDto.class), anyLong()))
                .willReturn(createApplicationVersionResponseDto);
        given(applicationRepository.save(any(Application.class)))
                .willReturn(application);

        // when
        CreateApplicationVersionResponseDto result =
                applicationService.addVersionToApplication(createApplicationVersionRequestDto, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldChangeCurrentVersionInApplicationEntityWhenApplicationExistsAndVersionIsSuccessfullyAdded() {
        // given
        CreateApplicationVersionResponseDto createApplicationVersionResponseDto =
                createDummyCreateApplicationVersionResponseDto(DUMMY_VERSION_ID);
        application.setCurrentVersion(createApplicationVersionResponseDto.getFullVersion());
        given(applicationRepository.findById(anyLong()))
                .willReturn(Optional.of(application));
        given(applicationVersionService.createApplicationVersion(any(CreateApplicationVersionRequestDto.class), anyLong()))
                .willReturn(createApplicationVersionResponseDto);

        // when
        applicationService.addVersionToApplication(
                createApplicationVersionRequestDto, DUMMY_APPLICATION_ID);

        // then
        verify(applicationRepository).save(application);
    }
}
