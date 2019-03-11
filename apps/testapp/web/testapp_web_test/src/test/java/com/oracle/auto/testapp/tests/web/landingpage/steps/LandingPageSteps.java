package com.oracle.auto.testapp.tests.web.landingpage.steps;

import com.oracle.auto.testapp.mobile.pages.LandingPage;
import com.oracle.auto.testapp.web.pages.AssetsPages.JSGeneratorLandingPage;
import com.oracle.auto.testapp.web.pages.AssetsPages.LandingPageConPage;
import com.oracle.auto.testapp.web.pages.AssetsPages.LandingPageMainPage;
import com.oracle.auto.testapp.web.pages.AssetsPages.RegistrationServiceLandingPage;
import com.oracle.auto.testapp.web.pages.AudiencePages.AudienceMainPage;
import com.oracle.auto.testapp.web.pages.CampaignPages.*;
import com.oracle.auto.testapp.web.pages.HomePage;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.pages.WebDriverEx;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebAssert;
import org.apache.regexp.RE;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.interactions.Actions;

/**
 * Created by grace on 8/29/2017.
 */

public class LandingPageSteps {
    private TestAppPageFactory factory;

    public LandingPageSteps() {
        factory = TestAppPageFactory.getInstance();
    }

    @When("move cursor to assets")
    public void moveCursorToAssets() {
        HomePage eloquaHomePage = factory.pageAs(HomePage.class);
        eloquaHomePage.page().waitForSecond(2);
        int index = 0;
        while ((!eloquaHomePage.getHomeTopNavigator().getAssetMenu().getAssets().isReady()) && (index < 60)) {
            eloquaHomePage.page().waitForSecond(2);
            index++;
        }
        eloquaHomePage.getHomeTopNavigator().getAssetMenu().getAssets().moveToElement();
    }

    @When("click the landing page button")
    public void clickLandingPageButton() {
        HomePage eloquaHomePage = factory.pageAs(HomePage.class);
        eloquaHomePage.getHomeTopNavigator().getAssetMenu().getLandingpages().click();
    }

    @Then("choose blank landing page")
    public void chooseBlankLandingPage() {
        LandingPageMainPage landingPageMainPage = factory.pageAs(LandingPageMainPage.class);
        landingPageMainPage.getCreateLandingPageBtn().click();
        landingPageMainPage.getCreateBlankLPBtn().click();
        landingPageMainPage.getChooseBtn().click();
    }

    @Then("select domain and vanity URL: $vanityURL")
    public void selectDomain(String vanityURL) {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        String domainName = PropertyConfiger.instance().getEnvData("eloqua.wechat.registration.landing.domain", "");
        landingPageConPage.page().executeScript("document.getElementsByTagName(\"input\")[0].spellcheck=\"true\"");
        landingPageConPage.getDomainInputBox().setValueWithENTER(domainName);
        landingPageConPage.page().waitForSecond(2);
        landingPageConPage.getVanityURL().setValue(vanityURL);
    }

    @Then("choose HTML landing page")
    public void chooseHTMLLandingPage() {
        LandingPageMainPage landingPageMainPage = factory.pageAs(LandingPageMainPage.class);
        landingPageMainPage.getCreateLandingPageBtn().click();
        landingPageMainPage.getCreateBlankLPBtn().click();
        landingPageMainPage.getChooseBtn().click();

    }

    @Then("click cloud content button")
    public void clickCloudContentBtn() {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        landingPageConPage.page().waitForSecond(2);
        landingPageConPage.getCloudContact().click();
    }

    @Then("click form button")
    public void clickFormBtn() {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        landingPageConPage.page().waitForSecond(2);
        landingPageConPage.getForm().click();
    }

    @Then("select one form: $formName")
    public void selectFormIssue(String formName) {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        landingPageConPage.page().waitForSecond(5);
        landingPageConPage.getSearchBox().setValue(formName);
        landingPageConPage.page().waitForSecond(5);
        // int index=landingPageConPage.getExceptedIssueIndex(cloudContentIssue);
        landingPageConPage.dragOneForm(formName);
        landingPageConPage.getCloseCloudContentBtn().click();
        if (landingPageConPage.getCloseCloudContentBtn().isReady()) {
            landingPageConPage.getCloseCloudContentBtn().click();
        }
    }

