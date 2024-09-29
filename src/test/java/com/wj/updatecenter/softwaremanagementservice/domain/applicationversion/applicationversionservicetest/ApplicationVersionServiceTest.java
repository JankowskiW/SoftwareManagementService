package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.applicationversionservicetest;

import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.ApplicationVersionRepository;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.ApplicationVersionService;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.helper.ApplicationVersionValidator;
import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.mapper.ApplicationVersionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class ApplicationVersionServiceTest {
    @Mock
    ApplicationVersionRepository applicationVersionRepository;
    @Mock
    ApplicationVersionMapper applicationVersionMapper;
    @Mock
    ApplicationVersionValidator applicationVersionValidator;
    @InjectMocks
    ApplicationVersionService applicationVersionService;

    Pageable pageable;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(1, 25);
    }
}
