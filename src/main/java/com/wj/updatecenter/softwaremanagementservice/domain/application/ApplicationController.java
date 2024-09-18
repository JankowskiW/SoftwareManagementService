package com.wj.updatecenter.softwaremanagementservice.domain.application;

import com.wj.updatecenter.softwaremanagementservice.core.helper.SoftwareManagementPaginationHelper;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.*;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionResponseDto;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetSimplifiedApplicationVersionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.wj.updatecenter.shared.LoggerHelper.*;
import static com.wj.updatecenter.softwaremanagementservice.core.helper.SoftwareManagementPaginationHelper.*;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {
    private final ApplicationService applicationService;
    private final SoftwareManagementPaginationHelper softwareManagementPaginationHelper;

    @GetMapping("/{id}")
    public ResponseEntity<GetApplicationDetailsDto> getApplicationDetails(@PathVariable long id) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "GET", "/applications/{id}", "", id);
        GetApplicationDetailsDto getApplicationDetailsDto = applicationService.getApplicationDetails(id);
        log.info(GET_REQUEST_LOG_MESSAGE_TEMPLATE,
                "Application", getApplicationDetailsDto.toString());
        return ResponseEntity.ok(getApplicationDetailsDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetSimplifiedApplicationResponseDto>> getApplications(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long businessOwnerId,
            @RequestParam(required = false) Long assigneeId,
            @RequestParam(defaultValue = "false") boolean archived,
            @RequestParam(defaultValue = DEFAULT_SORT) String sort,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE, "GET", "/applications", "", "");
        log.info("With filter parameters: name: {}, businessOwnerId: {}, assigneeId: {}, archived: {}",
                name, businessOwnerId, assigneeId, archived);
        log.info("With pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        log.info("With sort: {}", sort);
        Pageable pageable = softwareManagementPaginationHelper.convertToPageable(pageNumber, pageSize, sort);
        Page<GetSimplifiedApplicationResponseDto> getSimplifiedApplicationResponseDtos =
                applicationService.getApplications(pageable, name, businessOwnerId, assigneeId, archived);
        log.info(GET_REQUEST_LOG_MESSAGE_TEMPLATE, "Applications page", "size: " + getSimplifiedApplicationResponseDtos.getSize());
        return ResponseEntity.ok(getSimplifiedApplicationResponseDtos);
    }

    @PostMapping
    public ResponseEntity<CreateApplicationResponseDto> createApplication(
            @RequestBody CreateApplicationRequestDto createApplicationRequestDto) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "POST", "/applications", createApplicationRequestDto.toString(), "");
        CreateApplicationResponseDto createApplicationResponseDto
                = applicationService.createApplication(createApplicationRequestDto);
        log.info(POST_REQUEST_LOG_MESSAGE_TEMPLATE,
                "Application", createApplicationResponseDto.toString());
        return ResponseEntity.ok(createApplicationResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateApplicationResponseDto> fullyUpdateApplication(
            @PathVariable long id,
            @RequestBody UpdateApplicationRequestDto updateApplicationRequestDto) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "PUT", "/applications/{id}", updateApplicationRequestDto.toString(), "id: " + id);
        UpdateApplicationResponseDto updateApplicationResponseDto
                = applicationService.fullyUpdateApplication(updateApplicationRequestDto, id);
        log.info(PUT_REQUEST_LOG_MESSAGE_TEMPLATE,
                "Application", updateApplicationResponseDto.toString());
        return ResponseEntity.ok(updateApplicationResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UpdateApplicationResponseDto> partiallyUpdateApplication(
            @PathVariable long id,
            @RequestBody UpdateApplicationRequestDto updateApplicationRequestDto) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "PATCH", "/applications/{id}", updateApplicationRequestDto.toString(), "id: " + id);
        UpdateApplicationResponseDto updateApplicationResponseDto
                = applicationService.partiallyUpdateApplication(updateApplicationRequestDto, id);
        log.info(PATCH_REQUEST_LOG_MESSAGE_TEMPLATE,
                "Application", "id: " + id);
        return ResponseEntity.ok(updateApplicationResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable long id) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "DELETE", "/applications/{id}", "", "id: " + id);
        applicationService.deleteApplication(id);
        log.info(DELETE_REQUEST_LOG_MESSAGE_TEMPLATE, "Application", id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/application-versions")
    public ResponseEntity<CreateApplicationVersionResponseDto> addVersionToApplication(
            @PathVariable long id,
            @RequestBody CreateApplicationVersionRequestDto createApplicationVersionRequestDto) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE, "PATCH", "/applications/{id}/application-versions",
                createApplicationVersionRequestDto.toString(), "id: " + id);
        CreateApplicationVersionResponseDto createApplicationVersionResponseDto =
                applicationService.addVersionToApplication(createApplicationVersionRequestDto, id);
        log.info(PATCH_REQUEST_LOG_MESSAGE_TEMPLATE, "Application", "id: " + id);
        return ResponseEntity.ok(createApplicationVersionResponseDto);
    }

    @GetMapping("/{id}/application-versions")
    public ResponseEntity<Page<GetSimplifiedApplicationVersionResponseDto>> getApplicationVersions(
            @PathVariable long id,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE, "GET", "/applications/{id}/application-versions", "", "id: " + id);
        log.info("With pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        Pageable pageable = softwareManagementPaginationHelper.convertToPageable(pageNumber, pageSize);
        Page<GetSimplifiedApplicationVersionResponseDto> getSimplifiedApplicationVersionResponseDtos
                = applicationService.getApplicationVersions(pageable, id);
        log.info(GET_REQUEST_LOG_MESSAGE_TEMPLATE, "ApplicationVersion", "size: " + getSimplifiedApplicationVersionResponseDtos.getSize());
        return ResponseEntity.ok(getSimplifiedApplicationVersionResponseDtos);
    }
}
