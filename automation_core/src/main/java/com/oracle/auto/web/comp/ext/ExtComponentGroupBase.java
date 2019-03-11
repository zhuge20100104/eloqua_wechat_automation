package com.oracle.auto.web.comp.ext;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.comp.ComponentPanelBase;
import com.oracle.auto.web.comp.interfaces.IComponent;
import com.oracle.auto.web.comp.interfaces.IComponentGroup;

import java.util.List;

// add ext web element features
public class ExtComponentGroupBase extends ExtWebElement implements IComponentGroup {
	protected ComponentPanelBase panel;
	public ExtComponentGroupBase() {
		panel = new ComponentPanelBase();
	}
	
	@Override
	public void setPage(WebDriverSeleniumPageEx page) {
		super.setPage(page);
		panel.setPage(page);
	}

	@Override
	public void setConfig(String jsonValue) {
		super.setConfig(jsonValue);
		panel.setConfig(jsonValue);
	}
	
	@Override
	public boolean isReady() {
		return super.isReady() && panel.isReady();
	}
	
	@Override
	public boolean isEnabled() {
		return super.isEnabled() && panel.isEnabled();
	}
	
	@Override
	public void registerComp(IComponent... comps) {
		panel.registerComp(comps);		
	}

	@Override
	public void registerComp(String compName, IComponent comp) {
		panel.registerComp(compName, comp);
		
	}

	@Override
	public IComponent getComp(String compName) {
		return panel.getComp(compName);
	}

	@Override
	public List<IComponent> getComps() {
		return panel.getComps();
	}

	@Override
	public <T> T getCompAs(String compName, Class<T> classType) {
		return panel.getCompAs(compName, classType);
	}

}
