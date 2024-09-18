package com.wj.updatecenter.softwaremanagementservice.domain.application;

import com.wj.shared.definition.RequestValidationException;
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
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetSimplifiedApplicationVersionResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.*;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private ApplicationMapper applicationMapper;
    @Mock
    private ApplicationSpecificationBuilder applicationSpecificationBuilder;
    @Mock
    private ApplicationValidator applicationValidator;
    @Mock
    private ApplicationMerger applicationMerger;
    @Mock
    private ApplicationVersionService applicationVersionService;
    @InjectMocks
    private ApplicationService applicationService;

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
        Application application = createDummyApplication(DUMMY_APPLICATION_ID);
        GetApplicationVersionDetailsDto getApplicationVersionDetailsDto =
                createDummyGetApplicationVersionDetailsDto(1L, DUMMY_APPLICATION_ID);
        GetApplicationDetailsDto getApplicationDetailsDto =
                createDummyGetApplicationDetailsDtoWithCurrentVersionFields(DUMMY_APPLICATION_ID);
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
        Application application = createDummyApplication(DUMMY_APPLICATION_ID);
        GetApplicationDetailsDto getApplicationDetailsDto =
                createDummyGetApplicationDetailsDtoWithCurrentVersionFields(DUMMY_APPLICATION_ID);
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

    @SuppressWarnings("unchecked") // Intentionally unchecked - Specification is always with type Application
    @Test
    void shouldReturnEmptyPageWhenRepositoryFindAllMethodReturnsEmptyPage() {
        // given
        Pageable pageable = PageRequest.of(1,1);
        given(applicationSpecificationBuilder.build(eq(null), eq(null), eq(null), anyBoolean()))
                .willReturn(Specification.where(null));
        given(applicationRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(new PageImpl<Application>(new ArrayList<>()));
        // when
        Page<GetSimplifiedApplicationResponseDto> result = applicationService.getApplications(pageable, null, null, null, false);

        // then
        assertThat(result)
                .isEmpty();
    }

    @SuppressWarnings("unchecked") // Intentionally unchecked - Specification is always with type Application
    @Test
    void shouldReturnOneGetSimplifiedApplicationResponseDtoWhenRepositoryFindAllMethodReturnsOneApplication() {
        // given
        Pageable pageable = PageRequest.of(1,1);
        Application application = createDummyApplication(DUMMY_APPLICATION_ID);
        GetSimplifiedApplicationResponseDto getSimplifiedApplicationResponseDto =
                createDummyGetSimplifiedApplicationResponseDtoWithoutCurrentVersion(DUMMY_APPLICATION_ID);
        given(applicationSpecificationBuilder.build(eq(null), eq(null), eq(null), anyBoolean()))
                .willReturn(Specification.where(null));
        given(applicationRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(application)));
        given(applicationMapper.toGetSimplifiedApplicationResponseDto(any(Application.class)))
                .willReturn(getSimplifiedApplicationResponseDto);

        // when
        Page<GetSimplifiedApplicationResponseDto> result = applicationService.getApplications(pageable, null, null, null, false);

        // then
        assertThat(result)
                .containsExactly(getSimplifiedApplicationResponseDto);
    }

    @SuppressWarnings("unchecked") // Intentionally unchecked - Specification is always with type Application
    @Test
    void shouldReturnGetSimplifiedApplicationResponseDtosWhenRepositoryFindAllMethodReturnsMoreThanOneApplication() {
        // given
        Pageable pageable = PageRequest.of(1,1);
        List<Application> applications = List.of(
                createDummyApplication(1L),
                createDummyApplication(2L)
        );
        List<GetSimplifiedApplicationResponseDto> getSimplifiedApplicationResponseDtos = List.of(
                createDummyGetSimplifiedApplicationResponseDtoWithoutCurrentVersion(1L),
                createDummyGetSimplifiedApplicationResponseDtoWithoutCurrentVersion(2L)
        );
        given(applicationSpecificationBuilder.build(eq(null), eq(null), eq(null), anyBoolean()))
                .willReturn(Specification.where(null));
        given(applicationRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(applications));
        given(applicationMapper.toGetSimplifiedApplicationResponseDto(eq(applications.get(0))))
                .willReturn(getSimplifiedApplicationResponseDtos.get(0));
        given(applicationMapper.toGetSimplifiedApplicationResponseDto(eq(applications.get(1))))
                .willReturn(getSimplifiedApplicationResponseDtos.get(1));

        // when
        Page<GetSimplifiedApplicationResponseDto> result = applicationService.getApplications(pageable, null, null, null, false);

        // then
        assertThat(result)
                .containsExactlyElementsOf(getSimplifiedApplicationResponseDtos);
    }

    @Test
    void shouldReturnCreateApplicationResponseDtoWhenCreateApplicationRequestDtoIsValid() {
        // given
        CreateApplicationRequestDto createApplicationRequestDto = createDummyCreateApplicationRequestDto();
        Application mappedApplication = createSimplyDummyApplication();
        Application savedApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        CreateApplicationResponseDto createApplicationResponseDto =
                createDummyCreateApplicationResponseDto(DUMMY_APPLICATION_ID);
        CreateApplicationResponseDto expectedResult =
                createDummyCreateApplicationResponseDto(DUMMY_APPLICATION_ID);
        doNothing().when(applicationValidator).validateCreateRequest(any(CreateApplicationRequestDto.class));
        given(applicationMapper.toApplication(any(CreateApplicationRequestDto.class)))
                .willReturn(mappedApplication);
        given(applicationRepository.save(any(Application.class)))
                .willReturn(savedApplication);
        given(applicationMapper.toCreateApplicationResponseDto(any(Application.class)))
                .willReturn(createApplicationResponseDto);

        // when
        CreateApplicationResponseDto result = applicationService.createApplication(createApplicationRequestDto);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenCreateApplicationRequestDtoIsInvalid() {
        // given
        String exceptionMessage = "some-message-that-should-not-be-changed";
        CreateApplicationRequestDto createApplicationRequestDto = createDummyCreateApplicationRequestDto();
        willThrow(new RequestValidationException(exceptionMessage))
                .given(applicationValidator).validateCreateRequest(createApplicationRequestDto);

        // when && then
        assertThatThrownBy(() -> applicationService.createApplication(createApplicationRequestDto))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void shouldReturnUpdateApplicationResponseDtoWhenFullyUpdateApplicationRequestDtoIsValid() {
        // given
        UpdateApplicationRequestDto updateApplicationRequestDto = createDummyUpdateApplicationRequestDto();
        Application mappedApplication = createSimplyDummyApplication();
        mappedApplication.setId(DUMMY_APPLICATION_ID);
        Application savedApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        UpdateApplicationResponseDto updateApplicationResponseDto = createDummyUpdateApplicationResponseDto(DUMMY_APPLICATION_ID);
        UpdateApplicationResponseDto expectedResult = createDummyUpdateApplicationResponseDto(DUMMY_APPLICATION_ID);
        doNothing().when(applicationValidator).validateUpdateRequest(any(UpdateApplicationRequestDto.class), anyLong());
        given(applicationMapper.toApplication(any(UpdateApplicationRequestDto.class), anyLong()))
                .willReturn(mappedApplication);
        given(applicationRepository.save(any(Application.class)))
                .willReturn(savedApplication);
        given(applicationMapper.toUpdateApplicationResponseDto(any(Application.class)))
                .willReturn(updateApplicationResponseDto);

        // when
        UpdateApplicationResponseDto result = applicationService
                .fullyUpdateApplication(updateApplicationRequestDto, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenFullyUpdateApplicationRequestDtoIsInvalid() {
        // given
        UpdateApplicationRequestDto updateApplicationRequestDto = createDummyUpdateApplicationRequestDto();
        String exceptionMessage = "some-message-that-should-not-be-changed";
        willThrow(new RequestValidationException(exceptionMessage))
                .given(applicationValidator).validateUpdateRequest(updateApplicationRequestDto, DUMMY_APPLICATION_ID);

        // when && then
        assertThatThrownBy(() -> applicationService.fullyUpdateApplication(updateApplicationRequestDto, DUMMY_APPLICATION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void shouldReturnUpdateApplicationResponseDtoWhenPartiallyUpdateApplicationRequestDtoIsValid() {
        // given
        String newName = "new-name";
        UpdateApplicationRequestDto updateApplicationRequestDto = new UpdateApplicationRequestDto(
                newName, null, null, null, null, null, null, null);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setName(newName);
        applicationToUpdate.setId(DUMMY_APPLICATION_ID);
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application mergedApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        mergedApplication.setName(newName);
        Application savedApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        savedApplication.setId(DUMMY_APPLICATION_ID);
        UpdateApplicationResponseDto updateApplicationResponseDto =
                createDummyUpdateApplicationResponseDto(DUMMY_APPLICATION_ID, newName);
        UpdateApplicationResponseDto expectedResult =
                createDummyUpdateApplicationResponseDto(DUMMY_APPLICATION_ID, newName);
        given(applicationRepository.findById(anyLong()))
                .willReturn(Optional.of(originalApplication));
        doNothing().when(applicationValidator).validateUpdateRequest(any(UpdateApplicationRequestDto.class), anyLong());
        given(applicationMapper.toApplication(any(UpdateApplicationRequestDto.class), anyLong()))
                .willReturn(applicationToUpdate);
        given(applicationMerger.merge(originalApplication, applicationToUpdate))
                .willReturn(mergedApplication);
        given(applicationRepository.save(any(Application.class)))
                .willReturn(savedApplication);
        given(applicationMapper.toUpdateApplicationResponseDto(any(Application.class)))
                .willReturn(updateApplicationResponseDto);

        // when
        UpdateApplicationResponseDto result = applicationService
                .partiallyUpdateApplication(updateApplicationRequestDto, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenApplicationToPartiallyUpdateDoesNotExistById() {
        // given
        UpdateApplicationRequestDto updateApplicationRequestDto = createDummyUpdateApplicationRequestDto();
        given(applicationRepository.findById(anyLong())).willThrow(
                ResourceNotFoundException.notFound(
                        CommonApplicationValidator.ENTITY_NAME,
                        CommonApplicationValidator.ID_FIELD_NAME,
                        DUMMY_APPLICATION_ID)
        );

        // when && then
        assertThatThrownBy(() -> applicationService.partiallyUpdateApplication(updateApplicationRequestDto, DUMMY_APPLICATION_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContainingAll(
                        CommonApplicationValidator.ENTITY_NAME,
                        CommonApplicationValidator.ID_FIELD_NAME,
                        String.valueOf(DUMMY_APPLICATION_ID),
                        "does not exist");
    }

    @Test
    void shouldThrowExceptionWhenPartiallyUpdateApplicationRequestDtoIsInvalid() {
        // given
        UpdateApplicationRequestDto updateApplicationRequestDto = createDummyUpdateApplicationRequestDto();
        String exceptionMessage = "some-message-that-should-not-be-changed";
        given(applicationRepository.findById(anyLong()))
                .willReturn(Optional.of(createDummyApplication(DUMMY_APPLICATION_ID)));
        willThrow(new RequestValidationException(exceptionMessage))
                .given(applicationValidator).validateUpdateRequest(updateApplicationRequestDto, DUMMY_APPLICATION_ID);

        // when && then
        assertThatThrownBy(() -> applicationService.partiallyUpdateApplication(updateApplicationRequestDto, DUMMY_APPLICATION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void shouldThrowExceptionWhenDeleteRequestIsInvalid() {
        // given
        String exceptionMessage = "some-message-that-should-not-be-changed";
        willThrow(new RequestValidationException(exceptionMessage))
                .given(applicationValidator).validateDeleteRequest(DUMMY_APPLICATION_ID);

        // when && then
        assertThatThrownBy(() -> applicationService.deleteApplication(DUMMY_APPLICATION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void shouldCallDeleteApplicationVersionsByApplicationIdAndDeleteByIdMethods() {
        // given
        doNothing().when(applicationValidator).validateDeleteRequest(anyLong());

        // when
        applicationService.deleteApplication(DUMMY_APPLICATION_ID);

        // then
        assertAll(
                () -> verify(applicationVersionService).deleteApplicationVersionsByApplicationId(DUMMY_APPLICATION_ID),
                () -> verify(applicationValidator).validateDeleteRequest(DUMMY_APPLICATION_ID)
        );
    }

    @Test
    void shouldThrowExceptionWhenAddingVersionToNonExistentApplication() {
        // given
        CreateApplicationVersionRequestDto createApplicationVersionRequestDto =
                createDummyCreateApplicationVersionRequestDto();
        given(applicationRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() ->
                applicationService.addVersionToApplication(createApplicationVersionRequestDto, DUMMY_APPLICATION_ID))
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
        CreateApplicationVersionRequestDto createApplicationVersionRequestDto =
                createDummyCreateApplicationVersionRequestDto();
        String exceptionMessage = "some-message-that-should-not-be-changed";
        given(applicationRepository.findById(anyLong()))
                .willReturn(Optional.of(new Application()));
        given(applicationVersionService.createApplicationVersion(any(CreateApplicationVersionRequestDto.class), anyLong()))
            .willThrow(new RequestValidationException(exceptionMessage));

        // when && then
        assertThatThrownBy(() ->
                applicationService.addVersionToApplication(createApplicationVersionRequestDto, DUMMY_APPLICATION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void shouldReturnCreateVersionResponseWhenApplicationExistsAndVersionIsSuccessfullyAdded() {
        // given
        CreateApplicationVersionRequestDto createApplicationVersionRequestDto =
                createDummyCreateApplicationVersionRequestDto();
        CreateApplicationVersionResponseDto createApplicationVersionResponseDto =
                createDummyCreateApplicationVersionResponseDto();
        CreateApplicationVersionResponseDto expectedResult =
                createDummyCreateApplicationVersionResponseDto();
        Application application = createDummyApplication(DUMMY_APPLICATION_ID);
        given(applicationRepository.findById(anyLong()))
                .willReturn(Optional.of(application));
        given(applicationVersionService.createApplicationVersion(any(CreateApplicationVersionRequestDto.class), anyLong()))
                .willReturn(createApplicationVersionResponseDto);
        given(applicationRepository.save(any(Application.class)))
                .willReturn(application);

        // when
        CreateApplicationVersionResponseDto result =
                applicationService.addVersionToApplication(
                        createApplicationVersionRequestDto, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldChangeCurrentVersionInApplicationEntityWhenApplicationExistsAndVersionIsSuccessfullyAdded() {
        // given
        CreateApplicationVersionRequestDto createApplicationVersionRequestDto =
                createDummyCreateApplicationVersionRequestDto();
        CreateApplicationVersionResponseDto createApplicationVersionResponseDto =
                createDummyCreateApplicationVersionResponseDto();
        Application application = createDummyApplication(DUMMY_APPLICATION_ID);
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

    @Test
    void shouldReturnEmptyPageWhenApplicationDoesNotHaveAnyVersions() {
        // given
        Pageable pageable = PageRequest.of(1,1);
        given(applicationVersionService.getApplicationVersions(any(Pageable.class), anyLong()))
                .willReturn(new PageImpl<>(new ArrayList<>()));

        // when
        Page<GetSimplifiedApplicationVersionResponseDto> result =
                applicationService.getApplicationVersions(pageable, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .isEmpty();
    }

    @Test
    void shouldReturnAllApplicationVersionsReturnedByApplicationVersionServiceWhenAnyExists() {
        // given
        Pageable pageable = PageRequest.of(1,1);
        List<GetSimplifiedApplicationVersionResponseDto> versions = List.of(
                createDummyGetSimplifiedApplicationVersionResponseDto(1L),
                createDummyGetSimplifiedApplicationVersionResponseDto(2L)
        );
        given(applicationVersionService.getApplicationVersions(any(Pageable.class), anyLong()))
                .willReturn(new PageImpl<>(versions));

        // when
        Page<GetSimplifiedApplicationVersionResponseDto> result =
                applicationService.getApplicationVersions(pageable, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .containsExactlyElementsOf(versions);
    }
}