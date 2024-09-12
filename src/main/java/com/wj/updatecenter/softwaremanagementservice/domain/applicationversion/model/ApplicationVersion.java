package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    private long createdBy;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private long updatedBy;
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public String getFullVersion() {
        return major + "." + minor + "." + patch + "." + build;
    }
}
