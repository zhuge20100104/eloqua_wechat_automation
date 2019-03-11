package com.oracle.auto.testapp.tests.web.commons.steps;

import com.oracle.auto.testapp.web.pages.HomePage;
import com.oracle.auto.testapp.web.pages.LoginPage;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.testapp.web.pages.WeChatPlatformHomePage;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

/**
 * Created by stepzhou on 7/3/2017.
 */

public class WeChatPlatformLogin {
    private TestAppPageFactory factory;

    public WeChatPlatformLogin() {
        factory = TestAppPageFactory.getInstance();
    }

    @Given("login WeChat Platform")
    public void loginWeChatPlatform() {
        WeChatPlatformHomePage weChatPlatformHomePage = factory.resetBrowserAsLoginPage(PropertyConfiger.instance().getEnvData("abc","googlechrome"), WeChatPlatformHomePage.class,true);
        weChatPlatformHomePage.getAccount().setValue("abc");
        weChatPlatformHomePage.getPassword().setValue("def");
        weChatPlatformHomePage.getLoginBtn().click();
        //weChatPlatformHomePage.page().waitForSecond(5000);

    }

    @Then("click eloqua home logo")
    public void backToHomePage(){
        HomePage homePage = factory.pageAs(HomePage.class);
        homePage.getHomeLogo().click();
        homePage.page().waitForSecond(5);
    }
}
