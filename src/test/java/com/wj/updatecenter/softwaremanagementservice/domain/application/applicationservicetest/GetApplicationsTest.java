package com.wj.updatecenter.softwaremanagementservice.domain.application.applicationservicetest;

import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationRepository;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationService;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationSpecificationBuilder;
import com.wj.updatecenter.softwaremanagementservice.domain.application.mapper.ApplicationMapper;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.GetSimplifiedApplicationResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.*;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.createDummyGetSimplifiedApplicationResponseDtoWithoutCurrentVersion;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class GetApplicationsTest {
    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private ApplicationMapper applicationMapper;
    @Mock
    private ApplicationSpecificationBuilder applicationSpecificationBuilder;
    @InjectMocks
    private ApplicationService applicationService;

    private final Pageable pageable = PageRequest.of(1, 25);
    private List<Application> applications;
    private List<GetSimplifiedApplicationResponseDto> getSimplifiedApplicationResponseDtos;

    @BeforeEach
    void setUp() {
        applications = List.of(
                createDummyApplication(1L),
                createDummyApplication(2L)
        );
        getSimplifiedApplicationResponseDtos = List.of(
                createDummyGetSimplifiedApplicationResponseDtoWithoutCurrentVersion(1L),
                createDummyGetSimplifiedApplicationResponseDtoWithoutCurrentVersion(2L)
        );
    }

    @SuppressWarnings("unchecked") // Intentionally unchecked - Specification is always with type Application
    @Test
    void shouldReturnEmptyPageWhenRepositoryFindAllMethodReturnsEmptyPage() {
        // given
        given(applicationSpecificationBuilder.build(eq(null), eq(null), eq(null), anyBoolean()))
                .willReturn(Specification.where(null));
        given(applicationRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(new PageImpl<Application>(new ArrayList<>()));
        // when
        Page<GetSimplifiedApplicationResponseDto> result = applicationService.getApplications(pageable, null, null, null, false);

        // then
        assertThat(result)
                .isEmpty();
    }

    @Test
    void shouldReturnPageWithOneGetSimplifiedApplicationResponseDtoWhenRepositoryFindAllMethodReturnsOneApplication() {
        // given && when && then
        shouldReturnGetSimplifiedApplicationResponseDtoPageWhenRepositoryFindAllMethodReturnsNApplications(1);
    }

    @Test
    void shouldReturnPageWithMoreThanOneGetSimplifiedApplicationResponseDtosWhenRepositoryFindAllMethodReturnsMoreThanOneApplication() {
        // given && when && then
        shouldReturnGetSimplifiedApplicationResponseDtoPageWhenRepositoryFindAllMethodReturnsNApplications(applications.size());
    }

    @SuppressWarnings("unchecked") // Intentionally unchecked - Specification is always with type Application
    private void shouldReturnGetSimplifiedApplicationResponseDtoPageWhenRepositoryFindAllMethodReturnsNApplications(int n) {
        // given
        given(applicationSpecificationBuilder.build(eq(null), eq(null), eq(null), anyBoolean()))
                .willReturn(Specification.where(null));
        given(applicationRepository.findAll(any(Specification.class), any(Pageable.class)))
                .willReturn(new PageImpl<>(applications.subList(0, n)));
        for (int i = 0; i < n; i++) {
            given(applicationMapper.toGetSimplifiedApplicationResponseDto(eq(applications.get(i))))
                    .willReturn(getSimplifiedApplicationResponseDtos.get(i));
        }

        // when
        Page<GetSimplifiedApplicationResponseDto> result = applicationService.getApplications(pageable, null, null, null, false);

        // then
        assertThat(result)
                .containsExactlyElementsOf(getSimplifiedApplicationResponseDtos.subList(0, n));
    }
}
