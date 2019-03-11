package com.oracle.auto.web.pages;

import com.oracle.auto.web.utility.PropertyConfiger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class WebDriverEx implements ILeapWebDriver {

    protected final WebDriver driver;
    public static final int PAGE_WAIT_PAGE_READY_TIMEOUT = PropertyConfiger.instance().getEnvData("page.all.ready.timeout", 30);

    public WebDriverEx(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void get(String url) {
        if (!url.contains("://"))
            driver.get("http://" + url);
        else
            driver.get(url);
        driver.manage().window().maximize();
    }

    @Override
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        return driver.getTitle();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return driver.findElements(by);
    }

    @Override
    public WebElement findElement(By by) {
        return driver.findElement(by);
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        throw new UnsupportedOperationException("Managed Externally...");
    }

    @Override
    public Set<String> getWindowHandles() {
        return driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public WebDriver.TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public WebDriver.Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public WebDriver.Options manage() {
        return driver.manage();
    }

    public void refresh() {
        driver.navigate().refresh();
    }

    public static void waitFor(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            // continue
        }
    }

    public void waitForShort() {
        waitFor(2);
    }

    public void waitMilliSecond() {
        waitForMilliSecond(33);
    }

    public void waitForPageToLoad() {
        waitFor(PAGE_WAIT_PAGE_READY_TIMEOUT);
    }

    public void waitForMilliSecond(int miliSecond) {
        try {
            TimeUnit.MILLISECONDS.sleep(miliSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitForSecond(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
