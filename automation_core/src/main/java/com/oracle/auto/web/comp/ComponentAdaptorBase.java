package com.oracle.auto.web.comp;

import com.google.gson.annotations.Expose;
import com.oracle.auto.web.comp.interfaces.IComponent;
import com.oracle.auto.web.exceptions.*;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.utility.FieldHelper;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebDriverHelper;
import com.oracle.auto.web.utility.WebUtility;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.oracle.auto.web.pages.object.PageBase.PAGE_WAIT_PAGE_READY_TIMEOUT;


public abstract class ComponentAdaptorBase implements IComponent {
    private static Logger log = Logger.getLogger(ComponentAdaptorBase.class);
    private String lastError = "";
    protected WebDriverSeleniumPageEx page = null;

    protected WebDriver driver = null;
    @Expose
    protected boolean hideDefault = false;
    @Expose
    protected String locator = "";

    @Expose
    protected String rawLocator = "";

    @Expose
    protected String idLocator = "";

    @Override
    public void setPage(WebDriverSeleniumPageEx page) {
        this.page = Objects.requireNonNull(page);
        driver = Objects.requireNonNull(page.getDriver());
    }

    @Override
    public WebDriverSeleniumPageEx getPage() {
        return page;
    }

    protected ComponentAdaptorBase() {
//        if(this.page == null)
//            this.page = SeleniumPageFactory.instance().getLastPage();
    }

    @Override
    public void setConfig(String jsonValue) {
        FieldHelper.setFields(jsonValue, this, Expose.class);
    }

    // TODO: dump itself's every fields
    @Override
    public String toString() {
        String base = super.toString();
        return base + " " + getClass().getSimpleName() + " fields: " + FieldHelper.dumpObjects(this);
    }

    public boolean isEnabled() {
        return true;
    }

    public boolean isReady() {
        return true;
    }

    public boolean hideDefault() {
        return hideDefault;
    }

    public void waitForReadyEnabled() {
        WebDriverHelper.waitForAngular(page.getDriver());
        waitForElementReady(PropertyConfiger.instance().getEnvData("component." + getClass().getSimpleName() + ".ready.timeout", PAGE_WAIT_PAGE_READY_TIMEOUT));
        //waitForElementEnabled(PropertyConfiger.instance().getEnvData("component." + getClass().getSimpleName() + ".ready.timeout", PAGE_WAIT_ELEMENT_PRESENT_TIMEOUT));
    }

    public void waitForReadyEnabled(int timeout) {
        waitForElementReady(timeout);
        //waitForElementEnabled(timeout);
    }

    public void waitForReady() {
        //WebDriverHelper.waitForAngular(page.getDriver());
        waitForElementReady(PropertyConfiger.instance().getEnvData("component." + getClass().getSimpleName() + ".ready.timeout", PAGE_WAIT_PAGE_READY_TIMEOUT));
    }

    public void waitForElementReady(int timeout) {
        int i = 0;
        int waitUnit = 1;
        while (!isReady()) {
            if ((i += waitUnit) > timeout) {
                throw new ComponentNotReadyException(page, this.toString(), "time out to wait for the component to be ready in " + timeout + " seconds");
            }
            try {
                Thread.sleep(waitUnit * 1000);
                waitUnit = 1;
            } catch (Exception ex) {
                //
            }
        }
    }

    public void waitForElementNotReady(int timeout) {
        int i = 0;
        int waitUnit = 1;
        while (isReady()) {
            if ((i += waitUnit) > timeout) {
                throw new ComponentNotReadyException(page, this.toString(), "time out to wait for the component to be disappear in " + timeout + " seconds");
            }
            try {
                Thread.sleep(waitUnit * 1000);
                waitUnit = 1;
            } catch (Exception ex) {
                //
            }
        }
    }

    public void waitForElementEnabled(int timeout) {
        int i = 0;
        int waitUnit = 1;
        while (!isEnabled()) {
            if ((i += waitUnit) > timeout) {
                throw new ComponentNotEnabledException(page, this.toString(), "time out to wait for the component to be ready in " + timeout + " seconds");
            }
            try {
                Thread.sleep(waitUnit * 1000);
                waitUnit = 1;
            } catch (Exception ex) {
            }
        }
    }

