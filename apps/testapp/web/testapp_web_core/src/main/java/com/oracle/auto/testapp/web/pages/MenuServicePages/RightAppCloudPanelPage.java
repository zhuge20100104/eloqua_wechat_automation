package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.exceptions.WebExceptionBase;
import com.oracle.auto.web.pages.WebDriverEx;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 7/23/2017.
 */

@Component
public class RightAppCloudPanelPage extends TestAppPageBase {
    private HTMLDiv weChatMessageBtn;

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setWeChatMessageBtn(HTMLDiv weChatMessageBtn) {
        this.weChatMessageBtn = weChatMessageBtn;
    }

    public HTMLDiv getWeChatMessageBtn() {
        return weChatMessageBtn;
    }

    private HTMLDiv searchApp = new HTMLDiv("BDD_jQuery('.cloud-app-item')[%d]");

    public HTMLDiv getDesiredApp(String targetApp) {
        String getAppSizeStr = "return BDD_jQuery('.cloud-app-item').size()";
        searchApp.setPage(page);

        int curPageAppSize = page.executeScriptEx(getAppSizeStr).AsInt();
        String previousLastApp = getPreviousLastApp(curPageAppSize).getText();
        WebElement srcElement = page.getDriver().findElements(By.className("thumb-center")).get(5);

        while (true) {
            HTMLDiv returnValue = getTargetApp(targetApp, curPageAppSize);
            if (returnValue != null)
                return returnValue;

            new Actions(page.getDriver()).dragAndDropBy(srcElement, srcElement.getLocation().x, 50).build().perform();
            page.waitForShort();

            curPageAppSize = page.executeScriptEx(getAppSizeStr).AsInt();
            String curLastApp = getPreviousLastApp(curPageAppSize).getText();
            if (previousLastApp.equalsIgnoreCase(curLastApp))
                break;

            previousLastApp = curLastApp;
        }

        throw new WebExceptionBase(page, "Failed to find specified APP: " + targetApp);
    }

    private HTMLDiv getPreviousLastApp(int max) {
        searchApp.setLocator(String.format(searchApp.getRawLocator(), max - 1));
        return searchApp;
    }

    private HTMLDiv getTargetApp(String targetApp, int max) {
        for (int i = 0; i < max; i++) {
            searchApp.setLocator(String.format(searchApp.getRawLocator(), i));
            if (searchApp.getText().contains(targetApp))
                return searchApp;
        }
        return null;
    }

    public void waitForTime(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
