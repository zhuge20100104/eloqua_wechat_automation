package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.ComponentAdaptorBase;
import com.oracle.auto.web.comp.interfaces.IComponent;
import com.oracle.auto.web.exceptions.ComponentNotEnabledException;
import com.oracle.auto.web.exceptions.ComponentNotReadyException;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.EntityEnclosingRequestWrapper;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;


import java.awt.*;

public class HTMLComponentBase extends ComponentAdaptorBase {
    private static Logger log = Logger.getLogger(HTMLComponentBase.class);


    public HTMLComponentBase(String locator) {
        this.locator = locator;
        this.rawLocator = locator;
        this.idLocator = locator;
    }

    public HTMLComponentBase() {
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [locator=" + locator + "]";
    }

    // if disabled
    @Override
    public boolean isEnabled() {
        generateIDLocator();
        boolean ret = page.isEditable(idLocator);
        if (!ret)
            doSetLastError("element [" + idLocator + "] is not enabled.");
        return ret;
    }

    // isReady for read and write: basic Element is loaded, then element present,
    // rendered, visible and valid
    @Override
    public boolean isReady() {
        // if element is empty, return
        try {
            if (locator.startsWith("BDD_")) {
                if (StringUtils.isEmpty(page.executeScript("return " + locator)))
                return false;

                String str = page.executeScript("return BDD_GenId(" + locator + ")");
                idLocator = "id=" + str;
            }
            return isPresent() && isVisible();
        } catch (Exception e) {
            // just return false in case of exception
            return false;
        }
    }

    private void generateIDLocator() {
        if (locator.startsWith("BDD_")) {
            if (idLocator != null || StringUtils.isEmpty(page.executeScript("return " + locator)))
                return;
            String str = page.executeScript("return BDD_GenId(" + locator + ")");
            idLocator = "id=" + str;
        }
    }

    @Override
    public void waitForElementReady(int timeout) {
        try {
            super.waitForElementReady(timeout);
        } catch (RuntimeException ex) {
            throw new ComponentNotReadyException(page, locator, ex);
        }
    }

    @Override
    public void waitForElementNotReady(int timeout) {
        try {
            super.waitForElementNotReady(timeout);
        } catch (RuntimeException ex) {
            throw new ComponentNotReadyException(page, locator, ex);
        }
    }

    @Override
    public void waitForElementEnabled(int timeout) {
        try {
            super.waitForElementEnabled(timeout);
        } catch (RuntimeException ex) {
            throw new ComponentNotEnabledException(page, locator, ex);
        }
    }

    @Override
    public boolean isPresent() {
        generateIDLocator();
        boolean ret = page.isElementPresent(idLocator);
        if (!ret) {
            log.debug("component [" + locator + "] doesn't present on page.");
            doSetLastError(locator + ":" + "element doesn't present on page");
        }

        return ret;
    }

    public boolean isVisible() {
        boolean ret = page.isVisible(idLocator);
        if (!ret) {
            log.debug("component [" + locator + "] is not visible on page.");
            doSetLastError(locator + ":" + "element is not visible on page");
        }

        return ret;
    }

    protected void doClick() {
        page.click(idLocator);
    }

    protected void doContextClick(){
      //  page.contextClick(idLocator);

        Actions action = new Actions(page.getDriver());
        WebElement element = page.getDriver().findElement(By.id(idLocator.substring(3)));
        action.contextClick();
        action.contextClick(element);
    }

    public void doDoubleClick() {
        Actions action = new Actions(page.getDriver());
        WebElement element = page.getDriver().findElement(By.id(idLocator.substring(3)));
        action.doubleClick(element).perform();
    }


    public void dragAndDropTo(HTMLComponentBase target, int xOfferSet, int yOfferSet) {
        boolean breakIt;
        while (true) {
            breakIt = true;
            try {
                WebElement element = driver.findElement(By.id(target.getIdLocator().substring(3)));
                int currLocationX = element.getLocation().x;
                int currLocationY = element.getLocation().y;

                int moveX = xOfferSet - currLocationX;
                int moveY = yOfferSet - currLocationY;

                Actions builder = new Actions(driver);
                Action action = builder.dragAndDropBy(element, moveX, moveY).build();
                action.perform();

                int X = element.getLocation().x;
                int Y = element.getLocation().y;

            } catch (Exception e) {
                if (e.getMessage().contains("Error: element is not attached")) {
                    breakIt = false;
                }
            }
            if (breakIt) {
                break;
            }
        }
    }

    public void dragAndDropBy(HTMLComponentBase target, int xOfferSet, int yOfferSet) {
        boolean breakIt;
        while (true) {
            breakIt = true;
            try {
                WebElement element = driver.findElement(By.id(target.getIdLocator().substring(3)));
                int currLocationX = element.getLocation().x;
                int currLocationY = element.getLocation().y;

                Actions builder = new Actions(driver);
                Action action = builder.dragAndDropBy(element, xOfferSet, yOfferSet).build();
                action.perform();

                int X = element.getLocation().x;
                int Y = element.getLocation().y;

            } catch (Exception e) {
                if (e.getMessage().contains("Error: element is not attached")) {
                    breakIt = false;
                }
            }
            if (breakIt) {
                break;
            }
        }
    }

