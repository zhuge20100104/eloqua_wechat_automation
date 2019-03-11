package com.oracle.auto.web.comp.ext;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

// xtype: displayfield, but containing <a> for clicking
public class ExtValueDisplayLink extends ExtComponentClikableBase {
	private String el_click = "a";
	public ExtValueDisplayLink(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
	public void setClickElementTag(String el) {
		el_click = el;
	}
	
	public  void click() {
		waitForReadyEnabled();

		clickSubElement(".el.dom.getElementsByTagName('" + el_click + "')[0].click()");

	}

}
