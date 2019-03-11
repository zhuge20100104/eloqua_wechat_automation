package com.oracle.testlaunchers;

import com.oracle.auto.testapp.web.testsuites.TestAppWebTestSuite;
import com.oracle.auto.web.utility.PropertyConfiger;

import java.util.List;

import static java.util.Arrays.asList;

public class MobileTestSuites extends TestAppWebTestSuite {

    @Override
    protected String settingFileList() {
        return PropertyConfiger.instance().getEnvData("test.setting.file", "*_test.setting.properties");
    }

    @Override
    protected String getConsoleUrl() {
        return PropertyConfiger.instance().getEnvData("autoqe.testapp.url", "");
    }

    @Override
    protected String storyList() {
        return PropertyConfiger.instance().getEnvData("mobile.story.list", "**/*.story");
    }

    @Override
    protected List<String> metaFilters() {
        // use poc test suites by default
        String acceptanceSuites = "groovy: (type ==~ '.*poc.*') ";

        String filter = PropertyConfiger.instance().getEnvData("mobile.meta.filter", acceptanceSuites);
        String all_case = "all";  // magic string
        if (filter.equals(all_case)) filter = "";  //change "ALL_CASE" to "" to take effect
        return asList(filter);
    }
}
