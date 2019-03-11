package com.oracle.auto.web.exceptions;

import com.oracle.auto.commons.exceptions.snapshots.SnapshotTaken;
import com.oracle.auto.web.multithread.SeleniumPageFactory;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.ScreenshotException;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class WebDriverExceptionEx extends WebDriverException implements SnapshotTaken {
    private List<String> snapshot = new ArrayList<>();
    private static Logger log = Logger.getLogger(WebDriverExceptionEx.class);

    public WebDriverExceptionEx(Throwable cause) {
        super(cause);

        ScreenshotException se = (ScreenshotException) findWebExceptionFromRootCause(cause, ScreenshotException.class);

        if (se != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
                String time = sdf.format(new Date());
                String screenshotFilePath = SeleniumPageFactory.instance().getScreeshotLocation() + File.separator + "screenshort_" + time + ".png";

                log.info("try to to save screenshot at: " + screenshotFilePath);
                FileOutputStream out = new FileOutputStream(new File(screenshotFilePath));
                out.write(new Base64Encoder().decode(se.getBase64EncodedScreenshot()));
                out.close();

                snapshot.add(screenshotFilePath);
            } catch (Exception ex) {
                log.error("fail to save screenshot");
            }
        }
    }

    private static Throwable findWebExceptionFromRootCause(Throwable ex, Class<?> exClass) {
        do {
            if (ex == null) return null;
            if (exClass.isInstance(ex)) return ex;
            ex = ex.getCause();
        } while (true);
    }

    @Override
    public List<String> getSnapshots() {
        return snapshot;
    }
}
