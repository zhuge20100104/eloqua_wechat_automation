package com.oracle.auto.web.comp.html;


import com.oracle.auto.web.comp.interfaces.ISelectableList;
import com.oracle.auto.web.utility.WebDriverHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class HTMLDropDownList extends HTMLComponentBase implements ISelectableList {
    private String dropdownId;
    private Select select;

    private HashMap<Integer, String> indexItemMap = new LinkedHashMap<>();

    public HTMLDropDownList() { }

    public HTMLDropDownList(String locator) {
        super(locator);
        //wait for the page to load as we are executing a script on the page
        //  WebDriverHelper.waitForAngular(page.getDriver());
        //  this.dropdownId = page.executeScript("return BDD_GenId(" + locator + ")");
    }

    public HTMLDropDownList(String locator, String dropDownId) {
        super(locator);
        this.dropdownId = dropDownId;
    }

    private Select getSelectObject() {
        if (select == null)
            select = page.getSelectElement(String.format("id=%s", dropdownId));

//        Clicking on the drop-down here, as the data might not have been loaded some times, until it's expanded
        page.click(String.format("id=%s", dropdownId));
        page.waitForShort();
//        Closing as the data is now loaded
        page.click(String.format("id=%s", dropdownId));
        return select;
    }

    @Override
    public void select(String value) {
        waitForReady();
        getSelectObject().selectByVisibleText(value);
    }

    public void select(int index) {
        getSelectObject().selectByIndex(index);
    }

    @Override
    public int selectedIndex() {
        String selectedItem = this.selectedValue();
        return getSelectObject().getOptions().indexOf(selectedItem);
    }

    @Override
    public String selectedValue() {
        return getSelectObject().getFirstSelectedOption().getText().trim();
    }

    @Override
    public int getItemIndex(String value) {
        return this.getValues().indexOf(value);
    }

    // TODO: Re-visit this method. Parameter is not used
    @Override
    public int getItemCount(String value) {
        return indexItemMap.size() - 1;
    }

    @Override
    public boolean isItemExist(String value) {
        return this.getDisplayValues().contains(value);
    }

    @Override
    public int getCount() {
        return getSelectObject().getOptions().size();
    }

    @Override
    public List<String> getValues() {
        int totalValues = this.getCount();
        List<String> values = new ArrayList<>();
        List<WebElement> options = getSelectObject().getOptions();
        for (int i = 0; i < totalValues; i++) {
            values.add(options.get(i).getText().trim());
        }
        return values;
    }

    public void selectDisplayValue(String value) {
        select(value);
    }

    public List<String> getDisplayValues() {
        return this.getValues();
    }

}