package com.oracle.auto.web.comp.ext;

import com.google.gson.annotations.Expose;
import com.oracle.auto.web.comp.ComponentAdaptorBase;
import com.oracle.auto.web.comp.interfaces.IPageble;
import com.oracle.auto.web.exceptions.ComponentGetPropertyException;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

// page may need refresh its data any time, in this case, always check status of ready before calling method.
// xtype: just Ext Store
public abstract class ExtPageList extends ExtComponentBase implements IPageble {
	@Expose private String storeName = ".store";

	public ExtPageList(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
	public ExtPageList(WebDriverSeleniumPageEx page, String locator, String storeName) {
		super(page, locator);
		if (!storeName.startsWith("."))
			this.storeName = "." + storeName;
		else 
			this.storeName = storeName;
	}
	
	public ExtPageList() {}
	
	@Override
	public void setConfig(String jsonValue) {
		super.setConfig(jsonValue);
				
		// ensure there's prefix
		if (!storeName.startsWith("."))
			this.storeName = "." + storeName;
	}
	
	protected boolean isloading() {
		return getMethodProp(storeName + ".loading").AsBool();
	}
	
	// also check if it's finished to load the store
	// since sometimes, the list is auto load but not always load itself at once due to some delay. 
	// if it's passed and there's no any data, we'd better wait for twice to check it again.
	private boolean delayReadyFlag = true;
	
	@Override
	public boolean isReady() {
		boolean ret =  super.isReady() && !isloading();
		if (ret && delayReadyFlag && doGetCountInCurrentPage() <= 0) {
			WebDriverSeleniumPageEx.waitFor(2);
			delayReadyFlag = false;
			return false;
		}
		
		return ret;
	}

	
	// from 1...N
	@Override
	public int getCurrentPageNumber() {
		waitForReadyEnabled();
		return getMethodProp(storeName + ".currentPage").AsInt();
	}

	public int getCountInCurrentPage() {
		waitForReadyEnabled();
		return doGetCountInCurrentPage();
	}
	
	protected int doGetCountInCurrentPage() {
		return getMethodProp(storeName + ".count()").AsInt();
	}

	public int getMaxCountInPage() {
		waitForReadyEnabled();
		return getMethodProp(storeName + ".pageSize").AsInt();
	}

	@Override
	public void nextPage() {
		waitForReadyEnabled();
		setNoReturnMethodProp(storeName + ".nextPage()");
		waitForReadyEnabled(); // need to wait until it's finish loading
	}

	@Override
	public void previousPage(){
		waitForReadyEnabled();
		int current = getCurrentPageNumber();
		if(current>1)
		{
			setNoReturnMethodProp(storeName + ".previousPage()");
			waitForReadyEnabled(); // need to wait until it's finish loading
		}
	}
	
	@Override
	public void firstPage() {
		while (getCurrentPageNumber() > 1)
			previousPage();
	}
	
	// from 1...
	@Override
	public void gotoPage(int pageNumber) {
		if (pageNumber != getCurrentPageNumber()) {
			setNoReturnMethodProp(storeName + ".loadPage(" + pageNumber + ")");
			waitForReadyEnabled(); // need to wait until it's finish loading
		}
	}
	
	public int getCountOnCurrentPage(){
		waitForReadyEnabled();
		return getMethodProp( ".getStore().getCount()").AsInt();
	}
	
	public int getTotalCountInAllPages() {
		waitForReadyEnabled();
		
		firstPage();
		
		int totalCount = 0;
		int currentCountInPage = 0;
		int maxCountPerPage = getMaxCountInPage();
		while(true) {
			// check current page
			currentCountInPage = getCountInCurrentPage();
			totalCount += currentCountInPage;
			
			// reach end page. exit
			if (currentCountInPage < maxCountPerPage) break;
			
			// not in the end, go to next page
			nextPage();
		}
		
		// recover to first page
		firstPage();

		return totalCount;
	}
	
	protected boolean isItemInCurrentPage(String rawPropName, String value) {
		return getItemIndexInCurrentPage(rawPropName, value) >= 0;
	}
	
	protected int getItemIndexInCurrentPage(String rawPropName, String value) {
		try {
			String script = String.format("var lst = %s.data.items; for (i=0; i<lst.length;i++) { if (lst[i].data.%s == '%s') return i;} return -1;",
					locator + storeName, rawPropName, ComponentAdaptorBase.escape2JS(value));
			return page.executeScriptEx(script).AsInt();
		} catch (Exception ex) {
			throw new ComponentGetPropertyException(page, locator, ".store.data.items[" + rawPropName + "]==" + value, ex);
		}
	}

	protected int getCountInCurrentPage(String rawPropName, String value) {
		try {
			String script = String.format("var lst = %s.data.items; var count = 0; for (i=0; i<lst.length;i++) { if (lst[i].data.%s == '%s') count++;} return count;",
					locator + storeName, rawPropName, ComponentAdaptorBase.escape2JS(value));
			return page.executeScriptEx(script).AsInt();
		} catch (Exception ex) {
			throw new ComponentGetPropertyException(page, locator, ".store.data.items[" + rawPropName + "]==" + value, ex);
		}
	}
	
	protected int getCountInAllPage(String rawPropName, String value) {
		waitForReadyEnabled();
		
		firstPage();
		
		int totalCount = 0;
		int currentCountInPage = 0;
		int maxCountPerPage = getMaxCountInPage();
		while(true) {
			// check current page
			currentCountInPage = getCountInCurrentPage(rawPropName, value);
			totalCount += currentCountInPage;
			
			// reach end page. exit
			if (currentCountInPage < maxCountPerPage) break;
			
			// not in the end, go to next page
			nextPage();
		}
		
		// recover to first page
		firstPage();

		return totalCount;		
	}
	
	private static String valuesSeperator = "\n--#%%#--\n--#%%#--\n";
	protected List<String> getItemValuesInCurrentPage(String rawPropName) {
		// note following JavaScript doesn't handle the case when there's only one empty value, we check count firstly.
		if (getCountInCurrentPage() <= 0) return new ArrayList<>();

		try {
			String script = String.format("var lst = %s.data.items; " +
					"var sep='%s'; var ret = undefined; " +
					"for (i=0; i<lst.length;i++) { " +
						"var value = lst[i].data.%s; " +
						"value = (value == undefined ? '' : value); " + 
						"ret = (ret == undefined ? value : ret + sep + value); " +
						"} return (ret == undefined ? '' : ret) ;",
					locator + storeName, ComponentAdaptorBase.escape2JS(valuesSeperator), rawPropName);
			String ret = page.executeScriptEx(script).str();
			return asList(ret.split(valuesSeperator));
		} catch (Exception ex) {
			throw new ComponentGetPropertyException(page, locator, ".store.data.items[" + rawPropName + "]", ex);
		}
	}

	protected List<String> getItemValuesInAllPages(String rawPropName) {
		firstPage();
		
		List<String> total = new ArrayList<>();
		List<String> ret = null;
		int maxCountPerPage = getMaxCountInPage();
		while(true) {
			// check current page
			ret = getItemValuesInCurrentPage(rawPropName);
			total.addAll(ret);

			// reach end page. exit
			if (getCountInCurrentPage() < maxCountPerPage) return total;
			// not found, go to next page
			nextPage();
		}
	}

	protected List<String> getItemSubValuesInCurrentPage(String rawPropName, String subRawPropName, int itemIndex) {
		// note following JavaScript doesn't handle the case when there's only one empty value, we check count firstly.
		if (getCountInCurrentPage() <= 0) return new ArrayList<>();

		try {
			String script = String.format("var lst = %s.data.items[%d].data.%s; " +
					"var sep='%s'; var ret = undefined; " +
					"for (i=0; i<lst.length;i++) { " +
						"var value = lst[i].%s; " +
						"value = (value == undefined ? '' : value); " + 
						"ret = (ret == undefined ? value : ret + sep + value); " +
						"} return (ret == undefined ? '' : ret) ;",
					locator + storeName, itemIndex, rawPropName, ComponentAdaptorBase.escape2JS(valuesSeperator), subRawPropName);
			String ret = page.executeScriptEx(script).str();
			return asList(ret.split(valuesSeperator));
		} catch (Exception ex) {
			throw new ComponentGetPropertyException(page,locator, ".store.data.items[" + rawPropName + "]", ex);
		}
	}
	
	protected String getItemValueInCurrentPage(String rawPropName, int itemIndex) {
		return getMethodPropIgnoreUndefined(String.format("%s.data.items[%d].data.%s", storeName, itemIndex, rawPropName)).str();
	}
	
	protected boolean isItemInAnyPage(String rawPropName, String value) {
		return getItemIndexInAnyPage(rawPropName, value) >= 0;
	}
	
	protected int getItemIndexInAnyPage(String rawPropName, String value) {
		int index = getItemIndexInCurrentPage(rawPropName, value);
		if (index >= 0) return index;
		
		firstPage();
		
		int maxCountPerPage = getMaxCountInPage();
		while(true) {
			// check current page
			index = getItemIndexInCurrentPage(rawPropName, value);
			// find
			if (index >= 0) return index; 
			// not found and reach end page. exit
			if (getCountInCurrentPage() < maxCountPerPage) return -1;
			// not found, go to next page
			nextPage();
		}
	}
	
	// find last item
	protected int getLastItemIndexInAnyPage(String rawPropName, String value) {
		// find first item as before, then goto next page to see if can get more, keep loop
		int itemIndex = getItemIndexInAnyPage(rawPropName, value);
		if (itemIndex < 0)
			return -1;

		int pageIndex = getCurrentPageNumber();
		int maxCountPerPage = getMaxCountInPage();
		int newItemIndex = -1;

		while(true) {
			// reach end
			if (getCountInCurrentPage() < maxCountPerPage) {
				// return last item and goto that page
				gotoPage(pageIndex);
				return itemIndex;
			}
			
			// next page
			nextPage();
			
			// check current page
			newItemIndex = getItemIndexInCurrentPage(rawPropName, value);
			// find
			if (newItemIndex >= 0) {
				// refresh last item's position
				itemIndex = newItemIndex;
				pageIndex = getCurrentPageNumber();
			}
			
		}
		
	}
}
