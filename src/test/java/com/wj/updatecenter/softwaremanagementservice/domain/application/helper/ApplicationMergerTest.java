package com.wj.updatecenter.softwaremanagementservice.domain.application.helper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ApplicationMergerTest {
    @InjectMocks
    private ApplicationMerger applicationMerger;

    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#testDataForArgumentsValidation")
    void shouldThrowExceptionWhenOriginalApplicationOrApplicationToUpdateIsNull(Application originalApplication, Application applicationToUpdate) {
        // given
        ThrowableAssert.ThrowingCallable throwingCallable = () -> applicationMerger.merge(originalApplication, applicationToUpdate);

        // when && then
        assertThatIllegalArgumentExceptionWasThrown(throwingCallable);
    }

    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#testDataForStringUpdateOnlyIfHasText")
    void shouldUpdateNameFieldCorrectly(String name, String expectedValue) {
        // given
        Application originalApplication = new Application();
        originalApplication.setName(OLD_STRING_VALUE);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setName(name);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getName())
                .isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#testDataForStringUpdateOnlyIfHasText")
    void shouldUpdateDescriptionFieldCorrectly(String description, String expectedValue) {
        // given
        Application originalApplication = new Application();
        originalApplication.setDescription(OLD_STRING_VALUE);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setDescription(description);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getDescription())
                .isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#testDataForStringUpdateOnlyIfHasText")
    void shouldUpdateRepositoryUrlFieldCorrectly(String repositoryUrl, String expectedValue) {
        // given
        Application originalApplication = new Application();
        originalApplication.setRepositoryUrl(OLD_STRING_VALUE);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setRepositoryUrl(repositoryUrl);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getRepositoryUrl())
                .isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#testDataForStringUpdateOnlyIfNotNull")
    void shouldUpdateDocumentationUrlFieldCorrectly(String documentationUrl, String expectedValue) {
        // given
        Application originalApplication = new Application();
        originalApplication.setDocumentationUrl(OLD_STRING_VALUE);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setDocumentationUrl(documentationUrl);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getDocumentationUrl())
                .isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#testDataForStringUpdateOnlyIfNotNull")
    void shouldUpdateCurrentVersionFieldCorrectly(String currentVersion, String expectedValue) {
        // given
        Application originalApplication = new Application();
        originalApplication.setCurrentVersion(OLD_STRING_VALUE);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setCurrentVersion(currentVersion);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getCurrentVersion())
                .isEqualTo(expectedValue);
    }

    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#idsTestData")
    void shouldUpdateBusinessOwnerIdFieldCorrectly(Long businessOwnerId, Long expectedResult) {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setBusinessOwnerId(businessOwnerId);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getBusinessOwnerId())
                .isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#idsTestData")
    void shouldUpdateAssigneeIdFieldCorrectly(Long assigneeId, Long expectedResult) {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setAssigneeId(assigneeId);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getAssigneeId())
                .isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#archivedTestData")
    void shouldUpdateArchivedFieldCorrectly(Boolean archived, Boolean expectedResult) {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();
        applicationToUpdate.setArchived(archived);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertThat(result.getArchived())
                .isEqualTo(expectedResult);
    }
}