package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.applicationversionservicetest;

import com.wj.shared.definition.RequestValidationException;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.ApplicationVersion;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionResponseDto;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.DUMMY_APPLICATION_ID;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class CreateApplicationVersionTest extends ApplicationVersionServiceTest {

    @Test
    void shouldThrowExceptionWhenCreateApplicationVersionRequestIsInvalid() {
        // given
        CreateApplicationVersionRequestDto createApplicationVersionRequestDto =
                createDummyCreateApplicationVersionRequestDto();
        willThrow(new RequestValidationException(DUMMY_EXCEPTION_MESSAGE))
                .given(applicationVersionValidator)
                .validateCreateRequest(any(CreateApplicationVersionRequestDto.class), anyLong());

        // when && then
        assertThatThrownBy(() -> applicationVersionService
                .createApplicationVersion(createApplicationVersionRequestDto, DUMMY_APPLICATION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(DUMMY_EXCEPTION_MESSAGE);
    }

    @Test
    void shouldChangeCurrentVersionFlagToFalseWhenCurrentVersionExistsAndCreateApplicationVersionRequestIsValid() {
        // given
        CreateApplicationVersionRequestDto createApplicationVersionRequestDto =
                createDummyCreateApplicationVersionRequestDto();
        ApplicationVersion currentApplicationVersion = createDummyApplicationVersion(DUMMY_VERSION_ID);
        doNothing().when(applicationVersionValidator)
                .validateCreateRequest(any(CreateApplicationVersionRequestDto.class), anyLong());
        given(applicationVersionRepository.findByApplicationIdAndCurrent(anyLong(), anyBoolean()))
                .willReturn(Optional.of(currentApplicationVersion));

        // when
        applicationVersionService.createApplicationVersion(createApplicationVersionRequestDto, DUMMY_APPLICATION_ID);

        // then
        verify(applicationVersionRepository).save(currentApplicationVersion);
    }

    @Test
    void shouldReturnCreateApplicationVersionResponseDtoWhenCreateApplicationVersionRequestIsValid() {
        // given
        CreateApplicationVersionRequestDto createApplicationVersionRequestDto =
                createDummyCreateApplicationVersionRequestDto();
        ApplicationVersion applicationVersion = createSimpleApplicationVersion();
        ApplicationVersion savedApplicationVersion = createDummyApplicationVersion(DUMMY_VERSION_ID);
        CreateApplicationVersionResponseDto createApplicationVersionResponseDto =
                createDummyCreateApplicationVersionResponseDto(DUMMY_VERSION_ID);
        CreateApplicationVersionResponseDto expectedResult =
                createDummyCreateApplicationVersionResponseDto(DUMMY_VERSION_ID);
        doNothing().when(applicationVersionValidator)
                .validateCreateRequest(any(CreateApplicationVersionRequestDto.class), anyLong());
        given(applicationVersionRepository.findByApplicationIdAndCurrent(anyLong(), anyBoolean()))
                .willReturn(Optional.empty());
        given(applicationVersionMapper.toApplicationVersion(any(CreateApplicationVersionRequestDto.class), anyLong()))
                .willReturn(applicationVersion);
        given(applicationVersionRepository.save(any(ApplicationVersion.class))).willReturn(savedApplicationVersion);
        given(applicationVersionMapper.toCreateApplicationVersionResponseDto(any(ApplicationVersion.class)))
                .willReturn(createApplicationVersionResponseDto);

        // when
        CreateApplicationVersionResponseDto result = applicationVersionService.createApplicationVersion(createApplicationVersionRequestDto, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }
}
