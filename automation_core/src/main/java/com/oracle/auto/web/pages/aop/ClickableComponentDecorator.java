package com.oracle.auto.web.pages.aop;


import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

public class ClickableComponentDecorator implements IClickableComponentDecorator {

	IClickableComponent comp = null;
	@Override
	public void setComponent(IClickableComponent comp) {
		this.comp = comp;
	}
	
	@Override
	public void click() {
		comp.click();
	}

	@Override
	public boolean isEnabled() {
		return comp.isEnabled();
	}

	@Override
	public boolean isReady() {
		return comp.isReady();
	}

	@Override
	public String getLastError() {
		return comp.getLastError();
	}

	@Override
	public void waitForElementReady(int timeout) {
		comp.waitForElementReady(timeout);
	}

	@Override
	public void waitForElementEnabled(int timeout) {
		comp.waitForElementEnabled(timeout);
	}

	@Override
	public void setPage(WebDriverSeleniumPageEx page) {
		comp.setPage(page);
	}

	@Override
	public WebDriverSeleniumPageEx getPage() {
		// TODO Auto-generated method stub
		return comp.getPage();
	}

	@Override
	public void setConfig(String jsonValue) {
		comp.setConfig(jsonValue);
	}



	@Override
	public boolean hideDefault() {
		return comp.hideDefault();
	}
}
