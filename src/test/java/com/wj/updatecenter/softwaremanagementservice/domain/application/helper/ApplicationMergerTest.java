package com.wj.updatecenter.softwaremanagementservice.domain.application.helper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class ApplicationMergerTest {
    @InjectMocks
    private ApplicationMerger applicationMerger;

    @Test
    void shouldThrowExceptionWhenOriginalApplicationIsNull() {
        // given
        Application originalApplication = null;
        Application applicationToUpdate = new Application();

        // when && then
        assertThatThrownBy(() -> applicationMerger.merge(originalApplication, applicationToUpdate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Applications cannot be null");
    }

    @Test
    void shouldThrowExceptionWhenApplicationToUpdateIsNull() {
        // given
        Application originalApplication = new Application();
        Application applicationToUpdate = null;

        // when && then
        assertThatThrownBy(() -> applicationMerger.merge(originalApplication, applicationToUpdate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Applications cannot be null");
    }

    @Test
    void shouldUpdateNameWhenApplicationToUpdateNameHasText() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setName("new-name");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getName())
                .isEqualTo(applicationToUpdate.getName());
    }

    @Test
    void shouldNotUpdateNameWhenApplicationToUpdateNameIsNull() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setName(null);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getName())
                .isEqualTo(DUMMY_APPLICATION_NAME);
    }

    @Test
    void shouldNotUpdateNameWhenApplicationToUpdateNameIsEmpty() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setName("");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getName())
                .isEqualTo(DUMMY_APPLICATION_NAME);
    }

    @Test
    void shouldNotUpdateNameWhenApplicationToUpdateNameIsBlank() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setName("  ");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getName())
                .isEqualTo(DUMMY_APPLICATION_NAME);
    }

    @Test
    void shouldUpdateDescriptionWhenApplicationToUpdateDescriptionHasText() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setDescription("new-description");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getDescription())
                .isEqualTo(applicationToUpdate.getDescription());
    }

    @Test
    void shouldNotUpdateDescriptionWhenApplicationToUpdateDescriptionIsNull() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setDescription(null);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getDescription())
                .isEqualTo(DUMMY_APPLICATION_DESCRIPTION);
    }

    @Test
    void shouldNotUpdateDescriptionWhenApplicationToUpdateDescriptionIsEmpty() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setDescription("");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getDescription())
                .isEqualTo(DUMMY_APPLICATION_DESCRIPTION);
    }

    @Test
    void shouldNotUpdateDescriptionWhenApplicationToUpdateDescriptionIsBlank() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setDescription("  ");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getDescription())
                .isEqualTo(DUMMY_APPLICATION_DESCRIPTION);
    }

    @Test
    void shouldUpdateRepositoryUrlWhenApplicationToUpdateRepositoryUrlHasText() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setRepositoryUrl("new-repository-url");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getRepositoryUrl())
                .isEqualTo(applicationToUpdate.getRepositoryUrl());
    }

    @Test
    void shouldNotUpdateRepositoryUrlWhenApplicationToUpdateRepositoryUrlIsNull() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setRepositoryUrl(null);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getRepositoryUrl())
                .isEqualTo(DUMMY_APPLICATION_REPOSITORY_URL);
    }

    @Test
    void shouldNotUpdateRepositoryUrlWhenApplicationToUpdateRepositoryUrlIsEmpty() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setRepositoryUrl("");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getRepositoryUrl())
                .isEqualTo(DUMMY_APPLICATION_REPOSITORY_URL);
    }

    @Test
    void shouldNotUpdateRepositoryUrlWhenApplicationToUpdateRepositoryUrlIsBlank() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setRepositoryUrl("  ");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getRepositoryUrl())
                .isEqualTo(DUMMY_APPLICATION_REPOSITORY_URL);
    }

    @Test
    void shouldUpdateDocumentationUrlWhenApplicationToUpdateDocumentationUrlIsEmpty() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setDocumentationUrl("");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getDocumentationUrl())
                .isEqualTo(applicationToUpdate.getDocumentationUrl());
    }

    @Test
    void shouldUpdateDocumentationUrlWhenApplicationToUpdateDocumentationUrlIsBlank() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setDocumentationUrl("  ");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getDocumentationUrl())
                .isEqualTo(applicationToUpdate.getDocumentationUrl());
    }

    @Test
    void shouldUpdateDocumentationUrlWhenApplicationToUpdateDocumentationUrlHasText() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setDocumentationUrl("new-documentation-url");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getDocumentationUrl())
                .isEqualTo(applicationToUpdate.getDocumentationUrl());
    }

    @Test
    void shouldNotUpdateDocumentationUrlWhenApplicationToUpdateDocumentationUrlIsNull() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setDocumentationUrl(null);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getDocumentationUrl())
                .isEqualTo(DUMMY_APPLICATION_DOCUMENTATION_URL);
    }

    @Test
    void shouldUpdateBusinessOwnerIdWhenApplicationToUpdateBusinessOwnerIdIs0() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setBusinessOwnerId(0L);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getBusinessOwnerId())
                .isEqualTo(applicationToUpdate.getBusinessOwnerId());
    }

    @Test
    void shouldUpdateBusinessOwnerIdWhenApplicationToUpdateBusinessOwnerIdIsGreaterThan0() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setBusinessOwnerId(999L);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getBusinessOwnerId())
                .isEqualTo(applicationToUpdate.getBusinessOwnerId());
    }

    @Test
    void shouldNotUpdateBusinessOwnerIdWhenApplicationToUpdateBusinessOwnerIdIsNull() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setBusinessOwnerId(null);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getBusinessOwnerId())
                .isEqualTo(DUMMY_APPLICATION_BUSINESS_OWNER_ID);
    }

    @Test
    void shouldUpdateAssigneeIdWhenApplicationToUpdateAssigneeIdIs0() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setAssigneeId(0L);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getAssigneeId())
                .isEqualTo(applicationToUpdate.getAssigneeId());
    }

    @Test
    void shouldUpdateAssigneeIdWhenApplicationToUpdateAssigneeIdIsGreaterThan0() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setAssigneeId(999L);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getAssigneeId())
                .isEqualTo(applicationToUpdate.getAssigneeId());
    }

    @Test
    void shouldNotUpdateAssigneeIdWhenApplicationToUpdateAssigneeIdIsNull() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setAssigneeId(null);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getAssigneeId())
                .isEqualTo(DUMMY_APPLICATION_ASSIGNEE_ID);
    }

    @Test
    void shouldUpdateCurrentVersionWhenApplicationToUpdateCurrentVersionIsEmpty() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setCurrentVersion("");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getCurrentVersion())
            .isEqualTo(applicationToUpdate.getCurrentVersion());
    }

    @Test
    void shouldUpdateCurrentVersionWhenApplicationToUpdateCurrentVersionIsBlank() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setCurrentVersion("  ");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getCurrentVersion())
                .isEqualTo(applicationToUpdate.getCurrentVersion());
    }

    @Test
    void shouldUpdateCurrentVersionWhenApplicationToUpdateCurrentVersionHasText() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setCurrentVersion("1.2.3.4");

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getCurrentVersion())
                .isEqualTo(applicationToUpdate.getCurrentVersion());
    }

    @Test
    void shouldNotUpdateCurrentVersionWhenApplicationToUpdateCurrentVersionIsNull() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setCurrentVersion(null);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getCurrentVersion())
                .isEqualTo(DUMMY_APPLICATION_CURRENT_VERSION);
    }

    @Test
    void shouldUpdateArchivedWhenApplicationToUpdateArchivedIsTrue() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setArchived(true);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getArchived())
                .isEqualTo(applicationToUpdate.getArchived());
    }

    @Test
    void shouldUpdateArchivedWhenApplicationToUpdateArchivedIsFalse() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setArchived(false);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getArchived())
                .isEqualTo(applicationToUpdate.getArchived());
    }

    @Test
    void shouldNotUpdateArchivedWhenApplicationToUpdateArchivedIsNull() {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setArchived(null);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getArchived())
                .isEqualTo(DUMMY_APPLICATION_ARCHIVED);
    }
}