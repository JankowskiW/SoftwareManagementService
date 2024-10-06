package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.applicationversionservicetest;

import com.wj.shared.definition.RequestValidationException;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.helper.CommonApplicationVersionValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.ApplicationVersion;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

public class GetApplicationVersionDetailsTest extends ApplicationVersionServiceTest {

    @Test
    void shouldThrowExceptionWhenApplicationVersionDoesNotExistByGivenId() {
        // given
        String exceptionMessageTemplate = "%s does not exist (%s: %s)";
        given(applicationVersionRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> applicationVersionService.getApplicationVersionDetails(DUMMY_VERSION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(String.format(exceptionMessageTemplate,
                        CommonApplicationVersionValidator.ENTITY_NAME,
                        CommonApplicationVersionValidator.ID_FIELD_NAME,
                        DUMMY_VERSION_ID));
    }

    @Test
    void shouldReturnGetApplicationVersionDetailsDtoWhenVersionExistsByGivenId() {
        // given
        ApplicationVersion currentApplicationVersion = createDummyApplicationVersion(DUMMY_VERSION_ID);
        GetApplicationVersionDetailsDto expectedResult = createDummyGetApplicationVersionDetailsDto(DUMMY_VERSION_ID);
        given(applicationVersionRepository.findById(anyLong()))
                .willReturn(Optional.of(currentApplicationVersion));
        given(applicationVersionMapper.toGetApplicationVersionDetailsDto(any(ApplicationVersion.class)))
                .willReturn(createDummyGetApplicationVersionDetailsDto(DUMMY_VERSION_ID));

        // when
        GetApplicationVersionDetailsDto result = applicationVersionService
                .getApplicationVersionDetails(DUMMY_VERSION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }
}
