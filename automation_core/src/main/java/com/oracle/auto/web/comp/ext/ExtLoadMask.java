package com.oracle.auto.web.comp.ext;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

// xtype: loadmask
public class ExtLoadMask extends ExtComponentBase {
	private static String allLoadMaskLocators = "BDD_ExtQuery('loadmask{isVisible()}')";
	
	// no wait
	public ExtLoadMask(WebDriverSeleniumPageEx page) {
		super(page, allLoadMaskLocators);
	}

	public ExtLoadMask() {
		this.locator = allLoadMaskLocators;
	}

	@Override
	public boolean isReady() {
		// if Ext is not loaded, then directly return true, no check loading
		if (!isExtLoaded()) return true;
		
		return !doIsLoading();  // if it's loading, will not be ready.
	}
	
	@Override
	public boolean isEnabled() {
		// because this message might not shown before, meanwhile, it's a array actually. no property.
		return true;
	}
	
	protected boolean doIsLoading() {
		return getMethodPropIgnoreUndefined(".length").AsInt () > 0;
	}
}