    @Override
    public String getLastError() {
        return doGetLastError();
    }

    protected String doGetLastError() {
        return lastError;
    }

    protected void doSetLastError(String err) {
        lastError = err;
    }

    protected boolean isPresent() {
        String script = "var comp = " + locator + ";if(comp==undefined)return false;else return true;";
        boolean ret = page.executeScriptEx(script).AsBool();
        if (!ret) {
            log.debug("component [" + locator + "] doesn't present on page.");
            doSetLastError(locator + ":" + "element doesn't present on page");
        }
        return ret;
    }

    // get property
    public WebDriverSeleniumPageEx.ScriptExecuteResult getMethodProp(String methodProp) {
        try {
            return runMethodProp(methodProp);
        } catch (Exception ex) {
            throw new ComponentGetPropertyException(page, locator, methodProp, ex);
        }
    }

    // get property(which may be undefined)
    public WebDriverSeleniumPageEx.ScriptExecuteResult getMethodPropIgnoreUndefined(String methodProp) {
        try {
            return runMethodPropIgnoreUndefined(methodProp);
        } catch (Exception ex) {
            throw new ComponentGetPropertyException(page, locator, methodProp, ex);
        }
    }

    // set property
    protected void setMethodProp(String methodProp) {
        try {
            runMethodProp(methodProp);
        } catch (Exception ex) {
            throw new ComponentSetPropertyException(page, locator, methodProp, ex);
        }
    }

    // set property
    protected void setNoReturnMethodProp(String methodProp) {

        try {
            runNoReturnMethod(methodProp);
        } catch (Exception ex) {
            throw new ComponentSetPropertyException(page, locator, methodProp, ex);
        }
    }


    // click sub-element
    protected void clickSubElement(String clickSubElement) {
        try {
            runNoReturnMethod(clickSubElement);
        } catch (Exception ex) {
            throw new ComponentClickException(page, locator + clickSubElement, ex);
        }
    }

    // utility tools to get existing locator's property basing on the locator
    protected void runNoReturnMethod(String method) {
        String script = "var comp = " + locator + ";" +
                "if(comp==undefined) return \"[ERROR] not found the elemnt. \"; " +
                "comp" + method + ";";
        page.executeScriptEx(script);
    }

    protected WebDriverSeleniumPageEx.ScriptExecuteResult runMethodProp(String propName) {
        String script = "var comp = " + locator + ";" +
                "if(comp==undefined) return \"[ERROR] not found the elemnt. \"; " +
                "var ret = comp" + propName + ";" +
                "if(ret==undefined) return \"[ERROR] element doesn't have this method/property.\"; else return ret;";
        WebDriverSeleniumPageEx.ScriptExecuteResult ret = page.executeScriptEx(script);
        if (ret.errorMsg.isEmpty())
            return ret;
        throw new JavascriptExecutionException(page, script, ret.errorMsg);
    }

    private WebDriverSeleniumPageEx.ScriptExecuteResult runMethodPropIgnoreUndefined(String propName) {
        String script = "var comp = " + locator + ";" +
                "if(comp==undefined) return \"[ERROR] not found the elemnt. \"; " +
                "var ret = comp" + propName + ";" +
                "if(ret==undefined) return \"\"; else return ret;";
        return page.executeScriptEx(script);
    }

    // utility for javascript special charactor escape
    protected static String escape2JS(String s) {
        return WebUtility.escape2JS(s);
    }

    // utility for javascript special charactor escape
    protected String escape2JSReg(String s) {
        return WebUtility.escape2JSReg(s, page.browserType());
    }

    public String getLocator() {
        return locator;
    }

    public String getRawLocator() {
        return rawLocator;
    }

    public void setLocator(String locator) {
        this.locator = locator;
    }

    public void waitForDone(int time) {
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void setRelativeLocator(ComponentAdaptorBase ele) {
        ele.setLocator(getLocator() + ele.getRawLocator());
    }

    protected void setRelativeIndexLocator(ComponentAdaptorBase ele) {
        ele.setLocator(getLocator() + "[%d]" + ele.getRawLocator());
    }
}
