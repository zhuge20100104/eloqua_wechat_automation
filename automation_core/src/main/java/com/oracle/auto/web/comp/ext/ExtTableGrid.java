package com.oracle.auto.web.comp.ext;


import com.oracle.auto.web.comp.ComponentAdaptorBase;
import com.oracle.auto.web.comp.interfaces.IPagableTable;
import com.oracle.auto.web.exceptions.ComponentClickException;
import com.oracle.auto.web.exceptions.ComponentGetPropertyException;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

// xtype: grid
public class ExtTableGrid extends ExtPageList implements IPagableTable {

	private boolean showHeader = true;
	
	public ExtTableGrid(WebDriverSeleniumPageEx page, String locator, boolean showHeader) {
		super(page, locator);
		this.showHeader = showHeader;
	}

	// utiliyt
	// column related
	// return visible column count
	public int getColCount() {
		waitForReadyEnabled();

		try {
			String script = String.format("var lst = %s; var colCount = 0; for (var i = 0; i < lst.columns.length; ++ i) { if (lst.columns[i].width <= 1) continue; colCount++; } return colCount; ",
						locator);

			return page.executeScriptEx(script).AsInt();
		} catch (Exception ex) {
			throw new ComponentGetPropertyException(page,locator, "get column count", ex);
		}
	}
	
	// -1 means exceeds max column count
	private int mapVisibleColToRealCol(int visibleColIndex) {
		try {
			// exclude any column that width <= 1
			String script = String.format("var lst = %s; var col = %d; for (var i = 0; i < lst.columns.length; ++ i) { if (lst.columns[i].width <= 1) continue; if (col-- <= 0) return i; } return -1; ",
						locator, visibleColIndex);

			return page.executeScriptEx(script).AsInt();
		} catch (Exception ex) {
			throw new ComponentGetPropertyException(page,locator, "get column count", ex);
		}
	}
	
	// 
	// assert
	// utility method
	//
	private void assertRowIndexCellIndex(int rowIndex, int visibleColIndex) {
		assertRowIndex(rowIndex);
		assertColIndex(visibleColIndex);
	}
	
	private void assertColIndex(int visibleColIndex) {
		int count = getColCount();
		if (visibleColIndex < 0 || visibleColIndex >= getColCount())
			throw new ComponentGetPropertyException(page, locator, "get Column", "trying to get header text of column " + visibleColIndex + ", but there's only " + count + " columns.");
	}
	private void assertRowIndex(int rowIndex) {
		int count = getCountInCurrentPage();
		if (rowIndex < 0 || rowIndex >= count) 
			throw new ComponentGetPropertyException( page,locator, "get Row", "trying to access row index " + rowIndex + ", but there's only " + count + " rows.");
	}

	// 
	// click
	// utility
	protected void clickCellElement(int rowIndex, int visibleColIndex, String eleType) {
		clickCellElement(rowIndex, visibleColIndex, eleType, 0);		
	}

	protected void clickCellElement(int rowIndex, int visibleColIndex, String eleType, int eleIndex) {
		// select the row (so that we can get its intern
		selectRow(rowIndex);
		
		// get real colmun index
		int colIndex = mapVisibleColToRealCol(visibleColIndex);

		// click on the selected element's certain cell
		clickSubElement(String.format(".view.getSelectedNodes()[0].cells[%d].getElementsByTagName('%s')[%d].click()", colIndex, ComponentAdaptorBase.escape2JS(eleType), eleIndex));
	}
	
	protected int getCellElementCount(int rowIndex, int visibleColIndex, String eleType) {
		// select the row (so that we can get its intern
		selectRow(rowIndex);
		
		// get real colmun index
		int colIndex = mapVisibleColToRealCol(visibleColIndex);

		return getMethodProp(String.format(".view.getSelectedNodes()[0].cells[%d].getElementsByTagName('%s').length", colIndex, ComponentAdaptorBase.escape2JS(eleType))).AsInt();
	}

	
	//
	// xpath
	// xpath related
	protected String getViewId()
	{
		return getMethodProp(".view.id").str();
	}
	
