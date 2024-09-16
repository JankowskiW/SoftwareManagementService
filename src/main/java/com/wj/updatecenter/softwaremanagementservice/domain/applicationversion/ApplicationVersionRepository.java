package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion;

import com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model.ApplicationVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationVersionRepository extends JpaRepository<ApplicationVersion, Long> {
    Page<ApplicationVersion> findAllByApplicationId(Pageable pageable, long applicationId);

    boolean existsByApplicationIdAndMajorAndMinorAndPatchAndBuild(
            long applicationId, int major, int minor, int patch, int build);

    Optional<ApplicationVersion> findByApplicationIdAndCurrent(long applicationId, boolean current);

    void deleteByApplicationId(long applicationId);
}
