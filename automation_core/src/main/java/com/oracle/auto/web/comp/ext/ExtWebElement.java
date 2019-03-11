package com.oracle.auto.web.comp.ext;

import com.oracle.auto.web.comp.interfaces.IElementFinder;
import org.openqa.selenium.WebElement;


public class ExtWebElement extends ExtComponentBase implements IElementFinder {
	public WebElement getElment() {
		String id = getMethodProp(".el.id").str();
		WebElement el = page.findElementById(id);
		return el;
	}
	
	public String getElementValueByClassName(String className) {
		return getElementValueByClassName(className, 0);
	}

	public String[] getElementValuesByClassName(String className) {
		String propLocator = String.format(".el.dom.getElementsByClassName('%s').length", escape2JS(className));
		int count = getMethodProp(propLocator).AsInt();
		if (count <= 0) return new String[] {};
		
		String[] ret = new String[count];
		for (int i = 0; i < count; ++i) {
			ret[i] = getElementValueByClassName(className, i);
		}
		
		return ret;
	}
	
	public String getElementValueByClassName(String className, int index) {
		String propLocator = String.format(".el.dom.getElementsByClassName('%s')[%d].innerText", escape2JS(className), index);
		return getMethodProp(propLocator).str();
	}

	public void clickElementValueByClassName(String className) {
		clickElementValueByClassName(className, 0);
	}
	
	public void clickElementValueByClassName(String className, int index) {
		String clickLocator = String.format(".el.dom.getElementsByClassName('%s')[%d].click()", escape2JS(className), index);
		clickSubElement(clickLocator);
	}
}