    @Then("select one cloud content: $cloudContent")
    public void selectCloudContentIssue(String cloudContent) {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        landingPageConPage.page().waitForSecond(5);
        landingPageConPage.getSearchBox().setValue(cloudContent);
        landingPageConPage.page().waitForSecond(10);
        // int index=landingPageConPage.getExceptedIssueIndex(cloudContentIssue);
        landingPageConPage.dragOpenLandingPage();
        landingPageConPage.getCloseCloudContentBtn().click();
        landingPageConPage.getCloseCloudContentBtn().click();
    }


    @Then("open Registration cloud content issue")
    public void openRegistrationCloudContent(){
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        openCloudContentIssue(landingPageConPage.getCloudContentRegistrationIssue());
    }

    @Then("open JS cloud content issue")
    public void openJSCloudContent(){
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        landingPageConPage.page().waitForSecond(15);
        openCloudContentIssue(landingPageConPage.getCloudContentJSIssue());
    }


    public void openCloudContentIssue(HTMLDiv element) {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        landingPageConPage.page().switchToDefaultFrame();
        landingPageConPage.page().switchToIFrame();
        element.doubleClick();
    }

     @Then("move cloud content element in canvas")
    public void moveCloudContentIssue() {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        moveCanvasElement(landingPageConPage.getCloudContentJSIssue(), -500, -200);
    }

    @Then("move form element in canvas")
    public void moveFormIssue() {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        moveCanvasElement(landingPageConPage.getFormIssue(), -300, -200);
    }

    public void moveCanvasElement(HTMLDiv element, int xOffSet, int yOffSet) {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        landingPageConPage.page().waitForSecond(5);
        landingPageConPage.page().switchToDefaultFrame();
        landingPageConPage.page().switchToIFrame();

        landingPageConPage.getCloudContentJSIssue().click();
        landingPageConPage.getCloudContentJSIssue().dragAndDropBy(element, xOffSet, yOffSet);

        landingPageConPage.page().switchToDefaultFrame();
    }



    @Then("choose one form")
    public void chooseForm() {
        RegistrationServiceLandingPage registrationServiceLandingPage = factory.pageAs(RegistrationServiceLandingPage.class);
        String autoTestFormName = PropertyConfiger.instance().getEnvData("eloqua.wechat.form", "");
        registrationServiceLandingPage.page().waitForSecond(5);
        registrationServiceLandingPage.page().switchToDefaultFrame();
        registrationServiceLandingPage.page().switchToCloudContentConfigurationFrame();
        registrationServiceLandingPage.page().waitForSecond(5);
        registrationServiceLandingPage.getFormBodyLink().waitForElementReady(10);
        registrationServiceLandingPage.getFormBodyLink().click();
        registrationServiceLandingPage.getFormBox().click();
        registrationServiceLandingPage.getFormInputBox().setValue(autoTestFormName);
        int index = registrationServiceLandingPage.getExceptedFormIndex(autoTestFormName);
        registrationServiceLandingPage.getExceptedForm(index).click();

    }

    @Then("choose openID for service account")
    public void chooseServiceAccountOpenID() {
        RegistrationServiceLandingPage registrationServiceLandingPage = factory.pageAs(RegistrationServiceLandingPage.class);
        String serviceOpenID = PropertyConfiger.instance().getEnvData("eloqua.wechat.form.service.account.openID", "");
        registrationServiceLandingPage.page().waitForSecond(5);
        registrationServiceLandingPage.getOpenIDArrow().click();
        registrationServiceLandingPage.page().waitForSecond(3);
        int index = registrationServiceLandingPage.getExceptedFormContentIndex(serviceOpenID);
        registrationServiceLandingPage.getExceptedFormContent(index).click();
    }

    @Then("choose openID for subscription account")
    public void chooseSubscriptionAccountOpenID() {
        RegistrationServiceLandingPage registrationServiceLandingPage = factory.pageAs(RegistrationServiceLandingPage.class);
        String subscriptionOpenID = PropertyConfiger.instance().getEnvData("eloqua.wechat.form.subscription.account.openID", "");
        registrationServiceLandingPage.getOpenIDArrow().click();
        int index = registrationServiceLandingPage.getExceptedFormContentIndex(subscriptionOpenID);
        registrationServiceLandingPage.getExceptedFormContent(index).click();
    }

