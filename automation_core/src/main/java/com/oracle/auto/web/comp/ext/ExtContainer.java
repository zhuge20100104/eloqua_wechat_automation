/**
 * 
 */
package com.oracle.auto.web.comp.ext;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

public class ExtContainer extends ExtComponentBase {

	public ExtContainer(WebDriverSeleniumPageEx page, String locator) {
		this.page = page;
		this.locator = locator;
	}

}
