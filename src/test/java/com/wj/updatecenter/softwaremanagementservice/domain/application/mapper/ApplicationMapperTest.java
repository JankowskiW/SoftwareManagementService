package com.wj.updatecenter.softwaremanagementservice.domain.application.mapper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.*;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ApplicationMapperTest {
    @InjectMocks
    private ApplicationMapper applicationMapper;

    @Test
    void shouldReturnNullInsteadOfGetApplicationDetailsDtoWhenApplicationIsNull() {
        // given && when
        GetApplicationDetailsDto result = applicationMapper.toGetApplicationDetailsDto(null, null);

        // then
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldReturnGetApplicationDetailsDtoWithoutCurrentVersionRelatedFieldsWhenApplicationIsNotNullAndGetApplicationVersionDetailsDtoIsNull() {
        // given
        Application application = createDummyApplication(DUMMY_APPLICATION_ID);
        GetApplicationDetailsDto expectedResult = createDummyGetApplicationDetailsDtoWithoutCurrentVersionFields(DUMMY_APPLICATION_ID);

        // when
        GetApplicationDetailsDto result = applicationMapper.toGetApplicationDetailsDto(application, null);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnGetApplicationDetailsDtoWithCurrentVersionRelatedFieldsWhenApplicationAndGetApplicationVersionDetailsDtoIsNotNull() {
        // given
        Application application = createDummyApplication(DUMMY_APPLICATION_ID);
        GetApplicationVersionDetailsDto getApplicationVersionDetailsDto = createDummyGetApplicationVersionDetailsDto(1L, DUMMY_APPLICATION_ID);
        GetApplicationDetailsDto expectedResult = createDummyGetApplicationDetailsDtoWithCurrentVersionFields(DUMMY_APPLICATION_ID);

        // when
        GetApplicationDetailsDto result = applicationMapper.toGetApplicationDetailsDto(application, getApplicationVersionDetailsDto);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnNullInsteadOfGetSimplifiedApplicationResponseDtoWhenApplicationIsNull() {
        // given && when
        GetSimplifiedApplicationResponseDto result = applicationMapper.toGetSimplifiedApplicationResponseDto(null);

        // then
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldReturnGetSimplifiedApplicationResponseDtoWithoutCurrentVersionWhenCurrentVersionIsNull() {
        // given
        Application application = createDummyApplication(DUMMY_APPLICATION_ID);
        application.setCurrentVersion(null);
        GetSimplifiedApplicationResponseDto expectedResult =
                createDummyGetSimplifiedApplicationResponseDtoWithoutCurrentVersion(DUMMY_APPLICATION_ID);

        // when
        GetSimplifiedApplicationResponseDto result = applicationMapper.toGetSimplifiedApplicationResponseDto(application);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnGetSimplifiedApplicationResponseDtoWithCurrentVersionWhenCurrentVersionIsNotNull() {
        // given
        Application application = createDummyApplication(DUMMY_APPLICATION_ID);
        GetSimplifiedApplicationResponseDto expectedResult =
                createDummyGetSimplifiedApplicationResponseDtoWithCurrentVersion(DUMMY_APPLICATION_ID);

        // when
        GetSimplifiedApplicationResponseDto result = applicationMapper.toGetSimplifiedApplicationResponseDto(application);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnNullInsteadOfApplicationWhenCreateApplicationRequestDtoIsNull() {
        // given && when
        Application result = applicationMapper.toApplication(null);

        // then
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldReturnApplicationWhenCreateApplicationRequestDtoIsNotNull() {
        // given
        CreateApplicationRequestDto createApplicationRequestDto = createDummyCreateApplicationRequestDto();
        Application expectedResult = createSimplyDummyApplication();

        // when
        Application result = applicationMapper.toApplication(createApplicationRequestDto);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnNullInsteadOfCreateApplicationResponseDtoWhenApplicationIsNull() {
        // given && when
        CreateApplicationResponseDto result = applicationMapper.toCreateApplicationResponseDto(null);

        // then
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldReturnCreateApplicationResponseDtoWhenApplicationIsNotNull() {
        // given
        Application application = createDummyApplication(DUMMY_APPLICATION_ID);
        CreateApplicationResponseDto expectedResult = createDummyCreateApplicationResponseDto(DUMMY_APPLICATION_ID);

        // when
        CreateApplicationResponseDto result = applicationMapper.toCreateApplicationResponseDto(application);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnNullInsteadOfApplicationWhenUpdateApplicationRequestDtoIsNull() {
        // given && when
        Application result = applicationMapper.toApplication(null, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldReturnApplicationWhenUpdateApplicationRequestDtoIsNotNull() {
        // given
        UpdateApplicationRequestDto updateApplicationRequestDto = createDummyUpdateApplicationRequestDto();
        Application expectedResult = createSimplyDummyApplication();
        expectedResult.setId(DUMMY_APPLICATION_ID);
        expectedResult.setCurrentVersion(DUMMY_APPLICATION_CURRENT_VERSION);

        // when
        Application result = applicationMapper.toApplication(updateApplicationRequestDto, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnNullInsteadOfUpdateApplicationResponseDtoWhenApplicationIsNull() {
        // given && when
        UpdateApplicationResponseDto result = applicationMapper.toUpdateApplicationResponseDto(null);

        // then
        assertThat(result)
                .isNull();
    }

    @Test
    void shouldReturnUpdateApplicationResponseDtoWhenApplicationIsNotNull() {
        // given
        Application application = createDummyApplication(DUMMY_APPLICATION_ID);
        UpdateApplicationResponseDto expectedResult = createDummyUpdateApplicationResponseDto(DUMMY_APPLICATION_ID);

        // when
        UpdateApplicationResponseDto result = applicationMapper.toUpdateApplicationResponseDto(application);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }
}