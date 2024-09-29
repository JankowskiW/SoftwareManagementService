package com.wj.updatecenter.softwaremanagementservice.domain.application.applicationservicetest;

import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationRepository;
import com.wj.updatecenter.softwaremanagementservice.domain.application.ApplicationService;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationMerger;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationSpecificationBuilder;
import com.wj.updatecenter.softwaremanagementservice.domain.application.helper.ApplicationValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.application.mapper.ApplicationMapper;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.ApplicationVersionService;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.dto.CreateApplicationVersionRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.DUMMY_APPLICATION_ID;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.createDummyApplication;
import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationVersionTestsHelper.createDummyCreateApplicationVersionRequestDto;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceTest {
    @Mock
    ApplicationRepository applicationRepository;
    @Mock
    ApplicationMapper applicationMapper;
    @Mock
    ApplicationSpecificationBuilder applicationSpecificationBuilder;
    @Mock
    ApplicationValidator applicationValidator;
    @Mock
    ApplicationMerger applicationMerger;
    @Mock
    ApplicationVersionService applicationVersionService;
    @InjectMocks
    ApplicationService applicationService;

    CreateApplicationVersionRequestDto createApplicationVersionRequestDto;
    Application application;

    @BeforeEach
    void setUp() {
        createApplicationVersionRequestDto = createDummyCreateApplicationVersionRequestDto();
        application = createDummyApplication(DUMMY_APPLICATION_ID);
    }
}
