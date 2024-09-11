package com.wj.updatecenter.softwaremanagementservice.domain.application.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    private long businessOwner;
    @Column(nullable = false)
    private long assignedTo;
    @Column(nullable = false)
    private long createdBy;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private long updatedBy;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    private boolean archived;
    private long archivedBy;
    private LocalDateTime archivedAt;
}
