package com.oracle.auto.web.comp.ext;

import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

// xtype: numberfiled?
public class ExtNumberBox extends ExtComponentBase{
	public ExtNumberBox(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
	// value - maybe empty, so return string instead of number
	public String getValue() {
		waitForReadyEnabled();
		return getMethodPropIgnoreUndefined(".getValue()").str();
	}
	
	public void setValue(int value) {
		waitForReadyEnabled();
		setMethodProp(".setValue(" + value + ").value"); 
	}
	
	// content is valid for further submit
	public boolean isValid() {
		waitForReadyEnabled();
		return getMethodProp(".isValid()").AsBool();
	}
	
	public String getError() {
		waitForReadyEnabled();
		return getMethodProp(".getErrors()[0]").str();
	}

}
