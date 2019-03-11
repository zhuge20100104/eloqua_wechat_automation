package com.oracle.auto.web.comp.ext;

import com.oracle.auto.web.comp.ComponentAdaptorBase;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.apache.log4j.Logger;


public class ExtComponentBase extends ComponentAdaptorBase {
	private static Logger log = Logger.getLogger(ExtComponentBase.class);
	
	public ExtComponentBase(WebDriverSeleniumPageEx page, String locator) {
		this.page = page;
		this.locator = locator;
	}
	
	public ExtComponentBase() {
		
	}

	@Override
	public String toString() {
		return super.toString() + " " + getClass().getSimpleName() + " [locator=" + locator + "]";
	}


	@Override
	// if disabled
	public boolean isEnabled()
	{
		try {
			boolean ret = !runMethodProp(".isDisabled()").AsBool();
			if (!ret) {
				doSetLastError("element [" + locator + "] is not enabled.");
			}
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// isReady for read and write: basic Ext is loaded, then element present, rendered, visible and valid
	public boolean isReady() {
		try {
			String script = String.format("if (typeof Ext == 'undefined' || typeof Ext.ComponentQuery == 'undefined') return '[ERROR] Ext is not loaded';"
					+ "var cmp = %s; if (cmp == undefined) return '[ERROR] element is not present';"
					+ "var isrend = cmp.rendered; if (isrend == undefined || !isrend) return '[ERROR] element is not rended.';"
					+ "var isvisible = cmp.rendered; if (isvisible == undefined || !isvisible) return '[ERROR] element is not visible.';"
					+ " return true; ",
					locator);

			WebDriverSeleniumPageEx.ScriptExecuteResult ret = page.executeScriptEx(script);
			if (!ret.AsBool()) {
				doSetLastError("component[" + toString() + "] error [" + ret.errorMsg + "]");
				log.debug("element [" + locator + "] is not ready. detail error: " + ret.errorMsg);
			}
			
			return ret.AsBool();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

	
	// locator's properties
	protected boolean isExtLoaded() {
		String script = "return (typeof Ext != 'undefined' && typeof Ext.ComponentQuery != 'undefined')";
		boolean ret;
		try {
			ret = page.executeScriptEx(script).AsBool();
		} catch (Exception e) {
			// ignore the unexpected exception and consider Ext not loaded
			ret = false;
		}
		if (!ret) {
			log.debug("Ext module is not loaded.");
			doSetLastError(locator + ":" + "Ext is not loaded");
		}
		
		return ret;
	}

    protected boolean isSubElementReady(String subElementLocator) {
        ExtComponentBase sub = new ExtComponentBase(page, locator + subElementLocator);
        return sub.isReady();
    }


}
