package com.wj.updatecenter.softwaremanagementservice.domain.application;

import com.wj.updatecenter.softwaremanagementservice.core.helper.SoftwareManagementPaginationHelper;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.GetApplicationDetailsDto;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.GetSimplifiedApplicationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.wj.updatecenter.shared.LoggerHelper.GET_REQUEST_LOG_MESSAGE_TEMPLATE;
import static com.wj.updatecenter.shared.LoggerHelper.RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE;
import static com.wj.updatecenter.softwaremanagementservice.core.helper.SoftwareManagementPaginationHelper.*;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {
    private final ApplicationService applicationService;
    private final SoftwareManagementPaginationHelper softwareManagementPaginationHelper;

    @GetMapping("/{id}")
    public ResponseEntity<GetApplicationDetailsDto> getApplication(@PathVariable long id) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "GET", "/applications/{id}", "", id);
        GetApplicationDetailsDto getApplicationDetailsDto = applicationService.getApplication(id);
        log.info(GET_REQUEST_LOG_MESSAGE_TEMPLATE,
                "Application", getApplicationDetailsDto.toString());
        return ResponseEntity.ok(getApplicationDetailsDto);
    }

    @GetMapping
    public ResponseEntity<Page<GetSimplifiedApplicationResponseDto>> getApplications(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String productOwner,
            @RequestParam(required = false) String assignedTo,
            @RequestParam(defaultValue = "false") boolean archived,
            @RequestParam(defaultValue = DEFAULT_SORT) String sort,
            @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "GET", "/applications", "", "");
        log.info("With filter parameters: name: {}, productOwner: {}, assignedTo: {}, archived: {}",
                name, productOwner, assignedTo, archived);
        log.info("With pageNumber: {}, pageSize: {}", pageNumber, pageSize);
        log.info("With sort: {}", sort);
        Pageable pageable = softwareManagementPaginationHelper.convertToPageable(pageNumber, pageSize, sort);
        Page<GetSimplifiedApplicationResponseDto> getSimplifiedApplicationResponseDtos =
                applicationService.getApplications(pageable, name, productOwner, assignedTo, archived);
        log.info(GET_REQUEST_LOG_MESSAGE_TEMPLATE,
                "Applications page", getSimplifiedApplicationResponseDtos.toString());
        return ResponseEntity.ok(getSimplifiedApplicationResponseDtos);
    }
}
