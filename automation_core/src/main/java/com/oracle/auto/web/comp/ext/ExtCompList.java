package com.oracle.auto.web.comp.ext;

import com.google.gson.annotations.Expose;
import com.oracle.auto.web.comp.interfaces.IComponent;
import com.oracle.auto.web.comp.interfaces.IComponentList;
import com.oracle.auto.web.pages.object.ComponentData;
import com.oracle.auto.web.pages.object.ComponentLoader;

import java.util.List;

// no pagable list (only call the first page)
public class ExtCompList extends ExtPageList implements IComponentList {
	@Expose protected String defaultStorePropName = "name";
	@Expose protected ComponentData item;
	
	public ExtCompList() {
		super(null, "");
	}
	
	@Override
	public void setConfig(String jsonValue) {
		super.setConfig(jsonValue);
		
		if (item != null)
			item.replaceJsonFirstLevel("{parent}", locator);
	}
	
	@Override
	public int getItemIndex(String value) {
		waitForReadyEnabled();		
		return super.getItemIndexInCurrentPage(defaultStorePropName, value);
	}
	
	@Override
	public int getItemCount(String value) {
		waitForReadyEnabled();		
		return super.getCountInCurrentPage(defaultStorePropName, value);
	}
	
	@Override
	public boolean isItemExist(String value) {
		// call another public method
		return getItemIndex(value) >= 0;
	}
	
	@Override
	public int getCount() {
		waitForReadyEnabled();	
		return super.getCountInCurrentPage();
	}
	
	@Override
	public List<String> getValues() {
		waitForReadyEnabled();	
		return super.getItemValuesInCurrentPage(defaultStorePropName);
	}
	
	@Override
	public IComponent getComp(String value) {
		return getCompAs(value, IComponent.class);
	}
	
	protected <T> T doGetCompAsByData(int index, Class<T> classType) {
		if (index < 0) return null;
		ComponentData data = item.clone();
		data.replaceJsonFirstLevel("{index}", String.valueOf(index));
		
		return ComponentLoader.instance().getCompAsByData(data, page, classType);
	}


	@Override
	public <T> T getCompAs(String value, Class<T> classType) {
		if (item == null) throw new RuntimeException("there's no item configuraiton for the component list to generate");
		
		int index = getItemIndex(value);
		return doGetCompAsByData(index, classType);
	}
	
}
