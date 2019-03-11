package com.oracle.auto.web.utility;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverHelper {
	
    public static void waitForAngular(WebDriver webdriver) {
        try {
            webdriver.manage().timeouts().setScriptTimeout(PropertyConfiger.instance().getEnvData("angular.time.out", 30), TimeUnit.SECONDS);
            ((JavascriptExecutor) webdriver).executeAsyncScript(
                  "var callback = arguments[arguments.length - 1];\n"
                + "if (angular.getTestability) {\n"
                + "  angular.getTestability(document.body).whenStable(callback);\n"
                + "} else {\n"
                + "  angular.element(document.body).injector().get('$browser').notifyWhenNoOutstandingRequests(callback);}");
        } catch (Exception e) {
            // ignore
        }
    }
}
