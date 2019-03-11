package com.oracle.auto.web.exceptions;

import com.oracle.auto.commons.exceptions.snapshots.SnapshotTaken;
import com.oracle.auto.commons.exceptions.snapshots.TakeSanpshotHandler;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.utility.HttpUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class WebExceptionBase extends RuntimeException implements SnapshotTaken {
    private static Logger log = Logger.getLogger(WebExceptionBase.class);
    private static TakeSanpshotHandler takenSnapshotHandler = null;

    private static HttpUtils httpUtils = new HttpUtils();

    private List<String> snapshot = new ArrayList<>();
    private String consoleLog;

    public WebExceptionBase(WebDriverSeleniumPageEx page, String error, Throwable ex) {
        super(getMessage(page, error, ex), ex);

        log.warn("exception " + this.getClass().getSimpleName() + " is to be created.");

        if (ex != null && ex instanceof WebExceptionBase)
            snapshot = ((WebExceptionBase) (ex)).getSnapshots();
        else if (ex instanceof WebDriverException) {
            snapshot = new WebDriverExceptionEx(ex).getSnapshots();
        } else
            takeSnapshot(page);

        // dump console log
        consoleLog = page.dumpConsoleLog();
    }

    public WebExceptionBase(WebDriverSeleniumPageEx page, String error) {
        super(getMessage(page, error, null));

        log.warn("exception " + this.getClass().getSimpleName() + " is to be created.");

        takeSnapshot(page);

        // dump console log
        consoleLog = page.dumpConsoleLog();
    }

    private void takeSnapshot(WebDriverSeleniumPageEx page) {
        // try to expend all ExtErrors inside
        try {
            if (takenSnapshotHandler != null)
                takenSnapshotHandler.onBeforeTakeSnapshot(page);

            snapshot = page.saveSceenshot();

            if (takenSnapshotHandler != null)
                takenSnapshotHandler.onAfterTakenSnapshot(page, snapshot);

        } catch (Exception ex) {
            // still got errors during above codes (like expand message might fail due to javaScript exceptions
            if (ex instanceof WebDriverException) {
                snapshot = new WebDriverExceptionEx(ex).getSnapshots();
            }
        }
    }

    public static void setTakenSnapshotHandler(TakeSanpshotHandler hanlder) {
        takenSnapshotHandler = hanlder;
    }

    @Override
    public String getMessage() {
        if (consoleLog.isEmpty())
            return super.getMessage() + "\n\tSnapshort       = " + snapshot;
        else
            return super.getMessage() + "\n\tSnapshort       = " + snapshot + "\n\tBrowser log     = \n" + consoleLog;
    }

    private static String getMessage(WebDriverSeleniumPageEx page, String error, Throwable ex) {
        if (ex != null && ex instanceof WebExceptionBase)
            return "Message =" + error;

        try {
            String location = page.getCurrentUrl();
            return "\n\tCurrent page title = " + page.getTitle()
                    + "\n\tCurrent URL 	   = " + location
                    + "\n\tServer Time 	   = " + httpUtils.getServerTimeFromUrl(location) + "\t(This is approximate server time when error occurred, as server might be handling multiple requests)"
                    + "\n\tMessage         = " + error;
        } catch (Exception ex1) {
            return "";
        }
    }

    @Override
    public List<String> getSnapshots() {
        return snapshot;
    }
}
