package com.oracle.auto.testapp.tests.web.commons.steps;

import com.oracle.auto.testapp.web.pages.LoginPage;
import com.oracle.auto.testapp.web.pages.HomePage;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;

/**
 * Created by stepzhou on 7/3/2017.
 */

public class EloquaLogin {
    private TestAppPageFactory factory;

    public EloquaLogin() {
        factory = TestAppPageFactory.getInstance();
    }

    @Given("login Eloqua")
    public void loginEloqua() {
        LoginPage eloquaLogin = factory.resetBrowserAsLoginPage(PropertyConfiger.instance().getEnvData("default.browser", "googlechrome"), LoginPage.class, true);

        eloquaLogin.getCompanyName().setValue(PropertyConfiger.instance().getEnvData("eloqua.company.name",""));
        eloquaLogin.getUserName().setValue( PropertyConfiger.instance().getEnvData("eloqua.username",""));
        eloquaLogin.getPassword().setValue(PropertyConfiger.instance().getEnvData("eloqua.password",""));

        eloquaLogin.getLoginBtn().click();

        HomePage eloquaHomePage = factory.pageAs(HomePage.class);
        eloquaHomePage.getCloudAppBtn().waitForElementReady(120);
    }

    @Then("click eloqua home logo")
    public void backToHomePage(){
        HomePage homePage = factory.pageAs(HomePage.class);
        homePage.getHomeLogo().click();
        homePage.page().waitForSecond(5);
    }
}
