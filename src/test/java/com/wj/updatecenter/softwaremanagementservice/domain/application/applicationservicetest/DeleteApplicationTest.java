package com.wj.updatecenter.softwaremanagementservice.domain.application.applicationservicetest;

import com.wj.shared.definition.RequestValidationException;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationRepository;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationService;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.ApplicationVersionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.DUMMY_APPLICATION_ID;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteApplicationTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private ApplicationValidator applicationValidator;
    @Mock
    private ApplicationVersionService applicationVersionService;
    @InjectMocks
    private ApplicationService applicationService;


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
