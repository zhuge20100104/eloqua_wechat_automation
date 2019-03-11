package com.oracle.auto.mobile.exceptions;

import com.oracle.auto.commons.exceptions.snapshots.SnapshotTaken;
import com.oracle.auto.commons.exceptions.snapshots.TakeSanpshotHandler;
import com.oracle.auto.mobile.driver.MobileDriverEx;
import com.oracle.auto.web.exceptions.WebDriverExceptionEx;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class MobileExceptionBase extends RuntimeException implements SnapshotTaken {
    private static Logger log = Logger.getLogger(MobileExceptionBase.class);
    private static TakeSanpshotHandler takenSnapshotHandler = null;

    private List<String> snapshot = new ArrayList<>();
    private String appiumConsoleLogs = "";

    public MobileExceptionBase(MobileDriverEx driver, String error, Throwable ex) {
        super(getMessage(driver, error, ex), ex);

        log.warn("exception " + this.getClass().getSimpleName() + " is to be created.");

        if (ex != null && ex instanceof MobileExceptionBase)
            snapshot = ((MobileExceptionBase) (ex)).getSnapshots();
        else if (ex instanceof WebDriverException) {
            snapshot = new WebDriverExceptionEx(ex).getSnapshots();
        } else
            takeSnapshot(driver);

        // Dump console log
        appiumConsoleLogs = driver.getAppiumLogs();
        // Cut off the long log file. I'll improve this later to make it more logical.
        appiumConsoleLogs = appiumConsoleLogs.substring(appiumConsoleLogs.length() - 1000);
    }

    public MobileExceptionBase(MobileDriverEx driver, String error) {
        super(getMessage(driver, error, null));

        log.warn("exception " + this.getClass().getSimpleName() + " is to be created.");

        takeSnapshot(driver);

        // dump console log
        appiumConsoleLogs = driver.getAppiumLogs();
        appiumConsoleLogs = appiumConsoleLogs.substring(appiumConsoleLogs.length() - 1000);
    }

    private void takeSnapshot(MobileDriverEx driver) {
        // try to expend all ExtErrors inside
        try {
            if (takenSnapshotHandler != null)
                takenSnapshotHandler.onBeforeTakeSnapshot(driver);

            snapshot = driver.saveSceenshot();

            if (takenSnapshotHandler != null)
                takenSnapshotHandler.onAfterTakenSnapshot(driver, snapshot);

        } catch (Exception ex) {
            // Still got errors during above codes (like expand message might fail due to javaScript exceptions
            // Will develop a MobileDriverExcetion later.
            if (ex instanceof WebDriverException) {
                snapshot = new WebDriverExceptionEx(ex).getSnapshots();
            }
        }
    }

    public static TakeSanpshotHandler setTakenSnapshotHandler(TakeSanpshotHandler hanlder) {
        TakeSanpshotHandler before = takenSnapshotHandler;
        takenSnapshotHandler = hanlder;

        return before;
    }

    @Override
    public String getMessage() {
        if (appiumConsoleLogs.isEmpty())
            return super.getMessage() + "\n\tSnapshot       = " + snapshot;
        else
            return super.getMessage() + "\n\tSnapshot       = " + snapshot + "\n\tAppium log     = \n" + appiumConsoleLogs;
    }

    private static String getMessage(MobileDriverEx driver, String error, Throwable ex) {
        //String UAT_URL = PropertyConfiger.instance().getEnvData("web.console.url", "");
        if (ex != null && ex instanceof MobileExceptionBase) { return "Message =" + error; }

        try {
            return "\n\tCurrent Screen title = " + driver.getDriver().getTitle() + "\n\tMessage         = " + error;
        } catch (Exception ex1) { return ""; }
    }

    public List<String> getSnapshots() {
        return snapshot;
    }
}