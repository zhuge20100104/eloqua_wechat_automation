package com.oracle.auto.web.comp.ext;


import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

public abstract class ExtComponentClikableBase extends ExtComponentBase implements IClickableComponent {
	abstract public  void click();
	
	public ExtComponentClikableBase(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
}
