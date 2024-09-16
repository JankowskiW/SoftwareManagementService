package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion;

import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.GetApplicationVersionDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.wj.updatecenter.shared.LoggerHelper.*;

@RestController
@RequestMapping("/application-versions")
@RequiredArgsConstructor
@Slf4j
public class ApplicationVersionController {
    private final ApplicationVersionService applicationVersionService;

    @GetMapping("/{id}")
    public ResponseEntity<GetApplicationVersionDetailsDto> getApplicationVersionDetails(@PathVariable long id) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "GET", "/application-versions/{id}", "", id);
        GetApplicationVersionDetailsDto getApplicationVersionDetailsDto =
                applicationVersionService.getApplicationVersionDetails(id);
        log.info(GET_REQUEST_LOG_MESSAGE_TEMPLATE,
                "ApplicationVersion", getApplicationVersionDetailsDto.toString());
        return ResponseEntity.ok(getApplicationVersionDetailsDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable long id) {
        log.info(RECEIVED_REQUEST_LOG_MESSAGE_TEMPLATE,
                "DELETE", "/application-versions/{id}", "", "id: " + id);
        applicationVersionService.deleteApplicationVersion(id);
        log.info(DELETE_REQUEST_LOG_MESSAGE_TEMPLATE, "ApplicationVersion", id);
        return ResponseEntity.noContent().build();
    }
}
