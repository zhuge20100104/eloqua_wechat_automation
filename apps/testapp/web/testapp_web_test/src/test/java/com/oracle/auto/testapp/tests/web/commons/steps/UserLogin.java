package com.oracle.auto.testapp.tests.web.commons.steps;

import com.oracle.auto.apps.commons.LoginPage;
import com.oracle.auto.testapp.web.pages.GoogleHomePage;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.apache.log4j.Logger;

public class UserLogin {

    private static Logger log = Logger.getLogger(UserLogin.class);

    private final TestAppPageFactory factory;
    private static UserLogin instance = new UserLogin();

    public UserLogin() {
        this.factory = TestAppPageFactory.getInstance();
    }

    static public UserLogin getInstance() {
        return instance;
    }

    // before: any page
    // after: home page
    public void doRetryLogin(String username, String password, String browser, boolean resetBrowser) {
        int retry = 0;
        int max_retry = PropertyConfiger.instance().getEnvData("hotfix.login.retry.count", 0);

        do {
            try {
                log.info("tring to login system, retry=" + retry + " max_retry=" + max_retry);

                if (resetBrowser)
                    factory.resetBrowserAsLoginPage(browser, LoginPage.class, true).loginAs(GoogleHomePage.class, username, password);
                else
                    factory.resetCurrentPageAsLoginPage(browser, LoginPage.class, true).loginAs(GoogleHomePage.class, username, password);

                // get page URL
                boolean enableNominify = PropertyConfiger.instance().getEnvData("hotfix.enable.nominify", false);
                if (enableNominify) {
                    String specialSubURL = "?nominify";
                    WebDriverSeleniumPageEx driver = factory.pageAs(GoogleHomePage.class).page();
                    if (!driver.getCurrentUrl().contains(specialSubURL)) {
                        String fullURL = PropertyConfiger.instance().getEnvData("autoqe.testapp.url", "");
                        if (!fullURL.endsWith("/"))
                            fullURL += "/" + specialSubURL;
                        else
                            fullURL += specialSubURL;
                        driver.open(fullURL);
                        WebDriverSeleniumPageEx.waitFor(2); // wait for 2 seconds
                        // check if current page is home page
                        factory.pageAs(GoogleHomePage.class);
                    }
                }

                break;
            } catch (Exception ex) {
                log.error("fail to login to application...", ex);
                if (retry >= max_retry)
                    try {
                        throw ex;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        } while ((++retry) <= max_retry);
    }
}
