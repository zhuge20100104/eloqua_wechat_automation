package com.oracle.auto.web.pages.object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ReadonlyPage extends PageBase {

    /**
     * check if there any visible text box input
     *
     * @return true if no HTML input displayed
     */
    public boolean hasVisibleTextBox() {
        boolean isVisbile = false;
        List<WebElement> eles = page.getDriver().findElements(By.cssSelector("input[type='text']"));
        for (WebElement element : eles) {
            if (element.isDisplayed()) {
                isVisbile = true;
                break;
            }
        }
        return isVisbile;
    }
    
    /**
     * check if there any visible text box input
     *
     * @return true if no HTML input displayed
     */
    public boolean hasSpecificVisibleTextBoxById(String id) {
        boolean isVisbile = false;
        List<WebElement> eles = page.getDriver().findElements(By.id(id));
        for (WebElement element : eles) {
            if (element.isDisplayed()) {
                isVisbile = true;
                break;
            }
        }
        return isVisbile;
    }

    /**
     * check if there is any drop down selection
     *
     * @return true is no HTML selection displayed
     */
    public boolean hasVisibleDropDown() {
        boolean isVisbile = false;
        List<WebElement> eles = page.getDriver().findElements(By.tagName("select"));
        for (WebElement element : eles) {
            if (element.isDisplayed()) {
                isVisbile = true;
                break;
            }
        }
        return isVisbile;
    }
    
    /**
     * check if there is any drop down selection
     *
     * @return true is no HTML selection displayed
     */
    public boolean hasSpecificVisibleDropDownById(String id) {
        boolean isVisbile = false;
        List<WebElement> eles = page.getDriver().findElements(By.id(id));
        for (WebElement element : eles) {
            if (element.isDisplayed()) {
                isVisbile = true;
                break;
            }
        }
        return isVisbile;
    }

}
