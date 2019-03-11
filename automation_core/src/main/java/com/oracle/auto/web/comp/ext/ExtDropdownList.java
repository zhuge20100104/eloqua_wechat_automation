package com.oracle.auto.web.comp.ext;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.google.gson.annotations.Expose;
import com.oracle.auto.web.comp.interfaces.ISelectableList;
import com.oracle.auto.web.exceptions.ComponentSetPropertyException;

import java.util.List;

// xtype: combo
public class ExtDropdownList extends ExtPageList implements ISelectableList {
	@Expose private String displayValuePropRawName = "";
	@Expose private String valuePropRawName = "";
	
	public ExtDropdownList() {
		
	}
	
	public ExtDropdownList(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
	// display value
	public String getDisplayValue() {
		waitForReadyEnabled();
		return getMethodProp(".getDisplayValue()").str();
	}
	
	private String getDisplayPropRawName() {
		if (displayValuePropRawName.isEmpty()) {
			waitForReadyEnabled();
			displayValuePropRawName = getMethodProp(".displayField").str();
			if (displayValuePropRawName.isEmpty())
				throw new ComponentSetPropertyException(page, locator, "displayField", "cannot get the dropdown list display field");
		}
		
		return displayValuePropRawName;
	}
	
	private String getValuePropRawName() {
		if (valuePropRawName.isEmpty()) {
			waitForReadyEnabled();
			valuePropRawName = getMethodProp(".valueField").str();
			if (valuePropRawName.isEmpty())
				throw new ComponentSetPropertyException(page, locator, "valueField", "cannot get the dropdown list value field");
		}
		
		return valuePropRawName;
	}
	
	public void selectDisplayValue(String text) {
		waitForReadyEnabled();
		
		// for empty, directly set it.
		if (text.isEmpty()) {
			setNoReturnMethodProp(".select('')");
			
			// still need to check it
			String realValue = getDisplayValue();
			if (realValue.equals(text)) return;
			
			// if failed, need to use traditional issue, 
		}

		// get index by displayed text
		int index = getItemIndexInCurrentPage(getDisplayPropRawName(), text);
		if (index < 0)
			throw new ComponentSetPropertyException(page,locator, "selectText", "text [" + text + "] is not on the dropdown list");
		
		// get value of the index
		String value = getItemValueInCurrentPage(getValuePropRawName(), index);
		
		// call select passing value only (doesn't support passing display value)
		setNoReturnMethodProp(".select('" + escape2JS(value) + "')");
		
		String realValue = getDisplayValue();
		if (realValue.equals(text)) return;
		
		// method2
		// in some case (like Beacon checklist item reviewer list) the select() method doens't work. so need to use other ways.
		String inputId = getMethodProp(".inputEl.id").str();
		page.sendkeys(inputId, text);
		page.sendEnterKeysById(inputId);

		// check again
		realValue = getDisplayValue();
		if (!realValue.equals(text)) 
			throw new ComponentSetPropertyException(page,locator, "selectText", "fail to set dropdown list text as [" + text + "]");
		
		
	}
	
	public List<String> getDisplayValues() {
		waitForReadyEnabled();
		return getItemValuesInCurrentPage(getDisplayPropRawName());
	}
	
	public boolean isDisplayValueInList(String text) {
		waitForReadyEnabled();
		return isItemInCurrentPage(getDisplayPropRawName(), text);
	}


	@Override
	public int getItemIndex(String value) {
		return super.getItemIndexInCurrentPage(getDisplayPropRawName(), value);
	}
	

	@Override
	public int getItemCount(String value) {
		return super.getCountInCurrentPage(getDisplayPropRawName(), value);
	}
	

	@Override
	public boolean isItemExist(String value) {
		return isDisplayValueInList(value);
	}

	@Override
	public int getCount() {
		return super.getCountInCurrentPage();

	}
	

	@Override
	public List<String> getValues() {
		return getDisplayValues();
	}

	@Override
	public void select(String value) {
		selectDisplayValue(value);
	}

	@Override
	public int selectedIndex() {
		String value = selectedValue();
		List<String> values = getValues();
		int size = values.size();
		for (int i=0; i<size; ++i) {
			if (value.equals(values.get(i)))
				return i;
		}
		return -1;
	}

	@Override
	public String selectedValue() {
		return getDisplayValue();
	}
	
}