	// xpath for cell
	protected String getXPathForCell(int rowIndex, int visibleColIndex) {
		int colIndex = mapVisibleColToRealCol(visibleColIndex);

		// note, won't check if there's other selection, will directly select the rwoIndex
		// (maybe there already some row selected)
		// note: tr[row] starts from 2 (if header is shown)
		String xpath = String.format("//*[@id=\"%s\"]/table/tbody/tr[%d]/td[%d]", ComponentAdaptorBase.escape2JS(getViewId()), rowIndex + (showHeader ? 2 : 1), colIndex+1);
		
		return xpath;
	}
	
	// 
	// utility
	// content related
	protected WebDriverSeleniumPageEx.ScriptExecuteResult doGetDisplayedString(int rowIndex, int visibleColIndex) {
		int colIndex = mapVisibleColToRealCol(visibleColIndex);

		return getMethodProp(String.format(".view.body.dom.rows[%d].cells[%d].innerText.trim()", rowIndex, colIndex));
	}
	
	// return visible column count
	public String getColHeaderText(int visibleColIndex) {
		waitForReadyEnabled();
		assertColIndex(visibleColIndex);

		int colIndex = mapVisibleColToRealCol(visibleColIndex);
		return getMethodProp(".columns[" + colIndex + "].text").str();
	}
	
	// click visible column index
	public void clickRowCellLink(int rowIndex, int visibleColIndex) {
		waitForReadyEnabled();
		assertRowIndexCellIndex(rowIndex, visibleColIndex);

		clickCellElement(rowIndex, visibleColIndex, "a");
	}
	

	public void doubleClickRowCell(int rowIndex, int visibleColIndex) {
		waitForReadyEnabled();
		assertRowIndexCellIndex(rowIndex, visibleColIndex);
	
		String xpath = getXPathForCell(rowIndex, visibleColIndex);
		try {
			page.doubleClick(xpath);
		} catch (Exception ex) {
			throw new ComponentClickException(page,locator + xpath, ex);
		}
	}

	public void contextClickRow(int rowIndex, int visibleColIndex) {
		waitForReadyEnabled();
		assertRowIndexCellIndex(rowIndex, visibleColIndex);
		
		String xpath = getXPathForCell(rowIndex, visibleColIndex);

		try {
			page.contextClick(xpath);
		} catch (Exception ex) {
			throw new ComponentClickException(page,locator + xpath, ex);
		}
	}
	

	public String getItemValueInCurrentPage(int rowIndex, int visibleColIndex) {
		waitForReadyEnabled();
		assertRowIndexCellIndex(rowIndex, visibleColIndex);
		
		return doGetDisplayedString(rowIndex, visibleColIndex).str();
	}
	
	//
	// column related operations
	//
	
	public boolean isItemInCurrentPage(int visibleColIndex, String value) {
		return getItemIndexInCurrentPage(visibleColIndex, value) >= 0;
	}
	
	public boolean isItemInAnyPage(int visibleColIndex, String value) {
		return getItemIndexInAnyPage(visibleColIndex, value) >= 0;
	}
	
	
	public int getItemIndexInCurrentPage(int visibleColIndex, String value) {
		waitForReadyEnabled();		
		assertColIndex(visibleColIndex);
		
		int colIndex = mapVisibleColToRealCol(visibleColIndex);

		try {
			// support regular expression match
			String script = "";
			if (value.startsWith("(") && value.endsWith(")")) {
				value = "/" + value.substring(1, value.length()-1) + "/";
				script = String.format("var lst = %s.view.body.dom.rows; for (i=0; i<lst.length;i++) { var match=lst[i].cells[%d].innerText.trim().match(%s); if (match != undefined && match.length >= 1) return i;} return -1;",
						locator, colIndex, value); // no escape the value
				
			} else {
				script = String.format("var lst = %s.view.body.dom.rows; for (i=0; i<lst.length;i++) { if (lst[i].cells[%d].innerText.trim() == '%s') return i;} return -1;",
						locator, colIndex, ComponentAdaptorBase.escape2JS(value));
			}

			return page.executeScriptEx(script).AsInt();
		} catch (Exception ex) {
			throw new ComponentGetPropertyException(page,locator, ".view.body.dom.rows.cells[" + colIndex + "]==" + value, ex);
		}
	}