    public void dragAndDropToTarget(HTMLComponentBase source, HTMLComponentBase target) {
        boolean breakIt;
        while (true) {
            breakIt = true;
            try {
                WebElement from = driver.findElement(By.id(source.getIdLocator().substring(3)));
                Actions builder = new Actions(driver);
                int x = from.getLocation().x;
                int y = from.getLocation().y;


                builder.clickAndHold(from).build().perform();
                x = from.getLocation().x;
                y = from.getLocation().y;
                WebElement iFrame = driver.findElement(By.tagName("iframe"));
                //  System.out.println("iFrame Size: " + iFrame.getSize());
                int a = iFrame.getLocation().x;
                int b = iFrame.getLocation().y;
                driver.switchTo().frame(iFrame);

                WebElement to = driver.findElement(By.id(target.getIdLocator().substring(3)));
                int s = to.getLocation().x;
                int g = to.getLocation().y;

                Action action = builder.moveToElement(to, 100, 150).release(to).build();
                        //moveToElement(to)
                        //.click(to)
//                Action action = builder.moveToElement(to, -200,-600)
//                        .release(to)
//                        .build();
                action.perform();

                //  builder.click(to).build().perform();

                //   System.out.println(to.getLocation());
                // builder.moveToElement(to).build().perform();
                // builder.release(to).build().perform();
                //  builder.moveByOffset(-500,-100).build().perform();
                //   int c = from.getLocation().x;
                //   int d = from.getLocation().y;
//                builder.moveByOffset(-700,-100).build().perform();
//                builder.moveByOffset(-700,-100).build().perform();
//                builder.moveByOffset(-700,-100).build().perform();
                // builder.release(from).build().perform();

                //    builder.moveToElement(to,5,5).click(to).build().perform();
                //System.out.println(from.getLocation());
                //  builder.click(to).build().perform();
                // driver.switchTo().defaultContent();

            } catch (Exception e) {
                if (e.getMessage().contains("Error: element is not attached")) {
                    breakIt = false;
                }
            }
            if (breakIt) {
                break;
            }
        }
    }

    public void dragDrop(HTMLComponentBase from, HTMLComponentBase to) {
        boolean breakIt;
        while (true) {
            breakIt = true;
            try {
                WebElement elementFrom = driver.findElement(By.id(from.getIdLocator().substring(3)));
                WebElement elementTo = driver.findElement(By.id(to.getIdLocator().substring(3)));
                Actions builder = new Actions(driver);
                Action action = builder.clickAndHold(elementFrom)
                        .moveToElement(elementTo)
                        .release(elementTo)
                        .build();
                action.perform();
            } catch (Exception e) {
                if (e.getMessage().contains("element is not attached")) {
                    breakIt = false;
                }
            }
            if (breakIt) {
                break;
            }
        }

    }

    /*protected void doClickAt(String coordinates) {
        page.clickAt(idLocator, coordinates);
    }*/

    String doGetValue() {
        return page.getValue(idLocator);
    }

    void doSetValue(String value) {
        page.clearText(idLocator);
        page.sendkeys(idLocator, value);
    }

    void doInsertValue(String value) {
        page.sendkeys(idLocator, value);
    }

    void doClearDate() {
        //Necessary because clearText doesn't work on date fields http://stackoverflow.com/questions/15360362/clear-date-input-fails-on-chromewebdriver
        //Select current date value if there is one
        page.sendKeySequenceByLocator(idLocator, (Keys.chord(Keys.CONTROL, "a")));
        //Delete the entire value
        page.sendKeySequenceByLocator(idLocator, Keys.DELETE);
    }

    public void doSetValueNoClean(String value) {
        page.type(idLocator, value);
    }

    protected String doGetText() {
        return page.getText(idLocator);
    }

    String doGetCssAttribute(String cssProperty) {
        return page.getCssValue(idLocator, cssProperty);
    }

    protected String doGetAttribute(String attribute) {
        return page.getAttributeValue(idLocator, attribute);
    }

    boolean hasAttribute(String attribute) {
        return page.hasAttribute(idLocator, attribute);
    }

    void doMouseOver() {
        new Actions(page.getDriver()).moveToElement(page.getDriver().findElement(WebDriverSeleniumPageEx.parseBy(getIdLocator()))).perform();
    }

    public void doMouseOver(int xOffset, int yOffset) {
        new Actions(page.getDriver()).moveToElement(page.getDriver().findElement(WebDriverSeleniumPageEx.parseBy(getIdLocator())), xOffset, yOffset).perform();
    }

    public void doMouseDown() {
        //new Actions(page.getDriver())

        //new Actions(page.getDriver()).doubleClick().keyDown(Keys.BACK_SPACE).perform();

        new Actions(page.getDriver()).keyDown(Keys.valueOf("document name")).perform();
        new Actions(page.getDriver()).keyUp(Keys.ALT).perform();
    }

    public void doMouseUp() {
        //Write implementation here to do mouse up
    }

    public String getIdLocator() {
        waitForReady();
        return idLocator;
    }

    public boolean isReadOnly() {
        waitForReady();
        return page.isEditable(idLocator);
    }

    public void doClearText() {
        waitForReady();
        page.clearText(idLocator);
    }

    public String getBgColor() {
        waitForReady();
        return doGetCssAttribute("background-color");
    }

    public boolean hasChild(HTMLComponentBase child) {
        int length = page.executeScriptEx(String.format("return document.getElementById('%s').querySelectorAll('#%s').length", getIdLocator().substring(3), child.getIdLocator().substring(3))).AsInt();
        return length > 0;
    }

    public void pageDown() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.TAB).perform();
        action.sendKeys(Keys.PAGE_DOWN).perform();
    }

    public void sendEnterKeys() {
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER).perform();
    }

}
