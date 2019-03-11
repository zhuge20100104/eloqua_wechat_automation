package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.utility.WebDriverHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class HTMLButton extends HTMLComponentBase implements IClickableComponent {

    public HTMLButton(String locator) {
        super(locator);
    }

    public HTMLButton() {
    }

    public void click() {
        // waitForReadyEnabled();
        waitForReady();
        doClick();
    }

    public String getLabelText() {
        // waitForReadyEnabled();
        waitForReady();
        return page.getValue(locator);
    }

    public void clickByScript() {
        WebDriverHelper.waitForAngular(page.getDriver());
        WebElement element = page.getDriver().findElement(WebDriverSeleniumPageEx.parseBy(locator));
        ((JavascriptExecutor) page.getDriver()).executeScript("arguments[0].click();", element);
    }

    public String getAttribute(String attribute) {
        waitForReady();
        return doGetAttribute(attribute);
    }

    public boolean attributePresent(String attribute) {
        waitForReady();
        return hasAttribute(attribute);
    }

    //This method is needed, when we have multiple elements having the same id.
    // we use jquery to click such elements

    public void clickByJQuery() {
        runNoReturnMethod(".click()");
    }

}
