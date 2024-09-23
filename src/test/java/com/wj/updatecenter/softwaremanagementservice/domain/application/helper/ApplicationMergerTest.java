package com.wj.updatecenter.softwaremanagementservice.domain.application.helper;

import com.wj.updatecenter.softwaremanagementservice.domain.application.model.Application;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Function;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@ExtendWith(MockitoExtension.class)
class ApplicationMergerTest {
    @InjectMocks
    private ApplicationMerger applicationMerger;

    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#testDataForArgumentsValidation")
    void shouldThrowExceptionWhenOriginalApplicationOrApplicationToUpdateIsNull(Application originalApplication, Application applicationToUpdate) {
        // given && when && then
        assertThatIllegalArgumentException().isThrownBy(() -> applicationMerger.merge(originalApplication, applicationToUpdate));
    }

    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#testDataForUpdateStringFields")
    void shouldUpdateStringFieldCorrectly(String fieldValue, String expectedValue, String fieldName) {
        // given
        Application originalApplication = createApplicationWithField(fieldName, OLD_STRING_VALUE);
        Application applicationToUpdate = new Application();

        setFieldValue(applicationToUpdate, fieldName, fieldValue);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertFieldEquals(result, fieldName, expectedValue);
    }


    @ParameterizedTest
    @MethodSource("com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper#testDataForUpdateIds")
    void shouldUpdateIdFieldCorrectly(Long idValue, Long expectedResult, String fieldName) {
        // given
        Application originalApplication = createDummyApplication(DUMMY_APPLICATION_ID);
        Application applicationToUpdate = new Application();

        setFieldValue(applicationToUpdate, fieldName, idValue);

        // when
        Application result = applicationMerger.merge(originalApplication, applicationToUpdate);

        // then
        assertFieldEquals(result, fieldName, expectedResult);
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


    private void assertFieldEquals(Application result, String fieldName, Object expectedValue) {
        Function<Application, Object> getter = APPLICATION_FIELD_GETTERS.get(fieldName);
        if (getter != null) {
            Object actualValue = getter.apply(result);
            assertThat(actualValue).isEqualTo(expectedValue);
        } else {
            throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}