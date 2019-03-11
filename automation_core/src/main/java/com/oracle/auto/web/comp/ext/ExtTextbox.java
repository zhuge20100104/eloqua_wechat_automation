package com.oracle.auto.web.comp.ext;

import com.google.gson.annotations.Expose;
import com.oracle.auto.web.comp.interfaces.IValuable;
import com.oracle.auto.web.exceptions.ComponentSetPropertyException;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

//xtype: textfield, textarea
public class ExtTextbox extends ExtComponentBase implements IValuable {
	@Expose private boolean allowEmpty = true;

	public ExtTextbox(WebDriverSeleniumPageEx page, String locator) {
		this(page, locator, true);
	}
	
	public ExtTextbox(WebDriverSeleniumPageEx page, String locator, boolean allowEmpty) {
		super(page, locator);
		this.allowEmpty = allowEmpty;
	}
	
	public ExtTextbox() {}

	@Override
	public boolean isReady() {
		return super.isReady() && (allowEmpty ? true : !doGetValue().isEmpty());
	}
	
	// get value
	protected String doGetValue() {
		return getMethodProp(".getValue()").str();
	}
	
	// value
	public String getValue() {
		waitForReady();
		return doGetValue();
	}
	
	public void setValue(String value) {
		waitForReadyEnabled();
		setNoReturnMethodProp(".setValue(\"" + escape2JS(value) + "\")");
		
		// verify if it's set as expected
		String aftervalue = doGetValue();
		if (!aftervalue.equals(value))
			throw new ComponentSetPropertyException(page,locator, "value", "fail to set the value, expected = " + value + " after set, real=" + aftervalue);
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
	
	public boolean isBeaconRequired() {
		waitForReadyEnabled();
		return getMethodProp(".el.dom.getAttribute('class').indexOf('beacon-required') >= 0").AsBool();
	}

}
