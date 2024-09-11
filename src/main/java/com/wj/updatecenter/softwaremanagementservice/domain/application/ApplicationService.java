package com.wj.updatecenter.softwaremanagementservice.domain.application;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.GetApplicationDetailsDto;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.dto.GetSimplifiedApplicationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public GetApplicationDetailsDto getApplication(long id) {
        return null;
    }

    public Page<GetSimplifiedApplicationResponseDto> getApplications(
            Pageable pageable, String name, String productOwner, String assignedTo, boolean archived) {
        return null;
    }
}
