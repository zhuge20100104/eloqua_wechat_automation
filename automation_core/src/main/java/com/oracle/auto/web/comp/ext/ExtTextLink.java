package com.oracle.auto.web.comp.ext;


import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

// can be text?
public class ExtTextLink extends ExtComponentBase implements IClickableComponent {
	private String el_click = "a";
	public void setClickElementTag(String el) {
		el_click = el;
	}

	public ExtTextLink(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
	public ExtTextLink() {}

	
	public  void click() {
		waitForReadyEnabled();

		clickSubElement(".el.dom.getElementsByTagName('" + el_click + "')[0].click()");

	}

	public String getCaption() {
		waitForReadyEnabled();
		return getMethodProp(".el.dom.innerText").str();
	}
}
