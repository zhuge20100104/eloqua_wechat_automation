package com.oracle.auto.web.comp.ext;

import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
//xtype: datepicker
public class ExtDatePicker extends ExtDateField{
    private String todayButtonEl="todayBtn.btnEl";

    public ExtDatePicker(WebDriverSeleniumPageEx page, String locator) {
        super(page, locator);
    }

    public void clickTodayButton(){
        waitForReadyEnabled();
        clickSubElement(String.format(".%s.dom.click()", todayButtonEl));
    }


}
