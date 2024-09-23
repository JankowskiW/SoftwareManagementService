package com.wj.updatecenter.softwaremanagementservice.domain.application.applicationservicetest;

import com.wj.shared.definition.RequestValidationException;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationRepository;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationService;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.application.mapper.ApplicationMapper;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.UpdateApplicationRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.UpdateApplicationResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.*;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.DUMMY_APPLICATION_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class FullyUpdateApplicationTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private ApplicationMapper applicationMapper;
    @Mock
    private ApplicationValidator applicationValidator;
    @InjectMocks
    private ApplicationService applicationService;

    private UpdateApplicationRequestDto updateApplicationRequestDto;

    @BeforeEach
    void setUp() {
        updateApplicationRequestDto = createDummyUpdateApplicationRequestDto();
    }

    @Test
    void shouldReturnUpdateApplicationResponseDtoWhenFullyUpdateApplicationRequestDtoIsValid() {
        // given
        Application mappedApplication = createSimpleDummyApplication();
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
        String exceptionMessage = "some-message-that-should-not-be-changed";
        willThrow(new RequestValidationException(exceptionMessage))
                .given(applicationValidator).validateUpdateRequest(updateApplicationRequestDto, DUMMY_APPLICATION_ID);

        // when && then
        assertThatThrownBy(() -> applicationService.fullyUpdateApplication(updateApplicationRequestDto, DUMMY_APPLICATION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(exceptionMessage);
    }
}