    @Then("save and close the Registration page")
    public void closeRegistrationPage() {
        RegistrationServiceLandingPage registrationServiceLandingPage = factory.pageAs(RegistrationServiceLandingPage.class);
        registrationServiceLandingPage.getSaveBtn().click();
        registrationServiceLandingPage.page().waitForSecond(3);
        registrationServiceLandingPage.getSaveBtn().click();
        registrationServiceLandingPage.page().switchToDefaultFrame();
        registrationServiceLandingPage.getCloseBtn().click();
        registrationServiceLandingPage.page().waitForSecond(10);
    }

    @Then("save landing page: $landingPageName")
    public void saveLandingPage(String landingPageName) {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        landingPageConPage.getSaveLPBtn().click();
        landingPageConPage.page().waitForSecond(1);
        landingPageConPage.getInputLPName().setValue(landingPageName);
        landingPageConPage.getSaveLPNameBtn().click();
        landingPageConPage.page().waitForSecond(15);
    }

    @Then("click save landing page button")
    public void saveLandingPage() {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        landingPageConPage.getSaveLPBtn().click();
        landingPageConPage.page().waitForSecond(15);
    }

    @Then("get landing page url")
    public void getLandingPageURL() {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        landingPageConPage.getContentURL().click();
        String URL = landingPageConPage.getLandingPageURL().getText();

        String url1 = PropertyConfiger.instance().getEnvData("service.account.JS.landing.page", "");
        PropertyConfiger.instance().setEnvData("service.account.JS.landing.page", URL);
        String url2 = PropertyConfiger.instance().getEnvData("service.account.JS.landing.page", "");
        landingPageConPage.getLandingPageURL().click();
    }

    @Then("input Email Address: $emailAddress and Nick Name")
    public void inputEmailAddressandNickName(String emailAddress) {
        LandingPageConPage landingPageConPage = factory.pageAs(LandingPageConPage.class);
        landingPageConPage.page().switchTabByIndex(1);
        landingPageConPage.page().executeScript("document.getElementById(\"field1\").value=\"" + emailAddress + "\"");
        landingPageConPage.page().executeScript("document.getElementById(\"field4\").value=\"nickName\"");
        landingPageConPage.page().executeScript("var ele = document.getElementsByClassName(\"submit-button\")[0]; ele.click()");
        //document.getElementById("field1").textContent="testtest"
        //landingPageConPage.getServiceOpenID().setValue("test");
    }

    @Then("choose openID and nick name for JS Generator")
    public void chooseOpenIDforJSGenerator() {
        JSGeneratorLandingPage jsGeneratorLandingPage = factory.pageAs(JSGeneratorLandingPage.class);
        jsGeneratorLandingPage.page().waitForSecond(5);
        jsGeneratorLandingPage.page().switchToDefaultFrame();
        jsGeneratorLandingPage.page().switchToCloudContentConfigurationFrame();
        jsGeneratorLandingPage.getOpenIDArrow().waitForElementReady(20);
        jsGeneratorLandingPage.getOpenIDArrow().click();
        String serviceOpenID = PropertyConfiger.instance().getEnvData("eloqua.wechat.form.service.account.openID", "");
        jsGeneratorLandingPage.page().waitForSecond(2);
        int index = jsGeneratorLandingPage.getExceptedFormContentIndex(serviceOpenID);
        jsGeneratorLandingPage.getExceptedFormContent(index).click();

        String nickName = PropertyConfiger.instance().getEnvData("eloqua.wechat.form.nick.name", "");
        jsGeneratorLandingPage.getNickNameArrow().click();
        index = jsGeneratorLandingPage.getExceptedFormContentIndex(nickName);
        jsGeneratorLandingPage.getExceptedFormContent(index).click();

    }

    @Then("save and close the JS message page")
    public void closeJSPage() {
        JSGeneratorLandingPage jsGeneratorLandingPage = factory.pageAs(JSGeneratorLandingPage.class);
        jsGeneratorLandingPage.getSaveBtn().click();
        jsGeneratorLandingPage.page().switchToDefaultFrame();
        jsGeneratorLandingPage.getCloseBtn().click();
        jsGeneratorLandingPage.page().waitForSecond(5);
    }


}

