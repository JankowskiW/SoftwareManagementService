package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.mapper;

import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.ApplicationVersion;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionResponseDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetSimplifiedApplicationVersionResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ApplicationVersionMapperTest {
    @InjectMocks
    private ApplicationVersionMapper applicationVersionMapper;

    @Test
    void shouldReturnGetApplicationVersionDetailsDtoWhenApplicationVersionIsNotNull() {
        // given
        ApplicationVersion applicationVersion = createDummyApplicationVersion(DUMMY_VERSION_ID);
        GetApplicationVersionDetailsDto expectedResult = createDummyGetApplicationVersionDetailsDto(DUMMY_VERSION_ID);

        // when
        GetApplicationVersionDetailsDto result = applicationVersionMapper.toGetApplicationVersionDetailsDto(applicationVersion);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnNullInsteadOfGetApplicationVersionDetailsDtoWhenApplicationVersionIsNull() {
        // given && when
        GetApplicationVersionDetailsDto result = applicationVersionMapper.toGetApplicationVersionDetailsDto(null);

        // then
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldReturnGetSimplifiedApplicationVersionResponseDtoWhenApplicationVersionIsNotNull() {
        // given
        ApplicationVersion applicationVersion = createDummyApplicationVersion(DUMMY_VERSION_ID);
        GetSimplifiedApplicationVersionResponseDto expectedResult = createDummyGetSimplifiedApplicationVersionResponseDto(DUMMY_VERSION_ID);

        // when
        GetSimplifiedApplicationVersionResponseDto result = applicationVersionMapper.toGetSimplifiedApplicationVersionResponseDto(applicationVersion);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnNullInsteadOfGetSimplifiedApplicationVersionResponseDtoWhenApplicationVersionIsNull() {
        // given && when
        GetSimplifiedApplicationVersionResponseDto result = applicationVersionMapper.toGetSimplifiedApplicationVersionResponseDto(null);

        // then
        assertThat(result).isNull();
    }

    @Test
    void shouldReturnApplicationVersionWhenCreateApplicationVersionRequestDtoIsNotNull() {
        // given
        CreateApplicationVersionRequestDto createApplicationVersionRequestDto = createDummyCreateApplicationVersionRequestDto();
        ApplicationVersion expectedResult = createSimpleApplicationVersion();

        // when
        ApplicationVersion result = applicationVersionMapper.toApplicationVersion(createApplicationVersionRequestDto, DUMMY_VERSION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnNullInsteadOfApplicationVersionWhenCreateApplicationVersionRequestDtoIsNull() {
        // given && when
        ApplicationVersion result = applicationVersionMapper.toApplicationVersion(null, DUMMY_VERSION_ID);

        // then
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldReturnCreateApplicationVersionResponseDtoWhenApplicationVersionIsNotNull() {
        // given
        ApplicationVersion applicationVersion = createDummyApplicationVersion(DUMMY_VERSION_ID);
        CreateApplicationVersionResponseDto expectedResult =
                createDummyCreateApplicationVersionResponseDto(DUMMY_VERSION_ID);

        // when
        CreateApplicationVersionResponseDto result = applicationVersionMapper.toCreateApplicationVersionResponseDto(applicationVersion);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnNullInsteadOfCreateApplicationVersionResponseDtoWhenApplicationVersionIsNull() {
        // given && when
        CreateApplicationVersionResponseDto result = applicationVersionMapper.toCreateApplicationVersionResponseDto(null);

        // then
        assertThat(result)
                .isNull();
    }
}