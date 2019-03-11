package com.oracle.auto.web.pages;

import com.oracle.auto.web.comp.html.HTMLComponentBase;
import com.oracle.auto.web.exceptions.ComponentNotVisibleException;
import com.oracle.auto.web.exceptions.JavascriptExecutionException;
import com.oracle.auto.web.utility.PropertyConfiger;
import io.appium.java_client.MobileBy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class WebDriverSeleniumPageEx extends WebDriverEx {
    private static Logger log = Logger.getLogger(WebDriverSeleniumPageEx.class);
    public static final int PAGE_WAIT_ELEMENT_PRESENT_TIMEOUT = PropertyConfiger.instance().getEnvData("component.all.ready.timeout", 30);
    protected final JavascriptExecutor javascriptExecutor;
    private String browserType;
    private String serverDefaultURL;
    private String screenshotFolder;
    private String downloadFolderLocal;
    private String downloadFolderRemote;

    // set/getter
    private boolean uniqueFolder = true;
    private String localUserDataFolder = "";
    private String remoteUserDataFolder = "";

    private final Object LOCK = new Object();

    public void deleteAllVisibleCookies() {
        driver.manage().deleteAllCookies();
    }

    public static class WindowInfo {
        String title;
        String url;
        String bodyText;
    }

    public WebDriverSeleniumPageEx(WebDriver driver, String browserType, String serverDefaultURL, String screenshotFolder, String downloadFolderLocal, String downloadFolderRemote) {
        super(driver);
        this.browserType = browserType;
        this.serverDefaultURL = serverDefaultURL;
        this.screenshotFolder = screenshotFolder;
        this.downloadFolderLocal = downloadFolderLocal;
        this.downloadFolderRemote = downloadFolderRemote;
        this.javascriptExecutor = (JavascriptExecutor) driver;
    }

    private String convertLocatorToJS(String locator, String action) {
        if (locator.startsWith("id="))
            return "var el = document.getElementById('" + locator.substring(3) + "'); if (el != undefined) el" + action;
        else if (locator.startsWith("name="))
            return "var el = document.getElementsByName('" + locator.substring(5) + "')[0]; if (el != undefined) el" + action;
        else if (locator.startsWith("dom="))
            return "var el = " + locator.substring(4) + "; if (el != undefined) el" + action;
        else if (locator.startsWith("javascript="))
            return locator.substring(11) + action;
        else if (locator.startsWith("css=")) {
            String cssName = StringEscapeUtils.escapeEcmaScript(locator.substring("css=".length()));
            return "var el =document.querySelector('" + cssName + "'); el&&el" + action;
        } else {
            return "";
        }
    }

    public static class ScriptExecuteResult {
        protected String ret = "";
        public String errorMsg = "";

        public String str() {
            return ret;
        }

        public int AsInt() {
            if (ret.isEmpty())
                return 0;
            return Integer.parseInt(ret);
        }

        public boolean AsBool() {
            if (ret.isEmpty())
                return false;
            return Boolean.valueOf(ret);
        }
    }

    public void tryToAcceptCertWarnings() {
        try {
            if (this.browserType.contains("iexplore"))
                javascriptExecutor.executeScript("var link = document.getElementById('overridelink'); if (link != undefined) link.click();");
        } catch (Exception ex) {
            //
        }
    }

    // a temporary converter for some special case of java-script, better to change
    // them as the early when script is generated
    private String compatiblityConvert(String script) {
        if (browserType.contains("firefox") && script.contains(".innerText"))
            return script.replace(".innerText", ".textContent");
        if (browserType.contains("safari") && script.contains(".click()"))
            return script.replace(".click()", ".dispatchEvent(BDD_Util_NewClickEvent())");
        return script;
    }

    @SuppressWarnings("serial")
    static final Map<String, String> usefulJS = new HashMap<String, String>() {
        {
            put("BDD_ExtQuery",
                    "if(typeof BDD_ExtFilter=='undefined'){BDD_ExtFilter=function(e){if(e==undefined)e=[];var t=[];e=e.sort(function(e,t){if(e.id==undefined||t.id==undefined)return-1;if(e.id==t.id)return 0;if(e.id<t.id)return-1;return 1});for(var n=0;n<e.length;++n){if(typeof e[n].el!='undefined'&&e[n].el!=undefined&&e[n].el.dom!=undefined&&e[n].el.dom.offsetWidth>1){e[n].ExtQuery=function(e){var t=this.query(e);return BDD_ExtFilter(t)};e[n].ExtDomQuery=function(e,t,n){n=typeof n!=='undefined'?n:true;if(n){var r=this.getEl().query(e)[t]}else{var r=this.query(e)[t]}return Ext.getCmp(r.id)};e[n].HtmlDomQuery=function(e,t,n){n=typeof n!=='undefined'?n:true;if(n){var r=this.getEl().query(e)[t]}else{var r=this.query(e)[t]}return Ext.get(Ext.id(r))};t.push(e[n])}}return t}}if(typeof BDD_ExtQuery=='undefined'){BDD_ExtQuery=function(e){var t=Ext.ComponentQuery.query(e);return BDD_ExtFilter(t)}}if(typeof BDD_ExtDomQuery=='undefined'){BDD_ExtDomQuery=function(e,t){var n=Ext.query(e)[t];return Ext.getCmp(n.id)}}if(typeof BDD_HtmlDomQuery=='undefined'){BDD_HtmlDomQuery=function(e,t){var n=Ext.id(Ext.query(e)[t]);return Ext.get(n)}}");
            put("BDD_Util_NewClickEvent",
                    "if(typeof BDD_Util_NewClickEvent == 'undefined') {  BDD_Util_NewClickEvent = function() { var click_ev = document.createEvent('MouseEvent'); click_ev.initEvent('click', true, true); return click_ev;  }; };");
            put("BDD_Util_RgithClickEvent",
                    "if(typeof BDD_Util_RgithClickEvent == 'undefined') {  BDD_Util_RgithClickEvent = function() { var click_ev = document.createEvent('HTMLEvents'); click_ev.initEvent('contextmenu', true, false); return click_ev;  }; };");
            put("BDD_Util_GetElementByXpath",
                    "if(typeof BDD_Util_GetElementByXpath == 'undefined') { BDD_Util_GetElementByXpath = function (path)      {return document.evaluate(path, document, null, 9, null).singleNodeValue;}; }; ");
            put("BDD_jQuery",
                    "if(typeof BDD_jFilter=='undefined'){BDD_jFilter=function(e){var t=[];for(var n=0;n<e.length;++n){if(typeof e[n]!='undefined'&&e[n]!=undefined&&e[n].offsetWidth>1){e[n].jFind=function(e){var t=$(this).find(e);return BDD_jFilter(t)};t.push(e[n])}}return $(t)}}if(typeof BDD_jQuery=='undefined'){BDD_jQuery=function(e){return BDD_jFilter($(e))}}if(typeof BDD_jFindByAttr=='undefined'){BDD_jFindByAttr=function(e,t,n){var r=e+\"[\"+t+\"=\'\"+n+\"\']\";return $(r)}}if(typeof BDD_jFindBySelector=='undefined'){BDD_jFindBySelector=function(e){return $(e)}}if(typeof BDD_GenId=='undefined'){BDD_GenId=function(e){var t=e.id;var n='genkey_';var r=new Date;if(t=='undefined'||t==''||t==null){var i=r.getTime();t=e.setAttribute('id',n+i)}return e.id}}");
        }
    };

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

    public String browserType() {
        return this.browserType;
    }

    public ScriptExecuteResult executeScriptEx(String script) {
        String str = executeScript(script);
        ScriptExecuteResult ret = new ScriptExecuteResult();
        if (str.startsWith("[ERROR]"))
            ret.errorMsg = str;
        else
            ret.ret = str;
        return ret;
    }

    public String executeScript(String script) {
        script = compatiblityConvert(script);
        script = injectExtendedJS(script);
        // do {
        try {
            Object returnValue = javascriptExecutor.executeScript(script);
            log.debug("[run javascript] Result=" + (returnValue == null ? "undefined" : returnValue.toString()) + "\t[Script] = " + script);
            if (returnValue == null)
                return "";
            return returnValue.toString();
        } catch (Exception ex) {
            throw new JavascriptExecutionException(this, script, ex);
        }
        // } while (true);
    }

    // selenium locator to by
    public static By parseBy(String locator) {
        if (locator.startsWith("id="))
            return By.id(locator.substring(3));
        else if (locator.startsWith("name="))
            return By.name(locator.substring(5));
        else if (locator.startsWith("xpath="))
            return By.xpath(locator.substring(6));
        else if (locator.startsWith("class="))
            return By.className(locator.substring(6));
        else if (locator.startsWith("css="))
            return By.cssSelector(locator.substring(4));
        return By.xpath(locator);
    }

    // click on hidden item
    //	@Override
    public void click(String locator) {
        WebElement ele = driver.findElement(parseBy(locator));
        try {
            if (browserType.contains("iexplore")) {
                javascriptExecutor.executeScript("arguments[0].click();", ele);
            } else {
                ele.click();
            }
        } catch (WebDriverException ex) {
            {
                log.error("click error locator=[" + locator + "], try ", ex);
                waitFor(2);

                String js = convertLocatorToJS(locator, ".click()");
                if (!js.isEmpty()) {
                    log.error("try to click by javascript: " + js);
                    javascriptExecutor.executeScript(js);
                } else {
                    log.error("try to click by web driver dom");

                    new WebDriverWait(driver, PAGE_WAIT_ELEMENT_PRESENT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(ele)).click();
                }
            }
        }
    }


    public void type(String locator, String value) {
        try {
            driver.findElement(parseBy(locator)).sendKeys(value);
        } catch (WebDriverException ex) {
            if (ex.getCause() instanceof ElementNotVisibleException) {
                log.error("type error locator=[" + locator + "], try again");
                String js = convertLocatorToJS(locator, ".value='" + value + "'");
                if (!js.isEmpty())
                    javascriptExecutor.executeScript(js);
                else {
                    waitFor(2);
                    driver.findElement(parseBy(locator)).click();
                }
            } else
                ex.printStackTrace();
        }
    }

    public String getValue(String locator) {
        //return driver.findElement(parseBy(locator)).getText();
        return driver.findElement(parseBy(locator)).getAttribute("value");
    }

    public String getText(String locator) {
        return driver.findElement(parseBy(locator)).getText();
    }

    public boolean isChecked(String locator) {
        return driver.findElement(parseBy(locator)).isSelected();
    }

    public boolean isEditable(String locator) {
        return driver.findElement(parseBy(locator)).isEnabled();
    }

    public boolean isVisible(String locator) {
        return driver.findElement(parseBy(locator)).isDisplayed();
    }

    public boolean isElementPresent(String locator) {
        boolean isPresent = false;
        try {
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            driver.findElement(parseBy(locator));
            isPresent = true;
        } catch (NoSuchElementException ex) {
            log.debug("Element not present, locator: " + locator);
        } finally {
            driver.manage().timeouts().implicitlyWait(PropertyConfiger.instance().getEnvData("webdriver.timeout.search", 20), TimeUnit.SECONDS);
        }
        return isPresent;

    }

    public void contextClick(String xpath) {
        if (this.browserType.contains("safari")) {
            // safari doesn't support userActions, use Javascript to simulate
            // the event.
            this.executeScript(String.format("BDD_Util_GetElementByXpath('%s').dispatchEvent(BDD_Util_RgithClickEvent())", xpath));
        } else {
            WebElement ele = driver.findElement(By.xpath(xpath));
            Actions userActions = new Actions(driver);
            userActions.moveToElement(ele);
            userActions.contextClick(ele);
            userActions.build().perform();
        }
    }

    public void doubleClick(String xpath) {
        WebElement ele = driver.findElement(By.xpath(xpath));
        Actions userActions = new Actions(driver);
        userActions.doubleClick(ele);
        userActions.build().perform();
    }

    public void doubleClickUsingId(String id) {
        WebElement ele = driver.findElement(By.id(id));
        Actions userActions = new Actions(driver);
        userActions.doubleClick(ele);
        userActions.build().perform();
    }

    public void sendkeys(String locator, String keys) {
        WebElement ele = driver.findElement(parseBy(locator));
        ele.sendKeys(keys);
    }

    public void sendEnterKeysById(String id) {
        WebElement ele = driver.findElement(By.id(id));
        ele.sendKeys(Keys.RETURN);
    }

    public void sendKeySequenceByLocator(String locator, CharSequence... keys) {
        WebElement ele = driver.findElement(parseBy(locator));
        ele.sendKeys(keys);
    }

    public void sendKeyDownById(String id, int times) {
        WebElement ele = driver.findElement(By.id(id));
        for (int i = 0; i < times; ++i)
            ele.sendKeys(Keys.DOWN);
    }

    public void clearTextElementValueById(String id) {
        WebElement ele = driver.findElement(By.id(id)).findElement(By.cssSelector("input"));
        ele.clear();
    }

    public void clearText(String locator) {
        WebElement ele = driver.findElement(parseBy(locator));
        ele.clear();
    }

    public String serverDefaultURL() {
        return serverDefaultURL;
    }

    // user name and password
    public Map<String, String> getCookiesMap() {
        Map<String, String> cookies = new HashMap<>();
        for (Cookie cookie : driver.manage().getCookies()) {
            log.info("get cookie: name=" + cookie.getName() + " value=" + cookie.getValue());
            cookies.put(cookie.getName(), cookie.getValue());
        }
        return cookies;
    }

    public void SetCookiesWithJavaScript(Map<String, String> cookies) {
        String addCookieFormat = "document.cookie='%s=%s; expires=Wed, 1 Jul 2020 01:00:00 UTC; path=/'";
        for (Entry<String, String> cookie : cookies.entrySet()) {
            executeScript(String.format(addCookieFormat, cookie.getKey(), cookie.getValue()));
        }
    }

    public List<String> saveSceenshot() {
        return saveScreenshots("screenshot_");
    }

    private int doSaveScreenshot(String path, String preFile) throws IOException {
        Augmenter augmenter = new Augmenter();
        TakesScreenshot ts = (TakesScreenshot) augmenter.augment(driver);
        File source_file = ts.getScreenshotAs(OutputType.FILE);

        if (preFile == null || preFile.isEmpty() || source_file.length() != new File(preFile).length()) {
            log.debug("try to save screenshot at: " + path);
            FileUtils.copyFile(source_file, new File(path));
            return ImageIO.read(source_file).getHeight();
        } else {
            log.debug("duplicated snapshot. ignore the snapshot taken");
            return 0;
        }
    }

    // scroll to beginning only used for browser chrome
    public void downScrollBar() {
        executeScript("var q=document.documentElement.scrollTop=10000");
        // need to wait for 2 second
        waitFor(2);
    }

    public void upScrollBar() {
        executeScript("var q=document.documentElement.scrollTop=0");

        // need to wait for 2 second
        waitFor(2);
    }

    private void scrollPageTopChrome() {
        String script = "try { " + "document.body.scrollByPages(-100);} catch(err) { } ";
        executeScript(script);

        // need to wait for 1 second
        waitFor(1);
    }

    // only used for browser chrome
    private void scrollPageChrome() {
        String script = "try { " + "document.body.scrollByPages(1); } catch(err) { } ";
        executeScript(script);

        // need to wait for 1 second
        waitFor(1);
    }

    public List<String> saveScreenshots(String prefix) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        String time = sdf.format(new Date());
        List<String> snapshotList = new ArrayList<>();

        int fileIndex = 1;
        int maxFileCount = 10;
        String screenshotFilePathPrefix = screenshotFolder + File.separator + prefix + time + "_";
        String preFile = "";
        String nextFile = "";

        // if it's firefox, it will save whole page directly.
        boolean isChrome = this.browserType.contains("googlechrome");

        try {
//			This is not required now as the browser is already is maximized state
//			driver.manage().window().maximize();
//			Thread.sleep(2000); // wait for 1 seconds for window's ready

            // if it's chrome, need to scoll to top for snapshot taken
            if (isChrome)
                scrollPageTopChrome();


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

                // only take one for firefox and iexplore
                if (!isChrome) {
                    log.debug("it's firefox or ie, only need to take one snapshot.");
                    break;
                }

                // scroll to next page
                preFile = nextFile;
                scrollPageChrome();

            } while (true);
        } catch (Exception ex) {
            log.error("fail to save screenshot at: " + nextFile, ex);
        }

        return snapshotList;
    }

    // return fail and warning message
    public String dumpConsoleLog() {
        boolean enableLogDump = true;
        if (enableLogDump) {
            if (this.browserType.contains("iexplore")) {
                log.warn("ie doesn't support console log collection. ");
                return "";
            }

            StringBuilder returnMessage = new StringBuilder();
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER);
            String message;
            Level level;
            for (LogEntry entry : logEntries) {
                message = "[browser log] " + entry.getMessage();
                level = entry.getLevel();
                if (level.equals(Level.SEVERE)) {
                    log.error(message);
                    returnMessage.append(message).append("\n");
                } else if (level.equals(Level.WARNING)) {
                    log.warn(message);
                    returnMessage.append(message).append("\n");
                } else if (level.equals(Level.INFO) || level.equals(Level.CONFIG))
                    log.info(message);
                else
                    log.debug(message);
            }
            return returnMessage.toString();
        }
        return "";
    }


    // download folder
    public String getLocalDownloadFolder() {
        return this.downloadFolderLocal;
    }

    // download folder
    public String getRemoteDownloadFolder() {
        return this.downloadFolderRemote;
    }

    public String getLocalUserDataFolder() {
        return localUserDataFolder;
    }

    public void setUserDataFolder(String userDataFolder) {
        this.localUserDataFolder = userDataFolder;
    }

    // stop
    public void stop() {
        // super.selenium.stop();
        // waiting for 2 secs for screenshot
        waitFor(2);
        if (((RemoteWebDriver) driver).getSessionId() != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                //do nothing
            }
        }
        waitFor(2); // wait for 2 seconds to later clean up

        if (uniqueFolder) {
            try {
                log.info("clean download folder: " + downloadFolderLocal);
                File file = new File(downloadFolderLocal);
                if (file.exists())
                    FileUtils.deleteDirectory(file);

                file = new File(localUserDataFolder);
                log.info("clean user folder: " + localUserDataFolder);
                if (file.exists())
                    FileUtils.deleteDirectory(file);
            } catch (IOException e) {
                log.error("fail to stop current web page", e);
            }
        } else {
            log.info("it's configured as non unique folder. skip deleting folder. will delete after whole execution.");

        }
    }

    // this method will refresh current page before switch to solve an IE issue of switching.
    // it can also close current window before switch
    private void safeSwitch(String hanlde, boolean closeCurrent) {
        if (driver.getWindowHandle() == hanlde) {
            log.info("try to switch to itself, window: " + driver.getTitle()
                    + " url: " + driver.getCurrentUrl() + " content: "
                    + driver.getPageSource());
            return;
        }

        log.info("before switch, window: " + driver.getTitle() + " url: "
                + driver.getCurrentUrl() + " content: " + driver.getPageSource());

        if (closeCurrent) {
            log.info("need to close current window");
            driver.close();
        } else {
            log.info("need to refresh the page if it's IE");
            if (browserType.contains("iexplore"))
                refresh();
        }

        driver.switchTo().window(hanlde);
        log.info("after switch, window: " + driver.getTitle() + " url: "
                + driver.getCurrentUrl() + " content: " + driver.getPageSource());
    }

    public List<WindowInfo> getNewWindowDatas() {
        String current = driver.getWindowHandle();

        Set<String> set = driver.getWindowHandles();

        log.info("get window count: " + set.size());

        List<WindowInfo> lst = new ArrayList<WindowInfo>();

        for (String window : set) {
            if (window.equals(current))
                continue;
            safeSwitch(window, false);
            WindowInfo data = new WindowInfo();
            data.title = driver.getTitle();
            data.url = driver.getCurrentUrl();
            data.bodyText = driver.getPageSource();
            System.out.println("get window: " + data.title + " url: "
                    + data.url + " content: " + data.bodyText);

            lst.add(data);
        }

        // switch back
        safeSwitch(current, false);

        return lst;
    }

    public void closeCurrentWindowAndSwitch() {
        Set<String> set = driver.getWindowHandles();
        String closedHandle = driver.getWindowHandle();
        log.info("before close window, closedHandle: " + closedHandle
                + " existing: " + driver.getWindowHandles().toString());

        if (set.size() <= 1)
            throw new RuntimeException("there's no other window to switch");

        // select a new windows to switch
        String newWindow = "";
        for (String window : set) {
            if (window.equals(closedHandle))
                continue;
            newWindow = window;
            break;
        }

        // close current window and switch
        safeSwitch(newWindow, true);
    }

    public void switchToOtherWindow() {
        Set<String> windowHandles = driver.getWindowHandles();
        String currentWindow = driver.getWindowHandle();

        for (String window : windowHandles) {
            if (window.equals(currentWindow)) {
                continue;
            }
            safeSwitch(window, false);
            break;
        }
    }

    public void closeOtherWindows() {
        Set<String> set = driver.getWindowHandles();
        String current = driver.getWindowHandle();
        log.info("before close other windows, current handle: " + current
                + " existing: " + driver.getWindowHandles().toString());
        if (set.size() <= 1)
            return;

        // refresh current page, then close all other pages (IE only)
        if (browserType.contains("iexplore"))
            refresh();

        for (String window : set) {
            if (window.equals(current))
                continue;
            driver.switchTo().window(window);
            log.info("close window: " + driver.getTitle() + " url: "
                    + driver.getCurrentUrl() + " content: "
                    + driver.getPageSource());
            driver.close();
        }

        // switch back to current
        driver.switchTo().window(current);
    }

    public void switchTo(String regTitle) {
        String current = driver.getWindowHandle();

        Set<String> set = driver.getWindowHandles();
        log.info("get window count: " + set.size());
        for (String window : set) {
            if (window.equals(current))
                continue; // no switch to itself
            safeSwitch(window, false);
            log.info("check switch window: " + driver.getTitle() + " url: "
                    + driver.getCurrentUrl() + " content: "
                    + driver.getPageSource());
            if (!driver.getTitle().matches(regTitle)) {
                safeSwitch(current, false); // switch back
            }

            log.info("switch to window: " + window + " title: "
                    + driver.getTitle() + " url: " + driver.getCurrentUrl()
                    + " content: " + driver.getPageSource());
            return;
        }

        throw new RuntimeException("there's no window available to switch: "
                + regTitle);
    }

    /**
     * This method helps in switching between browser tabs based on supplied
     * index.
     *
     * @param index
     */
    public void switchTabByIndex(int index) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if ((index < 0) && (index > tabs.size() - 1)) {
            throw new RuntimeException("There is no tab with index [" + index
                    + "].");
        }
        // driver.switchTo().window(tabs.get(index));
        safeSwitch(tabs.get(index), false);
    }

    /**
     * Closes the browser based on supplied tab index.
     *
     * @param tabIndex
     */
    public void closeBrowserTab(int tabIndex) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        if (tabs.size() <= 0) {
            throw new RuntimeException("There is only one tab on the browser. Doing nothing....");
        }
        driver.switchTo().window(tabs.get(tabIndex));
        driver.close();
        driver.switchTo().window(tabs.get(0));
    }

    public void switchToDefaultTab() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(0));
    }

    public void duplicateCurrentWindow(String locator) {
        // get current URL and sub window count
        String url = "";
        //This is a workaround for IE. Due to an issue with IEDriver, for applications that use angular the url is not properly extracted. Angular open issue https://github.com/angular/protractor/issues/778
        try {
            if (this.browserType.contains("iexplore")) {
                url = this.executeScript(String.format("return angular.element(%s).injector().get('$location').absUrl()", locator));
            } else
                url = driver.getCurrentUrl();
        } catch (Exception ex) {
            //
        }

        if (url.isEmpty())
            url = "about:blank";

        int count1 = driver.getWindowHandles().size();
        String script = String.format("window.open('%s', '_blank');", url);
        executeScript(script);
        int count2 = driver.getWindowHandles().size();
        if (count2 <= count1)
            throw new RuntimeException(
                    "cannot open new window for current url: " + url);
        switchTabByIndex(count2 - 1);
    }

    public void switchToWindowAtLevel(int level) {
        int count = 0;
        for (String winHandle : driver.getWindowHandles()) {
            if (count == level) {
                driver.switchTo().window(winHandle);
                return;
            }
            count++;
        }
    }

    public void switchToChildWindow() {
//        String parent = driver.getWindowHandle();
//        String tile = driver.getTitle();
//
//        Set<String> windowsHandlers = driver.getWindowHandles();
//        for(String winHandler : windowsHandlers){
//            if (driver.switchTo().window(winHandler).getPageSource().contains("Add Official Account")){
//                driver.switchTo().window(winHandler);
//                break;
//            }
//        }

        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
        // driver.getPageSource();

        // WebElement element = driver.findElement(By.xpath(".//iframe[@src='https://apps.p01.elqqa01.com/oardc/lifecycle/app/viewConfig/54d7d809-52f9-4b88-8d8b-4d91786d2416/1daf3402-5f1e-4305-a3aa-1300ab048835/1640918993?userName=LIan.Liu&callback=nothing&userId=86&siteName=QAE10test&oauth_consumer_key=54d7d809-52f9-4b88-8d8b-4d91786d2416&oauth_nonce=198617607&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1499750277&oauth_version=1.0&oauth_signature=dW1%2FS5Gx7E5IG%2FYLNr3A7owXHFQ%3D']"));
        // driver.switchTo().frame(element);
        //  driver.switchTo().frame(0);

//        WebElement element = driver.findElement(By.xpath(".//iframe[@src='https://apps.p01.elqqa01.com/oardc/lifecycle/app/viewConfig/54d7d809-52f9-4b88-8d8b-4d91786d2416/1daf3402-5f1e-4305-a3aa-1300ab048835/1640918993?userName=LIan.Liu&callback=nothing&userId=86&siteName=QAE10test&oauth_consumer_key=54d7d809-52f9-4b88-8d8b-4d91786d2416&oauth_nonce=198617607&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1499750277&oauth_version=1.0&oauth_signature=dW1%2FS5Gx7E5IG%2FYLNr3A7owXHFQ%3D']"));
//        driver.switchTo().frame(element);

//        WebElement element = driver.findElement(By.tagName("iframe"));
//        driver.switchTo().frame(element);

//        WebElement frame = driver.findElement(By.cssSelector("div#appConfigContainer iframe"));
//        driver.switchTo().frame(frame);

        //driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='Fill Quote']")));

        // String currentWindow = driver.getWindowHandle();
//        driver.switchTo().window(currentWindow);
    }

    public void goBack() {
        if (browserType.contains("safari") || browserType.contains("iexplore")) {
            // on iexplore and safari, go back by selenium will make the
            // connection between web driver and browser lose
            // using javaScript to go back will have no such problem
            this.executeScript("history.back()");
        } else
            driver.navigate().back();
    }

    public String getRemoteUserDataFolder() {
        return remoteUserDataFolder;
    }

    public void setRemoteUserDataFolder(String remoteUserDataFolder) {
        this.remoteUserDataFolder = remoteUserDataFolder;
    }

    public boolean isUniqueDownloadUserFolder() {
        return uniqueFolder;
    }

    public void setUniqueDownloadUserFolder(boolean uniqueFolder) {
        this.uniqueFolder = uniqueFolder;
    }

    public Select getSelectElement(String locator) {
        return new Select(driver.findElement(parseBy(locator)));
    }


    public WebDriver getDriver() {
        return driver;
    }

    public String getCssValue(String locator, String cssProperty) {
        WebElement ele = driver.findElement(parseBy(locator));
        return ele.getCssValue(cssProperty);
    }

    public String getAttributeValue(String locator, String attribute) {
        WebElement ele = driver.findElement(parseBy(locator));
        return ele.getAttribute(attribute);
    }

    public boolean hasAttribute(String locator, String attribute) {
        WebElement ele = driver.findElement(parseBy(locator));
        if (ele.getAttribute(attribute) == null)
            return false;
        return true;
    }

    public void waitForVisible(String locator, int timeOut) {
        WebDriverWait wait = new WebDriverWait(driver, timeOut);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(parseBy(locator)));
        } catch (ComponentNotVisibleException ex) {
            throw new ComponentNotVisibleException(this, locator, ex);
        }
    }

    public boolean isSelected(String locator) {
        WebElement ele = driver.findElement(parseBy(locator));
        return ele.isSelected();
    }

    public void pressESCKey() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ESCAPE);
    }

    public void hoverMouse(String elementId) {
        WebElement ele = driver.findElement(By.id(elementId));
        Actions action = new Actions(driver);
        action.moveToElement(ele).build().perform();
    }

    public void hoverMouseAndWait(final String elementId) {
        Thread localThread = new Thread(new Runnable() {
            public void run() {
                synchronized (LOCK) {
                    hoverMouse(elementId);
                    try {
                        LOCK.wait();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
        }, "MouseHoverActionHelper");
        localThread.start();
    }

    public void release() {
        synchronized (LOCK) {
            LOCK.notify();
        }
    }

    public void open(String url) {
        if (!url.contains("://"))
            driver.get("http://" + url);
        else
            driver.get(url);
        driver.manage().window().maximize();
    }

    public void openFile(String url) {
        if (!url.contains("://"))
            driver.get("file:///" + url);
        else
            driver.get(url);
    }


    public WebElement findElementById(String id) {
        return driver.findElement(parseBy("id=" + id));
    }

    public WebElement findElementByCssSelector(String css) {
        return driver.findElement(parseBy("css=" + css));
    }

    private static final String CLOUD_CONTENT_CONFIGURATION_ID = "cloudContentConfigurationFrame";
    private static final String CLOUD_CONTENT_FRAME_ID = "cloudAppContentFrame";
    private static final String FULL_PAGE_FRAME = "fullPageFrame";

    public void switchToFullPageFrame() {
        driver.switchTo().frame(driver.findElement(By.id(FULL_PAGE_FRAME)));
    }

    public void switchToCloudContentConfigurationFrame() {
        driver.switchTo().frame(driver.findElement(By.id(CLOUD_CONTENT_CONFIGURATION_ID)));
    }

    public void switchToDefaultFrame() {
        driver.switchTo().defaultContent();
        //driver.findElement(By.)
        //executeScript("");
    }

    public void switchToIFrame(){
        driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
    }
    public void switchToCloudContentFrame() {
        driver.switchTo().frame(driver.findElement(By.id(CLOUD_CONTENT_FRAME_ID)));
    }

}
