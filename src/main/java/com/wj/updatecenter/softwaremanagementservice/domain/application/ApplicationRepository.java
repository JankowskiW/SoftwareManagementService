package com.wj.updatecenter.softwaremanagementservice.domain.application;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByName(String name);

    Page<Application> findAll(Specification<Application> specification, Pageable pageable);
}
