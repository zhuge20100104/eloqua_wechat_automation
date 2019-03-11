package com.oracle.auto.mobile.components;

import com.oracle.auto.mobile.driver.MobileDriverEx;
import com.oracle.auto.mobile.exceptions.ComponentClickException;
import com.oracle.auto.mobile.exceptions.ComponentGetPropertyException;
import com.oracle.auto.mobile.exceptions.ComponentSetPropertyException;
import com.oracle.auto.mobile.exceptions.JavascriptExecutionException;
import com.oracle.auto.web.utility.WebDriverHelper;
import com.oracle.auto.web.utility.WebUtility;
import org.apache.log4j.Logger;

public class WebViewComponentBase extends AppiumComponentBase {

    private static Logger log = Logger.getLogger(WebViewComponentBase.class);

    public WebViewComponentBase(String locator) {
        super(locator);
        this.rawLocator = locator;
    }

    @Override
    public void setContext() {
        super.getMobileDriverEx().setWebViewContext();
    }


    @Override
    protected void waitForReady() {
        super.waitForReady();
    }

    // get property
    public MobileDriverEx.ScriptExecuteResult getMethodProp(String methodProp) {
        try {
            return runMethodProp(methodProp);
        } catch (Exception ex) {
            throw new ComponentGetPropertyException(getMobileDriverEx(), locator, methodProp, ex);
        }
    }

    // get property(which may be undefined)
    public MobileDriverEx.ScriptExecuteResult getMethodPropIgnoreUndefined(String methodProp) {
        try {
            return runMethodPropIgnoreUndefined(methodProp);
        } catch (Exception ex) {
            throw new ComponentGetPropertyException(getMobileDriverEx(), locator, methodProp, ex);
        }
    }

    // set property
    protected void setMethodProp(String methodProp) {
        try {
            runMethodProp(methodProp);
        } catch (Exception ex) {
            throw new ComponentSetPropertyException(getMobileDriverEx(), locator, methodProp, ex);
        }
    }

    // set property
    protected void setNoReturnMethodProp(String methodProp) {
        try {
            runNoReturnMethod(methodProp);
        } catch (Exception ex) {
            throw new ComponentSetPropertyException(getMobileDriverEx(), locator, methodProp, ex);
        }
    }


    // click sub-element
    protected void clickSubElement(String clickSubElement) {
        try {
            runNoReturnMethod(clickSubElement);
        } catch (Exception ex) {
            throw new ComponentClickException(getMobileDriverEx(), locator + clickSubElement, ex);
        }
    }

    // utility tools to get existing locator's property basing on the locator
    private void runNoReturnMethod(String method) {
        WebDriverHelper.waitForAngular(getDriver());
        String script = "var comp = " + locator + ";" +
                "if(comp==undefined) return \"[ERROR] not found the elemnt. \"; " +
                "comp" + method + ";";
        getMobileDriverEx().executeScriptEx(script);
    }

    private MobileDriverEx.ScriptExecuteResult runMethodProp(String propName) {
        WebDriverHelper.waitForAngular(getDriver());
        String script = "var comp = " + locator + ";" +
                "if(comp==undefined) return \"[ERROR] not found the elemnt. \"; " +
                "var ret = comp" + propName + ";" +
                "if(ret==undefined) return \"[ERROR] element doesn't have this method/property.\"; else return ret;";
        MobileDriverEx.ScriptExecuteResult ret = getMobileDriverEx().executeScriptEx(script);
        if (ret.errorMsg.isEmpty())
            return ret;
        throw new JavascriptExecutionException(getMobileDriverEx(), script, ret.errorMsg);
    }

    private MobileDriverEx.ScriptExecuteResult runMethodPropIgnoreUndefined(String propName) {
        WebDriverHelper.waitForAngular(getDriver());
        String script = "var comp = " + locator + ";" +
                "if(comp==undefined) return \"[ERROR] not found the elemnt. \"; " +
                "var ret = comp" + propName + ";" +
                "if(ret==undefined) return \"\"; else return ret;";
        return getMobileDriverEx().executeScriptEx(script);
    }

    protected static String escape2JS(String s) {
        return WebUtility.escape2JS(s);
    }
}
