package com.wj.updatecenter.softwaremanagementservice.domain.application.applicationservicetest;

import com.wj.shared.definition.RequestValidationException;
import org.junit.jupiter.api.Test;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.DUMMY_APPLICATION_ID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class DeleteApplicationTest extends ApplicationServiceTest {

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
}
