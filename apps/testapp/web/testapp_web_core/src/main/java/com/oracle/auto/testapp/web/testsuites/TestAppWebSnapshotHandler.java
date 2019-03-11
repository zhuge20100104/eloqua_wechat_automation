package com.oracle.auto.testapp.web.testsuites;

import com.oracle.auto.commons.exceptions.snapshots.TakeSanpshotHandler;
import org.apache.log4j.Logger;

import java.util.List;

public class TestAppWebSnapshotHandler implements TakeSanpshotHandler {

    private static Logger log = Logger.getLogger(TestAppWebSnapshotHandler.class);

    // singleton
    private static Object object = new Object();
    private static TestAppWebSnapshotHandler instance = null;

    private TestAppWebSnapshotHandler() {
    }

    public static TestAppWebSnapshotHandler instance() {
        if (instance != null) return instance;
        synchronized (object) {
            if (instance == null)
                instance = new TestAppWebSnapshotHandler();
        }
        return instance;
    }

    @Override
    public void onBeforeTakeSnapshot(Object page) {

    }

    @Override
    public void onAfterTakenSnapshot(Object page,
                                     List<String> snapshots) {


    }
}
