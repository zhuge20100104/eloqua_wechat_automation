package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import com.oracle.auto.web.comp.interfaces.ITable;
import com.oracle.auto.web.exceptions.ComponentGetPropertyException;
import com.oracle.auto.web.utility.WebDriverHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HTMLTable extends HTMLComponentBase implements ITable {
    private boolean isHeaderPresent = true;


    public HTMLTable(String locator, boolean isHeaderPresent) {
        super(locator);
        this.isHeaderPresent = isHeaderPresent;
    }

    //
    // assert
    // utility method
    //
    protected void assertRowIndexCellIndex(int rowIndex, int visibleColIndex) {
        assertRowIndex(rowIndex);
        assertColIndex(visibleColIndex);
    }

    private void assertColIndex(int visibleColIndex) {
        int count = getColCount();
        if (visibleColIndex < 0 || visibleColIndex > getColCount())
            throw new ComponentGetPropertyException(page, locator, "get Column", "trying to get header text of column " + visibleColIndex + ", but there's only " + count + " columns.");
    }

    protected void assertRowIndex(int rowIndex) {
        int count = getCountInCurrentPage();
        if (rowIndex < 0 || rowIndex > count)
            throw new ComponentGetPropertyException(page, locator, "get Row", "trying to access row index " + rowIndex + ", but there's only " + count + " rows.");
    }


    @Override
    public int getColCount() {
        return getMethodProp(".find('tbody').find('tr')[0].cells.length").AsInt();

    }

    @Override
    public int getCountInCurrentPage() {
        return getMethodProp(".find('tbody').find('tr').length").AsInt();
    }

    @Override
    public String getColHeaderText(int colIndex) {
        assertColIndex(colIndex);
        return getMethodProp(".find('thead').find('tr').find('th')[" + colIndex + "].textContent").str();
    }


    @Override
    public boolean isItemInCurrentPage(int colIndex, String value) {
        for (int i = 0; i < getCountInCurrentPage(); i++) {
            if (getItemValueInCurrentPage(i, colIndex).equals(value))
                return true;
        }
        return false;
    }

    public boolean isItemInCurrentPageRegexMatch(int colIndex, String value) {
        for (int i = 0; i < getCountInCurrentPage(); i++) {
            if (getItemValueInCurrentPage(i, colIndex).matches(value))
                return true;
        }
        return false;
    }

    @Override
    public int getItemIndexInCurrentPage(int colIndex, String value) {
        WebDriverHelper.waitForAngular(page.getDriver());
        for (int i = 0; i < getCountInCurrentPage(); i++) {
            String tableValue = getItemValueInCurrentPage(i, colIndex);
            if (tableValue.toLowerCase().equals(value.toLowerCase()) || tableValue.equals(value))
                return i;
        }
        return -1;
    }

    @Override
    public List<String> getItemValuesInCurrentPage(int colIndex) {
        List<String> itemValues = new ArrayList<String>();
        for (int i = 0; i < getCountInCurrentPage(); i++) {
            itemValues.add(getItemValueInCurrentPage(i, colIndex));

        }
        return itemValues;
    }

    @Override
    public List<String> getDisplayedStringsByRow(int rowIndex) {
        List<String> itemValues = new ArrayList<String>();
        for (int j = 0; j < getColCount(); j++) {
            itemValues.add(getItemValueInCurrentPage(rowIndex, j));
        }
        return itemValues;
    }


    @Override
    public void selectRow(int rowIndex) {
        assertRowIndex(rowIndex);
        setNoReturnMethodProp(".find('tbody tr').eq('" + rowIndex + "').click()");
    }

    @Override
    public void unSelectAll() {
        setNoReturnMethodProp(".removeClass('highlight')");
    }

    @Override
    public void contextClickRow(int rowIndex) {
        //  not needed for angular
    }

    @Override
    public void clickRowCellLink(int rowIndex, int colIndex) {
        assertRowIndexCellIndex(rowIndex, colIndex);
        setNoReturnMethodProp(String.format(".find('tbody tr').eq('%s').find('td').eq('%s').click()", rowIndex, colIndex));
    }

    @Override
    public void doubleClickRowCell(int rowIndex, int colIndex) {
        //  not needed for beacon NG angular
    }

    @Override
    public void contextClickRow(int rowIndex, int colIndex) {
        //  not needed for beacon NG angular
    }

    @Override
    public String getItemValueInCurrentPage(int rowIndex, int colIndex) {
        return getMethodProp(String.format(".find('tbody').find('tr').eq(%s).find('td').eq(%s).text().trim()", rowIndex, colIndex)).str();
    }


    public int getTotalCountInAllPages() {
        return getCountInCurrentPage();
    }


    public int getItemIndexInAnyPage(int columnIndex, String value) {
        return getItemIndexInCurrentPage(columnIndex, value);
    }


    public int getCountInAllPages(String value) {
        int count = 0;
        for (int i = 0; i < getCountInCurrentPage(); i++) {
            for (int j = 0; j < getColCount(); j++) {
                if (getItemValueInCurrentPage(i, j).equals(value))
                    count++;
            }
        }
        return count;
    }

    public void clickCellElement(int rowIndex, int colIndex) {
        clickCellElement(rowIndex, colIndex, null);
    }

    public void clickCellElement(int rowIndex, int colIndex, String buttonId) {
        String selector = ".find('tbody').find('tr').eq(%s).find('td').eq(%s)";
        if (buttonId != null)
            selector += ".find('#" + buttonId + "')";
        runNoReturnMethod(String.format(selector + ".click()", rowIndex, colIndex));
    }

    public void clickCellElementBySelector(int rowIndex, int colIndex, String buttonSelector) {
        String selector = ".find('tbody').find('tr').eq(%s).find('td').eq(%s)";
        if (buttonSelector != null)
            selector += ".find('" + buttonSelector + "')";
        runNoReturnMethod(String.format(selector + ".click()", rowIndex, colIndex));
    }

    public void clickElementInCell(int rowIndex, int colIndex, IClickableComponent component) {
        component.click();
    }

    public List<String> getAvailableColumnsAsList() {
        List<String> columns = new ArrayList<String>();

        int colCount = this.getColCount();
        for (int i = 0; i < colCount; i++)
            columns.add(this.getColHeaderText(i).replaceAll("\u00A0", ""));

        return columns;
    }

    public String getHeaderValueForColumn(int colIndex) {
        return getMethodProp(String.format(".find('thead').find('tr').find('th').eq(%s).text().trim()", colIndex)).str();
    }
}
