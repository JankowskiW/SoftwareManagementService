package com.wj.updatecenter.softwaremanagementservice.core.config;

import com.wj.updatecenter.shared.PaginationHelper;
import com.wj.updatecenter.shared.SortOrderConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaginationHelperConfig {

    @Bean
    public SortOrderConverter sortOrderConverter() {
        return new SortOrderConverter();
    }

    @Bean
    public PaginationHelper paginationHelper(SortOrderConverter sortOrderConverter) {
        return new PaginationHelper(sortOrderConverter);
    }
}
