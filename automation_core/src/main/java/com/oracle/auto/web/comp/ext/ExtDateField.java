package com.oracle.auto.web.comp.ext;

import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.utility.PropertyConfiger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
//xtype: datefield

public class ExtDateField extends ExtTextbox {
    private String triggerEl = "triggerEl.elements[0]";
    private String pickerFieldLocator = "BDD_ExtQuery('datepicker')[0]";

    private String dateFormat = PropertyConfiger.instance().getEnvData("dateField.format", "");

    public ExtDateField(WebDriverSeleniumPageEx page, String locator) {
        super(page, locator, true);
    }

    public void clickCalenderImage() {
        waitForReadyEnabled();
        clickSubElement(String.format(".%s.dom.click()", triggerEl));
    }

    public void selectTodayInDatePicker() {
        ExtDatePicker datePicker = new ExtDatePicker(page, pickerFieldLocator);
        datePicker.clickTodayButton();
    }

    public void validateDateField(String date) {
        try {
            SimpleDateFormat formatDateJava = new SimpleDateFormat(dateFormat);
            formatDateJava.setLenient(false);
            formatDateJava.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
