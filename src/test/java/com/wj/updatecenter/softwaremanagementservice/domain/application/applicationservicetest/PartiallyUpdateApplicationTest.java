package com.wj.updatecenter.softwaremanagementservice.domain.application.applicationservicetest;

import com.wj.shared.definition.RequestValidationException;
import com.wj.shared.definition.ResourceNotFoundException;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.CommonApplicationValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.UpdateApplicationRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.UpdateApplicationResponseDto;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.doNothing;

public class PartiallyUpdateApplicationTest extends ApplicationServiceTest {

    @Test
    void shouldReturnUpdateApplicationResponseDtoWhenPartiallyUpdateApplicationRequestDtoIsValid() {
        // given
        String newName = "new-name";
        UpdateApplicationRequestDto updateApplicationRequestDto = new UpdateApplicationRequestDto(
                newName, null, null, null, null, null, null, null);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setName(newName);
        applicationToUpdate.setId(DUMMY_APPLICATION_ID);
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application mergedApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        mergedApplication.setName(newName);
        Application savedApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        savedApplication.setId(DUMMY_APPLICATION_ID);
        UpdateApplicationResponseDto updateApplicationResponseDto =
                createDummyUpdateApplicationResponseDto(DUMMY_APPLICATION_ID, newName);
        UpdateApplicationResponseDto expectedResult =
                createDummyUpdateApplicationResponseDto(DUMMY_APPLICATION_ID, newName);
        given(applicationRepository.findById(anyLong()))
                .willReturn(Optional.of(originalApplication));
        doNothing().when(applicationValidator).validateUpdateRequest(any(UpdateApplicationRequestDto.class), anyLong());
        given(applicationMapper.toApplication(any(UpdateApplicationRequestDto.class), anyLong()))
                .willReturn(applicationToUpdate);
        given(applicationMerger.merge(originalApplication, applicationToUpdate))
                .willReturn(mergedApplication);
        given(applicationRepository.save(any(Application.class)))
                .willReturn(savedApplication);
        given(applicationMapper.toUpdateApplicationResponseDto(any(Application.class)))
                .willReturn(updateApplicationResponseDto);

        // when
        UpdateApplicationResponseDto result = applicationService
                .partiallyUpdateApplication(updateApplicationRequestDto, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenApplicationToPartiallyUpdateDoesNotExistById() {
        // given
        UpdateApplicationRequestDto updateApplicationRequestDto = createDummyUpdateApplicationRequestDto();
        given(applicationRepository.findById(anyLong())).willThrow(
                ResourceNotFoundException.notFound(
                        CommonApplicationValidator.ENTITY_NAME,
                        CommonApplicationValidator.ID_FIELD_NAME,
                        DUMMY_APPLICATION_ID)
        );

        // when && then
        assertThatThrownBy(() -> applicationService.partiallyUpdateApplication(updateApplicationRequestDto, DUMMY_APPLICATION_ID))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContainingAll(
                        CommonApplicationValidator.ENTITY_NAME,
                        CommonApplicationValidator.ID_FIELD_NAME,
                        String.valueOf(DUMMY_APPLICATION_ID),
                        "does not exist");
    }

    @Test
    void shouldThrowExceptionWhenPartiallyUpdateApplicationRequestDtoIsInvalid() {
        // given
        UpdateApplicationRequestDto updateApplicationRequestDto = createDummyUpdateApplicationRequestDto();
        String exceptionMessage = "some-message-that-should-not-be-changed";
        given(applicationRepository.findById(anyLong()))
                .willReturn(Optional.of(createDummyApplication(DUMMY_APPLICATION_ID)));
        willThrow(new RequestValidationException(exceptionMessage))
                .given(applicationValidator).validateUpdateRequest(updateApplicationRequestDto, DUMMY_APPLICATION_ID);

        // when && then
        assertThatThrownBy(() -> applicationService.partiallyUpdateApplication(updateApplicationRequestDto, DUMMY_APPLICATION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(exceptionMessage);
    }
}