	public int getItemIndexInAnyPage(int visibleColIndex, String value) {
		waitForReadyEnabled();
		assertColIndex(visibleColIndex);

		int index = getItemIndexInCurrentPage(visibleColIndex, value);
		if (index >= 0) return index;
		
		firstPage();
		
		int maxCountPerPage = getMaxCountInPage();
		while(true) {
			// check current page
			index = getItemIndexInCurrentPage(visibleColIndex, value);
			// find
			if (index >= 0) return index; 
			// not found and reach end page. exit
			if (getCountInCurrentPage() < maxCountPerPage) return -1;
			// not found, go to next page
			nextPage();
		}
	}

	private static String valuesSeperator = "\n--#%%#--\n--#%%#--\n";
	public List<String> getItemValuesInCurrentPage(int visibleColIndex) {
		waitForReadyEnabled();
		assertColIndex(visibleColIndex);
		
		int colIndex = mapVisibleColToRealCol(visibleColIndex);

		try {
			String script = String.format("var lst = %s.view.body.dom.rows; " +
					"var sep='%s'; var ret = undefined; " +
					"for (i=0; i<lst.length;i++) { " +
						"var value = lst[i].cells[%d].innerText.trim(); " +
						"ret = (ret == undefined ? value : ret + sep + value); " +
						"} return (ret == undefined ? '' : ret) ;",
					locator, ComponentAdaptorBase.escape2JS(valuesSeperator), colIndex);
			String ret = page.executeScriptEx(script).str();
			return asList(ret.split(valuesSeperator));
		} catch (Exception ex) {
			throw new ComponentGetPropertyException(page,locator, "all column datas, col index = " + colIndex, ex);
		}
	}

	public List<String> getItemValuesInAllPage(int visibleColIndex) {
		waitForReadyEnabled();
		assertColIndex(visibleColIndex);
		
		List<String> results = new ArrayList<String>();
		firstPage();
		
		int maxCountPerPage = getMaxCountInPage();
		while(true) {
			// check current page
			results.addAll(getItemValuesInCurrentPage(visibleColIndex));

			// reach end page. exit
			if (getCountInCurrentPage() < maxCountPerPage) break;
			
			// go to next page
			nextPage();
		}
		
		return results;
	}

	//
	// row based operations
	//
	public void selectRow(int rowIndex) {
		waitForReadyEnabled();
		
		assertRowIndex(rowIndex);
		
		setNoReturnMethodProp(".view.select(" + rowIndex + ")");
	}
	
	public void unSelectAll() {
		waitForReadyEnabled();
		setNoReturnMethodProp(".view.select(" + -1 + ")");
		
	}

	public void contextClickRow(int rowIndex) {
		contextClickRow(rowIndex, 0);
	}
	
	public List<String> getDisplayedStringsByRow(int rowIndex) {
		waitForReadyEnabled();
		int count = getCountInCurrentPage();
		if (rowIndex < 0 || rowIndex >= count)
			throw new ComponentGetPropertyException(page,locator, "get Row", "trying to access row" + rowIndex + ", but there's only " + count + " rows.");

		List<String> list = new ArrayList<String>();
		count = getColCount();
		for (int i = 0; i < count; ++i) {
			list.add(doGetDisplayedString(rowIndex, i).str());
		}
		return list;
	}
	
    /**
     * This method returns the list of columns in a grid.
     * @return list of columns
     */
    public List<String> getAvailableColumnsAsList(){
        List<String> columns=new ArrayList<String>();

        int colCount = this.getColCount();
        for(int i=0;i<colCount;i++)
            columns.add(this.getColHeaderText(i));

        return columns;
    }

}

