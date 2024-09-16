package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "applications_versions")
@Getter
@Setter
public class ApplicationVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private long applicationId;
    @Column(nullable = false)
    private int major;
    @Column(nullable = false)
    private int minor;
    @Column(nullable = false)
    private int patch;
    @Column(nullable = false)
    private int build;
    @Column(nullable = false)
    private boolean current;
    @Column(nullable = false)
    private String changelog;
    @Column(nullable = false)
    @CreatedDate
    private long createdBy;
    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdAt;
    @Column(nullable = false)
    @LastModifiedBy
    private long updatedBy;
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public String getFullVersion() {
        return major + "." + minor + "." + patch + "." + build;
    }
}
