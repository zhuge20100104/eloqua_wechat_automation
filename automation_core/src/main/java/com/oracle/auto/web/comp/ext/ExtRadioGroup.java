package com.oracle.auto.web.comp.ext;


import com.oracle.auto.web.comp.ComponentAdaptorBase;
import com.oracle.auto.web.exceptions.ComponentSetPropertyException;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

// xtype: radiogroup
public class ExtRadioGroup extends ExtComponentBase {
	public ExtRadioGroup(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
	// value
	public String getValue() {
		waitForReadyEnabled();
		String script = "var comp = " + locator + ";"
				+ "if (comp == undefined) return ''; "
				+ "var v = comp.getValue(); if (Object.keys(v).length <= 0) return '';"
				+ "return v[Object.keys(v)[0]];";
		return page.executeScriptEx(script).str();
	}
	
	protected String getValueKeyName() {
		return getMethodProp(".getValue()").str();
	}
	
	public void setValue(String value) {
		waitForReadyEnabled();
		setNoReturnMethodProp(".setValue(\"" + ComponentAdaptorBase.escape2JS(value) + "\")");
		
		if (!getValue().equals(value))
			throw new ComponentSetPropertyException(page,locator, "set value = " + value, "the raio box doesn't have the check box with this value");
	}
	

}
