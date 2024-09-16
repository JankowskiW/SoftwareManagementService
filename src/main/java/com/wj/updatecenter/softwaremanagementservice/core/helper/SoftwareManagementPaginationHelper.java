package com.wj.updatecenter.softwaremanagementservice.core.helper;

import com.wj.updatecenter.shared.PaginationHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SoftwareManagementPaginationHelper {
    public static final String DEFAULT_PAGE_NUMBER = "1";
    public static final String DEFAULT_PAGE_SIZE = "25";
    public static final String DEFAULT_SORT = "id,asc";

    private final PaginationHelper paginationHelper;

    static {
        log.info("Default page number = {}", DEFAULT_PAGE_NUMBER);
        log.info("Default page size = {}", DEFAULT_PAGE_SIZE);
        log.info("Default sort = {}", DEFAULT_SORT);
    }

    public Pageable convertToPageable(int pageNumber, int pageSize) {
        return paginationHelper.convertToPageable(pageNumber, pageSize, DEFAULT_SORT);
    }

    public Pageable convertToPageable(int pageNumber, int pageSize, String sort) {
        return paginationHelper.convertToPageable(pageNumber, pageSize, sort);
    }
}
