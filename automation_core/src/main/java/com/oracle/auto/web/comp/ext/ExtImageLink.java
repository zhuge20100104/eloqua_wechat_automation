package com.oracle.auto.web.comp.ext;

import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
// xtype: imagecomponent
public class ExtImageLink  extends ExtComponentBase implements IClickableComponent {
	public ExtImageLink(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
	public ExtImageLink() {}
	
	public  void click() {
		waitForReadyEnabled();

		clickSubElement(".imgEl.dom.click()");

	}
	
}
