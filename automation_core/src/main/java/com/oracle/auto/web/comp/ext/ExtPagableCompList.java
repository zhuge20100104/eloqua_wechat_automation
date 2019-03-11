package com.oracle.auto.web.comp.ext;

import com.oracle.auto.web.comp.interfaces.IComponent;
import com.oracle.auto.web.comp.interfaces.IPagableComponentList;

import java.util.List;

// no pagable list (only call the first page)
public class ExtPagableCompList extends ExtCompList implements IPagableComponentList {
	public ExtPagableCompList() {	}
	
	@Override
	public int getItemIndex(String value) {
		waitForReadyEnabled();		
		return super.getItemIndexInAnyPage(defaultStorePropName, value);
	}
	
	@Override
	public int getItemCount(String value) {
		waitForReadyEnabled();		
		return super.getCountInAllPage(defaultStorePropName, value);
	}
	
	@Override
	public boolean isItemExist(String value) {
		// call another public method
		return getItemIndex(value) >= 0;
	}
	
	@Override
	public int getCount() {
		waitForReadyEnabled();	
		return super.getTotalCountInAllPages();
	}
	
	@Override
	public List<String> getValues() {
		waitForReadyEnabled();	
		return super.getItemValuesInAllPages(defaultStorePropName);
	}
	
	@Override
	public IComponent getCompInCurrentPage(String value) {
		return getCompAsInCurrentPage(value, IComponent.class);
	}

	@Override
	public <T> T getCompAsInCurrentPage(String value, Class<T> classType) {
		if (item == null) throw new RuntimeException("there's no item configuraiton for the component list to generate");
		
		int index = getItemIndexInCurrentPage(value);
		return doGetCompAsByData(index, classType);
	}

	@Override
	public int getItemIndexInCurrentPage(String value) {
		return super.getItemIndex(value);
	}

	@Override
	public int getItemCountInCurrentPage(String value) {
		return super.getItemCount(value);
	}

	@Override
	public boolean isItemExistInCurrentPage(String value) {
		return getItemIndexInCurrentPage(value) >= 0;
	}

	@Override
	public List<String> getValuesInCurrentPage() {
		return super.getValues();
	}
	
}
