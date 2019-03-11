package com.oracle.auto.web.comp.ext;

import com.google.gson.annotations.Expose;
import com.oracle.auto.web.comp.interfaces.IValueDisplay;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

// xtype: displayfield
public class ExtValueDisplay extends ExtComponentBase implements IValueDisplay {
	@Expose private boolean allowEmpty = true;
	public ExtValueDisplay(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
	public ExtValueDisplay(WebDriverSeleniumPageEx page, String locator, boolean allowEmpty) {
		super(page, locator);
		this.allowEmpty = allowEmpty;
	}
	
	public ExtValueDisplay() {}
	
	public void setReadyMode(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}


	@Override
	public boolean isReady() {
		boolean ret = super.isReady();
		if (!ret) return ret;
		if (allowEmpty) return ret;
		
		// basing on test, the value display might disappear in a second and appear again.
		WebDriverSeleniumPageEx.waitFor(1);
		return super.isReady() && !doGetDisplayValue().isEmpty();
	}
	
	// called by public method (changed to get display
	protected String doGetDisplayValue() {
		return getMethodProp(".inputEl.dom.innerText.trim()").str();
		//return getMethodPropIgnoreUndefined(".getValue()").str();
	}
	
	// displayed value
	//      if it's HTML type, need to convert it into plain text rather than directly return the HTML data
	//      if it's POJO type, also need to do that.
	public String getDisplayValue() {
		waitForReadyEnabled();

		return getMethodProp(".inputEl.dom.innerText.trim()").str();
		
	}

	@Override
	public String getValue() {
		return getDisplayValue();
	}
}
