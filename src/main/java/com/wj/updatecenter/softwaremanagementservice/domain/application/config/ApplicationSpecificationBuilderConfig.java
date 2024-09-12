package com.wj.updatecenter.softwaremanagementservice.domain.application.config;

import com.wj.updatecenter.shared.CommonSpecificationBuilder;
import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationSpecificationBuilderConfig {

    @Bean
    public CommonSpecificationBuilder<Application> applicationSpecBuilder() {
       return new CommonSpecificationBuilder<>();
    }
}
