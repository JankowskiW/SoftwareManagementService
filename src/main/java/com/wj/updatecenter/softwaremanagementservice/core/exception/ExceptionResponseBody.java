package com.wj.updatecenter.softwaremanagementservice.core.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ExceptionResponseBody (
        HttpStatus statusCode,
        String message,
        LocalDateTime timestamp
) {}