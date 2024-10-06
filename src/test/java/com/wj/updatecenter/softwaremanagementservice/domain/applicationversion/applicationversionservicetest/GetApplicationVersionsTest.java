package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.applicationversionservicetest;

import com.wj.shared.definition.RequestValidationException;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.ApplicationVersion;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetSimplifiedApplicationVersionResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.DUMMY_APPLICATION_ID;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class GetApplicationVersionsTest extends ApplicationVersionServiceTest {


    @Test
    void shouldReturnGetSimplifiedApplicationVersionResponseDtosWhenRepositoryFindAllByApplicationIdReturnsMoreThanOneVersion() {
        // given
        List<ApplicationVersion> applicationVersions = List.of(
                createDummyApplicationVersion(1L),
                createDummyApplicationVersion(2L)
        );
        List<GetSimplifiedApplicationVersionResponseDto> getSimplifiedApplicationVersionResponseDtos = List.of(
                createDummyGetSimplifiedApplicationVersionResponseDto(1L),
                createDummyGetSimplifiedApplicationVersionResponseDto(2L)
        );
        doNothing().when(commonApplicationValidator).validateIfExistsById(anyLong());
        given(applicationVersionRepository.findAllByApplicationId(any(Pageable.class), anyLong()))
                .willReturn(new PageImpl<>(applicationVersions));
        given(applicationVersionMapper.toGetSimplifiedApplicationVersionResponseDto(applicationVersions.get(0)))
                .willReturn(getSimplifiedApplicationVersionResponseDtos.get(0));
        given(applicationVersionMapper.toGetSimplifiedApplicationVersionResponseDto(applicationVersions.get(1)))
                .willReturn(getSimplifiedApplicationVersionResponseDtos.get(1));

        // when
        Page<GetSimplifiedApplicationVersionResponseDto> result =
                applicationVersionService.getApplicationVersions(pageable, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .containsExactlyInAnyOrder(getSimplifiedApplicationVersionResponseDtos.get(0), getSimplifiedApplicationVersionResponseDtos.get(1));
    }

    @Test
    void shouldReturnOneGetSimplifiedApplicationVersionResponseDtosWhenRepositoryFindAllByApplicationIdReturnsOneVersion() {
        // given
        GetSimplifiedApplicationVersionResponseDto getSimplifiedApplicationVersionResponseDto =
                createDummyGetSimplifiedApplicationVersionResponseDto(DUMMY_VERSION_ID);
        doNothing().when(commonApplicationValidator).validateIfExistsById(anyLong());
        given(applicationVersionRepository.findAllByApplicationId(any(Pageable.class), anyLong()))
                .willReturn(new PageImpl<>(List.of(createDummyApplicationVersion(DUMMY_VERSION_ID))));
        given(applicationVersionMapper.toGetSimplifiedApplicationVersionResponseDto(any(ApplicationVersion.class)))
                .willReturn(getSimplifiedApplicationVersionResponseDto);

        // when
        Page<GetSimplifiedApplicationVersionResponseDto> result =
                applicationVersionService.getApplicationVersions(pageable, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .containsExactlyElementsOf(List.of(getSimplifiedApplicationVersionResponseDto));
    }

    @Test
    void shouldReturnEmptyPageWhenRepositoryFindAllByApplicationIdReturnsEmptyPage() {
        // given
        doNothing().when(commonApplicationValidator).validateIfExistsById(anyLong());
        given(applicationVersionRepository.findAllByApplicationId(any(Pageable.class), anyLong()))
                .willReturn(new PageImpl<>(List.of()));

        // when
        Page<GetSimplifiedApplicationVersionResponseDto> result =
                applicationVersionService.getApplicationVersions(pageable, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenApplicationWithGivenIdDoesNotExist() {
        // given
        doThrow(new RequestValidationException(DUMMY_EXCEPTION_MESSAGE))
                .when(commonApplicationValidator).validateIfExistsById(anyLong());

        // when && then
        assertThatThrownBy(() ->applicationVersionService.getApplicationVersions(pageable, DUMMY_APPLICATION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(DUMMY_EXCEPTION_MESSAGE);
    }
}
