package com.wj.updatecenter.softwaremanagementservice.core.auditing;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorProvider implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        // TODO: Change to specific user authenticated in spring security
        return Optional.of(1L);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return Optional.empty();
//        }
//
//        return Optional.of(authenticatedUser);
    }
}
