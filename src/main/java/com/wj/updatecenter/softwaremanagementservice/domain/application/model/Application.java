package com.wj.updatecenter.softwaremanagementservice.domain.application.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "applications")
@Getter
@Setter
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String repositoryUrl;
    private String documentationUrl;
    @Column(nullable = false)
    private Long businessOwnerId;
    @Column(nullable = false)
    private Long assigneeId;
    private String currentVersion;
    @Column(nullable = false)
    @CreatedBy
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
    private Boolean archived;
    private Long archivedBy;
    private LocalDateTime archivedAt;

    @PreUpdate
    public void preArchived() {
        if (archived) {
            archivedAt = updatedAt;
            archivedBy = updatedBy;
        }
    }
}
