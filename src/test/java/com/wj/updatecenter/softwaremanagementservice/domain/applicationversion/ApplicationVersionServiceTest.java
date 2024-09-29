package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion;

import com.wj.shared.definition.RequestValidationException;
import com.wj.shared.definition.ResourceNotFoundException;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.helper.ApplicationVersionValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.helper.CommonApplicationVersionValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.mapper.ApplicationVersionMapper;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.ApplicationVersion;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionResponseDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetSimplifiedApplicationVersionResponseDto;
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

import java.util.List;
import java.util.Optional;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.DUMMY_APPLICATION_ID;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationVersionServiceTest {
    @Mock
    private ApplicationVersionRepository applicationVersionRepository;
    @Mock
    private ApplicationVersionMapper applicationVersionMapper;
    @Mock
    private ApplicationVersionValidator applicationVersionValidator;
    @InjectMocks
    private ApplicationVersionService applicationVersionService;

    private final String exceptionMessage = "message-that-should-not-be-changed";
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(1, 25);
    }

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
    void shouldThrowExceptionWhenCreateApplicationVersionRequestIsInvalid() {
        // given
        CreateApplicationVersionRequestDto createApplicationVersionRequestDto =
                createDummyCreateApplicationVersionRequestDto();
        willThrow(new RequestValidationException(exceptionMessage))
                .given(applicationVersionValidator)
                .validateCreateRequest(any(CreateApplicationVersionRequestDto.class), anyLong());

        // when && then
        assertThatThrownBy(() -> applicationVersionService
                .createApplicationVersion(createApplicationVersionRequestDto, DUMMY_APPLICATION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(exceptionMessage);
    }

    @Test
    void shouldChangeCurrentVersionFlagToFalseWhenCurrentVersionExistsAndCreateApplicationVersionRequestIsValid() {
        // given
        CreateApplicationVersionRequestDto createApplicationVersionRequestDto =
                createDummyCreateApplicationVersionRequestDto();
        ApplicationVersion currentApplicationVersion = createDummyApplicationVersion(DUMMY_VERSION_ID);
          doNothing().when(applicationVersionValidator)
                .validateCreateRequest(any(CreateApplicationVersionRequestDto.class), anyLong());
        given(applicationVersionRepository.findByApplicationIdAndCurrent(anyLong(), anyBoolean()))
                .willReturn(Optional.of(currentApplicationVersion));

        // when
        applicationVersionService.createApplicationVersion(createApplicationVersionRequestDto, DUMMY_APPLICATION_ID);

        // then
        verify(applicationVersionRepository).save(currentApplicationVersion);
    }

    @Test
    void shouldReturnCreateApplicationVersionResponseDtoWhenCreateApplicationVersionRequestIsValid() {
        // given
        CreateApplicationVersionRequestDto createApplicationVersionRequestDto =
                createDummyCreateApplicationVersionRequestDto();
        ApplicationVersion applicationVersion = createSimpleApplicationVersion();
        ApplicationVersion savedApplicationVersion = createDummyApplicationVersion(DUMMY_VERSION_ID);
        CreateApplicationVersionResponseDto createApplicationVersionResponseDto =
                createDummyCreateApplicationVersionResponseDto(DUMMY_VERSION_ID);
        CreateApplicationVersionResponseDto expectedResult =
                createDummyCreateApplicationVersionResponseDto(DUMMY_VERSION_ID);
        doNothing().when(applicationVersionValidator)
                .validateCreateRequest(any(CreateApplicationVersionRequestDto.class), anyLong());
        given(applicationVersionRepository.findByApplicationIdAndCurrent(anyLong(), anyBoolean()))
                .willReturn(Optional.empty());
        given(applicationVersionMapper.toApplicationVersion(any(CreateApplicationVersionRequestDto.class), anyLong()))
                .willReturn(applicationVersion);
        given(applicationVersionRepository.save(any(ApplicationVersion.class))).willReturn(savedApplicationVersion);
        given(applicationVersionMapper.toCreateApplicationVersionResponseDto(any(ApplicationVersion.class)))
                .willReturn(createApplicationVersionResponseDto);

        // when
        CreateApplicationVersionResponseDto result = applicationVersionService.createApplicationVersion(createApplicationVersionRequestDto, DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldReturnEmptyOptionalWhenApplicationDoesNotContainsCurrentVersion() {
        // given
        given(applicationVersionRepository.findByApplicationIdAndCurrent(anyLong(), anyBoolean()))
                .willReturn(Optional.empty());

        // when
        Optional<GetApplicationVersionDetailsDto> result = applicationVersionService
                .getCurrentApplicationVersionDetails(DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .isEmpty();
    }

    @Test
    void shouldReturnOptionalOfGetApplicationVersionDetailsDtoWhenCurrentVersionExists() {
        // given
        Optional<GetApplicationVersionDetailsDto> expectedResult =
                Optional.of(createDummyGetApplicationVersionDetailsDto(DUMMY_VERSION_ID));
        ApplicationVersion currentApplicationVersion = createDummyApplicationVersion(DUMMY_VERSION_ID);
        given(applicationVersionRepository.findByApplicationIdAndCurrent(anyLong(), anyBoolean()))
                .willReturn(Optional.of(currentApplicationVersion));
        given(applicationVersionMapper.toGetApplicationVersionDetailsDto(any(ApplicationVersion.class)))
                .willReturn(createDummyGetApplicationVersionDetailsDto(DUMMY_VERSION_ID));

        // when
        Optional<GetApplicationVersionDetailsDto> result =
                applicationVersionService.getCurrentApplicationVersionDetails(DUMMY_APPLICATION_ID);

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expectedResult);
    }

    @Test
    void shouldThrowExceptionWhenApplicationVersionDoesNotExistByGivenId() {
        // given
        String exceptionMessageTemplate = "%s does not exist (%s: %s)";
        given(applicationVersionRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when && then
        assertThatThrownBy(() -> applicationVersionService.getApplicationVersionDetails(DUMMY_VERSION_ID))
                .isInstanceOf(ResourceNotFoundException.class)
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

    @Test
    void shouldThrowExceptionWhenDeleteVersionRequestIsInvalid() {
        // given
        doThrow(new RequestValidationException(exceptionMessage))
                .when(applicationVersionValidator).validateDeleteRequest(anyLong());

        // when
        assertThatThrownBy(() -> applicationVersionService.deleteApplicationVersion(DUMMY_VERSION_ID))
                .isInstanceOf(RequestValidationException.class)
                .hasMessage(exceptionMessage);
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

    @Test
    void shouldCallDeleteByApplicationId() {
        // given && when
        applicationVersionService.deleteApplicationVersionsByApplicationId(DUMMY_APPLICATION_ID);

        // then
        verify(applicationVersionRepository).deleteByApplicationId(DUMMY_APPLICATION_ID);
    }
}