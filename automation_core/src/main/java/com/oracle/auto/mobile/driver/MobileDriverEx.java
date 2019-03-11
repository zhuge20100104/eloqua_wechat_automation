package com.oracle.auto.mobile.driver;

import com.oracle.auto.mobile.exceptions.JavascriptExecutionException;
import com.oracle.auto.web.utility.PropertyConfiger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.logging.LogEntry;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class MobileDriverEx {

    private static Logger log = Logger.getLogger(MobileDriverEx.class);
    protected final AppiumDriver driver;
    private String screenshotFolder;

    private AppiumDriverLocalService appiumDriverLocalService;

    public MobileDriverEx(AppiumDriver driver, String screenshotFolder) {
        this.screenshotFolder = screenshotFolder;
        this.driver = driver;
    }

    void setAppiumServer(AppiumDriverLocalService appiumDriverLocalService) {
        this.appiumDriverLocalService = appiumDriverLocalService;
    }

    private AppiumDriverLocalService getAppiumServer() {
        return appiumDriverLocalService;
    }

    void stopAppiumServer() {
        waitFor(2);
        if (getAppiumServer() != null)
            getAppiumServer().stop();
    }

    private static final Map<String, String> usefulJS = new HashMap<String, String>() {{
        put("BDD_jQuery", "if(typeof BDD_jFilter=='undefined'){BDD_jFilter=function(e){var t=[];for(var n=0;n<e.length;++n){if(typeof e[n]!='undefined'&&e[n]!=undefined&&e[n].offsetWidth>1){e[n].jFind=function(e){var t=$(this).find(e);return BDD_jFilter(t)};t.push(e[n])}}return $(t)}}if(typeof BDD_jQuery=='undefined'){BDD_jQuery=function(e){return BDD_jFilter($(e))}}if(typeof BDD_jFindByAttr=='undefined'){BDD_jFindByAttr=function(e,t,n){var r=e+\"[\"+t+\"=\'\"+n+\"\']\";return $(r)}}if(typeof BDD_jFindBySelector=='undefined'){BDD_jFindBySelector=function(e){return $(e)}}if(typeof BDD_GenId=='undefined'){BDD_GenId=function(e){var t=e.id;var n='genkey_';var r=new Date;if(t=='undefined'||t==''||t==null){var i=r.getTime();t=e.setAttribute('id',n+i)}return e.id}}");
    }};

    private String injectExtendedJS(String script) {
        for (String key : usefulJS.keySet()) {
            if (key.isEmpty()) {
                log.info("inject mandantory script :" + key);
                script = usefulJS.get(key) + script;
            } else {
                if (script.contains(key)) {
                    log.info("inject ondemand script :" + key);
                    script = usefulJS.get(key) + script;
                } else {
                    log.info("skip inject ondemand script :" + key);
                }
            }
        }
        return script;
    }

    public String executeScript(String script) {
        script = injectExtendedJS(script);
        try {
            Object returnValue = driver.executeScript(script);
            log.debug("[run javascript] Result=" + (returnValue == null ? "undefined" : returnValue.toString()) + "\t[Script] = " + script);
            if (returnValue == null) return "";
            return returnValue.toString();
        } catch (Exception ex) {
            throw new JavascriptExecutionException(this, script, ex);
        }
    }

    public List<String> saveScreenshots(String prefix) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String time = sdf.format(new Date());
        List<String> snapshotList = new ArrayList<>();

        int fileIndex = 1;
        int maxFileCount = 1;
        String screenshotFilePathPrefix = screenshotFolder + File.separator + prefix + time + "_";
        String preFile = "";
        String nextFile = "";

        try {
            do {
                if (fileIndex > maxFileCount) {
                    log.warn("exceed max snapshot count " + maxFileCount + ", stop continue capturing current page's content. ");
                    break;
                }
                // take snapshot
                nextFile = screenshotFilePathPrefix + (fileIndex++) + ".png";
                int height = doSaveScreenshot(nextFile, preFile);
                if (height <= 0) {
                    log.debug("get the duplicate snapshot, stop capturing page's content.");
                    break;
                }

                // add into snapshot list
                snapshotList.add(nextFile);

                // scroll to next page
                preFile = nextFile;

            } while (true);
        } catch (Exception ex) {
            log.error("fail to save screenshot at: " + nextFile, ex);
        }

        return snapshotList;
    }

    private int doSaveScreenshot(String path, String preFile) throws IOException {
        setNativeContext();
        File source_file = (driver).getScreenshotAs(OutputType.FILE);

        if (preFile == null || preFile.isEmpty() || source_file.length() != new File(preFile).length()) {
            log.debug("try to save screenshot at: " + path);
            FileUtils.copyFile(source_file, new File(path));
            return ImageIO.read(source_file).getHeight();
        } else {
            log.debug("duplicated snapshot. ignore the snapshot taken");
            return 0;
        }
    }

    public List<String> saveSceenshot() {
        return saveScreenshots("screenshot_");
    }

    public AppiumDriver getDriver() {
        return driver;
    }

    public void stop() {
        //Waiting for 2 secs for screenshot
        waitFor(2);
        if (driver != null)
            driver.quit();
    }

    public static void waitFor(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // continue
        }
    }

    public By parseBy(String locator) {
        if (locator.startsWith("id="))
            return MobileBy.id(locator.substring(3));
        else if (locator.startsWith("name="))
            return MobileBy.name(locator.substring(5));
        else if (locator.startsWith("xpath="))
            return MobileBy.xpath(locator.substring(6));
        else if (locator.startsWith("class="))
            return MobileBy.className(locator.substring(6));
        else if (locator.startsWith("css="))
            return MobileBy.cssSelector(locator.substring(4));
        else if (locator.startsWith("linkText="))
            return MobileBy.linkText(locator.substring(9));
        else if (locator.startsWith("pLinkText="))
            return MobileBy.partialLinkText(locator.substring(10));
        else if (locator.startsWith("accessibilityId="))
            return MobileBy.AccessibilityId(locator.substring(16));
        else if (locator.startsWith("iosUIAutomation="))
            return MobileBy.IosUIAutomation(locator.substring(16));
        else if (locator.startsWith("androidUIAutomator="))
            return MobileBy.AndroidUIAutomator(locator.substring(19));
        else
            throw new RuntimeException("This is not a supported format for element locator: " + locator);
    }

    // return fail and warning message
    public String getAppiumLogs() {
        boolean enableLogDump = true;
        if (enableLogDump) {
            StringBuilder returnMessage = new StringBuilder();
            List<LogEntry> logEntries = driver.manage().logs().get("logcat").filter(Level.ALL);
            String message;
            for (LogEntry entry : logEntries) {
                message = "[Appium Server Log]: " + entry.getMessage();
                returnMessage.append(message).append("\n");
            }
            return returnMessage.toString();
        }
        return "";
    }

    public void setWebViewContext() {
        getDriver().context(getDriver().getContextHandles().toArray()[1].toString());
    }

    public void setNativeContext() { getDriver().context(getDriver().getContextHandles().toArray()[0].toString()); }

    public void switchToScreenByIndex(int index) {
        MobileDriverEx.waitFor(2);
        Set<String> windowHandles = driver.getWindowHandles();
        driver.switchTo().window(windowHandles.toArray()[index].toString());
    }

    public static class ScriptExecuteResult {
        protected String ret = "";
        public String errorMsg = "";

        public String str() {
            return ret;
        }

        public int AsInt() {
            if (ret.isEmpty()) return 0;
            return Integer.parseInt(ret);
        }

        public boolean AsBool() {
            if (ret.isEmpty()) return false;
            return Boolean.valueOf(ret);
        }
    }

    public MobileDriverEx.ScriptExecuteResult executeScriptEx(String script) {
        String str = executeScript(script);
        MobileDriverEx.ScriptExecuteResult ret = new MobileDriverEx.ScriptExecuteResult();
        if (str.startsWith("[ERROR]")) {
            ret.errorMsg = str;
        } else {
            ret.ret = str;
        }
        return ret;
    }

    public void setImplicitlyWaitTime(int time) { getDriver().manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS); }

    public void setDefaultImplicitylyWaitTime() { getDriver().manage().timeouts().implicitlyWait(PropertyConfiger.instance().getEnvData("implicitly_wait_timeout", 30), TimeUnit.SECONDS); }
}