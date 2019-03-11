package com.oracle.auto.web.comp.ext;

import com.google.gson.annotations.Expose;
import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

// xtyp: button
public class ExtButton extends ExtComponentBase implements IClickableComponent {
	@Expose protected String btnEl = "btnEl";
	public ExtButton(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
	public ExtButton() {}
	
	public  void click() {
		waitForReadyEnabled();
		clickSubElement(String.format(".%s.dom.click()", btnEl));
	}
}
