package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.applicationversionservicetest;

import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.ApplicationVersion;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.DUMMY_APPLICATION_ID;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.*;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.DUMMY_VERSION_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

public class GetCurrentApplicationVersionDetailsTest extends ApplicationVersionServiceTest {

    @Test
    void shouldReturnEmptyOptionalWhenApplicationDoesNotContainsCurrentVersion() {
        // given
        given(applicationVersionRepository.findByApplicationIdAndCurrent(anyLong(), anyBoolean()))
                .willReturn(Optional.empty());

        // when
        Optional<GetApplicationVersionDetailsDto> result = applicationVersionService
                .getCurrentApplicationVersionDetails(DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .isEmpty();
    }

    @Test
    void shouldReturnOptionalOfGetApplicationVersionDetailsDtoWhenCurrentVersionExists() {
        // given
        Optional<GetApplicationVersionDetailsDto> expectedResult =
                Optional.of(createDummyGetApplicationVersionDetailsDto(DUMMY_VERSION_ID));
        ApplicationVersion currentApplicationVersion = createDummyApplicationVersion(DUMMY_VERSION_ID);
        given(applicationVersionRepository.findByApplicationIdAndCurrent(anyLong(), anyBoolean()))
                .willReturn(Optional.of(currentApplicationVersion));
        given(applicationVersionMapper.toGetApplicationVersionDetailsDto(any(ApplicationVersion.class)))
                .willReturn(createDummyGetApplicationVersionDetailsDto(DUMMY_VERSION_ID));

        // when
        Optional<GetApplicationVersionDetailsDto> result =
                applicationVersionService.getCurrentApplicationVersionDetails(DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }
}
