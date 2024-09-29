package com.wj.updatecenter.softwaremanagementservice.domain.applicationversion.applicationversionservicetest;

import org.junit.jupiter.api.Test;

import static com.wj.updatecenter.softwaremanagementservice.testhelper.ApplicationTestsHelper.DUMMY_APPLICATION_ID;
import static org.mockito.Mockito.verify;

public class DeleteByApplicationIdTest extends ApplicationVersionServiceTest  {

    @Test
    void shouldCallDeleteByApplicationId() {
        // given && when
        applicationVersionService.deleteApplicationVersionsByApplicationId(DUMMY_APPLICATION_ID);

        // then
        verify(applicationVersionRepository).deleteByApplicationId(DUMMY_APPLICATION_ID);
    }
}
