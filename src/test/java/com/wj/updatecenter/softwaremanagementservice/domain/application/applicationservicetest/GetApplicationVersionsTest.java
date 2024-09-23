package com.wj.updatecenter.softwaremanagementservice.domain.application.applicationservicetest;

import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationService;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.ApplicationVersionService;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetSimplifiedApplicationVersionResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.DUMMY_APPLICATION_ID;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.createDummyGetSimplifiedApplicationVersionResponseDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class GetApplicationVersionsTest {
    @Mock
    private ApplicationVersionService applicationVersionService;
    @InjectMocks
    private ApplicationService applicationService;


    @Test
    void shouldReturnEmptyPageWhenApplicationDoesNotHaveAnyVersions() {
        // given
        Pageable pageable = PageRequest.of(1,1);
        given(applicationVersionService.getApplicationVersions(any(Pageable.class), anyLong()))
                .willReturn(new PageImpl<>(new ArrayList<>()));

        // when
        Page<GetSimplifiedApplicationVersionResponseDto> result =
                applicationService.getApplicationVersions(pageable, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .isEmpty();
    }

    @Test
    void shouldReturnAllApplicationVersionsReturnedByApplicationVersionServiceWhenAnyExists() {
        // given
        Pageable pageable = PageRequest.of(1,1);
        List<GetSimplifiedApplicationVersionResponseDto> versions = List.of(
                createDummyGetSimplifiedApplicationVersionResponseDto(1L),
                createDummyGetSimplifiedApplicationVersionResponseDto(2L)
        );
        given(applicationVersionService.getApplicationVersions(any(Pageable.class), anyLong()))
                .willReturn(new PageImpl<>(versions));

        // when
        Page<GetSimplifiedApplicationVersionResponseDto> result =
                applicationService.getApplicationVersions(pageable, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .containsExactlyElementsOf(versions);
    }
}
