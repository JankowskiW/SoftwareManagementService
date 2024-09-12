package com.wj.updatecenter.softwaremanagementservice.domain.application;

import com.wj.updatecenter.softwaremanagementservice.core.helper.SoftwareManagementPaginationHelper;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.*;
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
            @RequestParam(required = false) long productOwnerId,
            @RequestParam(required = false) long assigneeId,
            @RequestParam(defaultValue = "false") boolean archived,
            @RequestParam(defaultValue = DEFAULT_SORT) String sort,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "GET", "/applications", "", "");
        log.info("With filter parameters: name: {}, productOwnerId: {}, assigneeId: {}, archived: {}",
                name, productOwnerId, assigneeId, archived);
        log.info("With pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        log.info("With sort: {}", sort);
        Pageable pageable = softwareManagementPaginationHelper.convertToPageable(pageNumber, pageSize, sort);
        Page<GetSimplifiedApplicationResponseDto> getSimplifiedApplicationResponseDtos =
                applicationService.getApplications(pageable, name, productOwnerId, assigneeId, archived);
        log.info(GET_REQUEST_LOG_MESSAGE_TEMPLATE,
                "Applications page", getSimplifiedApplicationResponseDtos.toString());
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
    public ResponseEntity<UpdateApplicationResponseDto> updateApplication(
            @RequestBody UpdateApplicationRequestDto updateApplicationRequestDto) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "PUT", "/applications/{id}", updateApplicationRequestDto.toString(), "");
        UpdateApplicationResponseDto updateApplicationResponseDto
                = applicationService.updateApplication(updateApplicationRequestDto);
        log.info(PUT_REQUEST_LOG_MESSAGE_TEMPLATE,
                "Application", updateApplicationResponseDto.toString());
        return ResponseEntity.ok(updateApplicationResponseDto);
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<ArchiveApplicationResponseDto> archiveApplication(@PathVariable long id) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "PATCH", "/applications/{id}/archive", "", "id: " + id);
        ArchiveApplicationResponseDto archiveApplicationResponseDto
                = applicationService.archiveApplication(id);
        log.info(PATCH_REQUEST_LOG_MESSAGE_TEMPLATE,
                "Application", "id: " + id);
        return ResponseEntity.ok(archiveApplicationResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable long id) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "DELETE", "/applications/{id}", "", "id: " + id);
        applicationService.deleteApplication(id);
        log.info(DELETE_REQUEST_LOG_MESSAGE_TEMPLATE, "Application", id);
        return ResponseEntity.noContent().build();
    }
}
