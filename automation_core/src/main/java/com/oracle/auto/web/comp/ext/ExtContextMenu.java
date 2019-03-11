package com.oracle.auto.web.comp.ext;

import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.comp.ComponentAdaptorBase;

// xtype: menu
public class ExtContextMenu extends ExtComponentBase {
	private static String contextMenuLocator = "BDD_ExtQuery('menu{isVisible()}')[0]";
	private static String redirectMenuItemRelativeLocator = ".ExtQuery('menuitem[actionType=%s][page=%s]{isVisible()}')[0]";
	private static String actionMenuItemRelativeLocator = ".ExtQuery('menuitem[actionType=%s]{isVisible()}')[0]";
	private static String customMenuItemRelativeLocator = ".ExtQuery(\"menuitem[%s='%s']{isVisible()}\")[0]";
	private static String indexMenuItemRelativeLocator = ".ExtQuery('menuitem{isVisible()}')[%s]";
			
	public ExtContextMenu(WebDriverSeleniumPageEx page) {
		super(page, contextMenuLocator);
	}
	
	public void clickMenuItem(String actionType, String pageType) {
		waitForReadyEnabled();

		clickSubElement(String.format(redirectMenuItemRelativeLocator, actionType, pageType) + ".itemEl.dom.click()");
	}
	
	public void clickMenuItem(String actionType) {
		waitForReadyEnabled();

		clickSubElement(String.format(actionMenuItemRelativeLocator, actionType) + ".itemEl.dom.click()");
	}

	public void clickMenuItemEx(String key, String value) {
		waitForReadyEnabled();

		clickSubElement(String.format(customMenuItemRelativeLocator, key, ComponentAdaptorBase.escape2JS(value)) + ".itemEl.dom.click()");
	}
	
	public void clickMenuItemByIndex(int index) {
		waitForReadyEnabled();

		clickSubElement(String.format(indexMenuItemRelativeLocator, index) + ".itemEl.dom.click()");
	}
	
	public boolean isMenuItemExist(String key, String value) {
		waitForReadyEnabled();
		
		return isSubElementReady(String.format(customMenuItemRelativeLocator, key, ComponentAdaptorBase.escape2JS(value)));
	}
	
	public void close() {
		waitForReadyEnabled();
		setNoReturnMethodProp(".close()");
	}
	
}
