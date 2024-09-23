package com.wj.updatecenter.softwaremanagementservice.domain.application.applicationservicetest;

import com.wj.shared.definition.RequestValidationException;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationRepository;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationService;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.application.mapper.ApplicationMapper;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.CreateApplicationRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.CreateApplicationResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class CreateApplicationTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private ApplicationMapper applicationMapper;
    @Mock
    private ApplicationValidator applicationValidator;
    @InjectMocks
    private ApplicationService applicationService;

    private CreateApplicationRequestDto createApplicationRequestDto;

    @BeforeEach
    void setUp() {
        createApplicationRequestDto = createDummyCreateApplicationRequestDto();
    }


    @Test
    void shouldReturnCreateApplicationResponseDtoWhenCreateApplicationRequestDtoIsValid() {
        CreateApplicationRequestDto createApplicationRequestDto = createDummyCreateApplicationRequestDto();
        Application mappedApplication = createSimpleDummyApplication();
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
        willThrow(new RequestValidationException(exceptionMessage))
                .given(applicationValidator).validateCreateRequest(createApplicationRequestDto);

        // when && then
        assertThatThrownBy(() -> applicationService.createApplication(createApplicationRequestDto))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(exceptionMessage);
    }

}
