package com.oracle.auto.testapp.web.testsuites;


import com.oracle.auto.apps.suites.AutoQEUiTestSuites;

public class TestAppWebTestSuite extends AutoQEUiTestSuites {

    @Override
    protected TestAppWebSnapshotHandler takenSnapshotHanlder() {
        return TestAppWebSnapshotHandler.instance();
    }
}
