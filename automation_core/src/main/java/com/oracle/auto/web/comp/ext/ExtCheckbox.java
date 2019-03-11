package com.oracle.auto.web.comp.ext;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

// xtype: checkbox
public class ExtCheckbox extends ExtComponentClikableBase {
	public ExtCheckbox(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
	
	public  void click() {
		waitForReadyEnabled();
		doClick();
	}
	
	private void doClick() {
		clickSubElement(".inputEl.dom.click()");
	}
	private boolean doGetChecked() {
		return getMethodProp(".getValue()").AsBool();
	}
	
	public boolean isChecked() {
		waitForReadyEnabled();
		return doGetChecked();
	}
	
	public void setValue(boolean value) {
		if (value)
			select();
		else unselect();
	}

	public void select() {
		waitForReadyEnabled();
		if (!doGetChecked())
			doClick();
	}
	
	public void unselect() {
		waitForReadyEnabled();
		if (doGetChecked())
			doClick();
	}
}
