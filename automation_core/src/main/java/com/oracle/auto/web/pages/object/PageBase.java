package com.oracle.auto.web.pages.object;

import com.oracle.auto.web.comp.interfaces.IComponent;
import com.oracle.auto.web.exceptions.PageInitException;
import com.oracle.auto.web.exceptions.PageStatusException;
import com.oracle.auto.web.multithread.SeleniumPageFactory;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


abstract public class PageBase implements IPage {
    private static Logger log = Logger.getLogger(PageBase.class);
    public static final int PAGE_WAIT_PAGE_READY_TIMEOUT = PropertyConfiger.instance().getEnvData("page.all.ready.timeout", 60);
    protected WebDriverSeleniumPageEx page;
    Map<String, IComponent> compList = new HashMap<>();
    private IPageMaker pageMaker = null;
    //private static boolean springEnabled = PropertyConfiger.instance().getEnvData("enable.spring", true);

    @Override
    public void setPage(WebDriverSeleniumPageEx page) {
        this.page = page;
    }

    @Override
    public WebDriverSeleniumPageEx page() {
        if (page == null)
            page = SeleniumPageFactory.instance().getLastPage();
        return page;
    }

    protected void init(){};

    public void resetPage(WebDriverSeleniumPageEx page) {
        setPage(page);
        init();
    }

    @Override
    public void setPageMaker(IPageMaker maker) {
        pageMaker = maker;
    }

    public <T extends PageBase> T pageAs(Class<T> pageClass, Object... initArgs) {
        return pageMaker.pageAs(pageClass, initArgs);
    }

    public <T extends PageBase> T backAs(Class<T> pageClass, Object... initArgs) {
        page.goBack();
        WebDriverSeleniumPageEx.waitFor(2);
        return pageMaker.pageAs(pageClass, initArgs);
    }

    public <T extends PageBase> T refreshAs(Class<T> pageClass, Object... initArgs) {
        page.refresh();
        WebDriverSeleniumPageEx.waitFor(2);
        return pageMaker.pageAs(pageClass, initArgs);
    }

    public <T extends PageBase> T closeAndSwitchToAs(Class<T> pageClass, Object... initArgs) {
        page.closeCurrentWindowAndSwitch();
        return pageMaker.pageAs(pageClass, initArgs);
    }

    public <T extends PageBase> T switchToAs(Class<T> pageClass, Object... initArgs) {
        page.switchTo();
        return pageMaker.pageAs(pageClass, initArgs);
    }

    public <T extends PageBase> T switchToAs(String titleReg, Class<T> pageClass, Object... initArgs) {
        page.switchTo(titleReg);
        return pageMaker.pageAs(pageClass, initArgs);
    }

    PageBase() {
        log.debug("[New Page Object] trying to enter page: " + this.getClass().getName());
        if (this.page == null)
            page = SeleniumPageFactory.instance().getLastPage();
    }


    private String lastReadyError = "";

    protected boolean isReady() {
        for (IComponent comp : compList.values()) {
            if (comp.hideDefault()) continue;
            if (!comp.isReady()) {
                lastReadyError = comp.getLastError();
                if (lastReadyError.isEmpty())
                    lastReadyError = "component [" + comp.toString() + "] is not ready.";
                return false;
            }
        }

        return true;
    }

    protected void registerComp(IComponent comp) {
        registerComp(comp, true);
    }

    private void registerComp(IComponent comp, boolean isRegistrationRequired) {
        comp.setPage(page);
        if (isRegistrationRequired)
            compList.put("Component " + ThreadLocalRandom.current().nextInt(1000000), comp);
    }


    void registerComp(String compName, IComponent comp) {
        compList.put(compName, comp);
    }

    // wait for page loading and elements rendering
    @Override
    public void waitForPageReady() {
        if (!waitForPageReadyTimout())
            throw new PageInitException(page, getClass().getName(), "time out to wait for it's ready. detail: " + lastReadyError);
    }

    // wait for page loading and elements rendering
    public boolean waitForPageReadyTimout() {
        return waitForPageReadyTimout(PropertyConfiger.instance().getEnvData("page." + getClass().getSimpleName() + ".ready.timeout", PAGE_WAIT_PAGE_READY_TIMEOUT));
    }

    public boolean waitForPageReadyTimout(int timeoutSeconds) {
        // wait for ajax loading for page elements
        int i = 0;
        int waitUnit = 1;
        while (!isReady()) {
            if ((i += waitUnit) > timeoutSeconds) {
                return false;
            }
            WebDriverSeleniumPageEx.waitFor(waitUnit);
            waitUnit = 1;
        }
        return true;
    }

    public String waitForFileDownload(String fileName, int timeout, int max_time_count) {
        String filePath = page.getLocalDownloadFolder() + File.separator + fileName;

        // wait for max_time_count seconds for file exist
        verify_exist:
        while (true) {
            for (int i = 0; i < max_time_count; ++i) {
                if (new File(filePath).exists())
                    break verify_exist;

                WebDriverSeleniumPageEx.waitFor(1);
            }

            // not exist
            throw new PageStatusException(page, getClass().getSimpleName(), "file doens't exist: " + filePath);
        }

        // wait the file downloading
        verify_no_change:
        while (true) {
            int max_time_out = timeout;
            long size1 = new File(filePath).length();
            int no_change_counter = 0;
            int max_no_change_counter = 10;    // wait for 10 seconds if the size keep no change
            for (int i = 0; i < max_time_out; ++i) {
                long size2 = new File(filePath).length();
                if (size2 != size1) {
                    // keep changing.
                    size1 = size2;
                    no_change_counter = 0; // reset no change counter
                    continue;
                } else {
                    // keep no change
                    if (no_change_counter++ >= max_no_change_counter)
                        break verify_no_change;
                }

                WebDriverSeleniumPageEx.waitFor(1);
            }

            throw new PageStatusException(page, getClass().getSimpleName(), "downloaded file keep increasing and time out: " + filePath);
        }


        return filePath;
    }

    // file download related - return downloaded file path
    public String waitFileDownload(String fileName, int timeout) {
        // wait for 20 seconds for file exist
        return waitForFileDownload(fileName, timeout, 20);
    }

    @Override
    public void loadPageConfig() { }
}