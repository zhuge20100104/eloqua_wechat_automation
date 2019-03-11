package com.oracle.testlaunchers;

import com.oracle.auto.testapp.web.testsuites.TestAppWebTestSuite;
import com.oracle.auto.web.utility.PropertyConfiger;

public class AcceptanceTestSuites extends TestAppWebTestSuite {

    @Override
    protected String settingFileList() {
        return PropertyConfiger.instance().getEnvData("test.setting.file", "acceptance_test.setting.properties");
    }

    @Override
    protected String getConsoleUrl() {
        return PropertyConfiger.instance().getEnvData("autoqe.testapp.url", "");
    }
}
