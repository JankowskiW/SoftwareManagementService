package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.applicationversionservicetest;

import com.wj.shared.definition.RequestValidationException;
import org.junit.jupiter.api.Test;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.DUMMY_EXCEPTION_MESSAGE;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.DUMMY_VERSION_ID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DeleteApplicationVersionTest extends ApplicationVersionServiceTest  {

    @Test
    void shouldThrowExceptionWhenDeleteVersionRequestIsInvalid() {
        // given
        doThrow(new RequestValidationException(DUMMY_EXCEPTION_MESSAGE))
                .when(applicationVersionValidator).validateDeleteRequest(anyLong());

        // when
        assertThatThrownBy(() -> applicationVersionService.deleteApplicationVersion(DUMMY_VERSION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(DUMMY_EXCEPTION_MESSAGE);
    }

    @Test
    void shouldCallDeleteByIdRepositoryMethodWhenDeleteVersionRequestIsValid() {
        // given
        doNothing().when(applicationVersionValidator)
                .validateDeleteRequest(anyLong());

        // when
        applicationVersionService.deleteApplicationVersion(DUMMY_VERSION_ID);

        // then
        verify(applicationVersionRepository).deleteById(DUMMY_VERSION_ID);
    }
}
