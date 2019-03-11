package com.oracle.auto.testapp.tests.web.mobilepoc.steps;

import com.oracle.auto.mobile.assertions.MobileAssert;
import com.oracle.auto.testapp.mobile.screens.wechat.OpenedPageScreen;
import com.oracle.auto.testapp.mobile.screens.wechat.OraAccountScreen;
import com.oracle.auto.testapp.web.pages.TestAppMobileScreenFactory;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.WebElement;


/**
 * Created by stepzhou on 7/25/2017.
 */
public class VerifyMessageSteps {
    private TestAppMobileScreenFactory mobileScreenFactory;

    public VerifyMessageSteps() {
        mobileScreenFactory = TestAppMobileScreenFactory.getInstance();
    }

    @When("find the message $title in oracle account")
    public void findMessageWithContent(String title) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.findContentsAndClick(title);
    }

    @When("verify the message content with $content1: $content2, $content3")
    public void getContentInMessage(String content1, String content2, String content3) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        String actualContent = oraAccountScreen.findContentsInElements(content1);
        String exceptedContent = content1 + content2 + content3;
        oraAccountScreen.verifyContentsInElements(exceptedContent, actualContent);
    }

    @When("verify the broadcast message content with $content")
    public void getContentInMessage(String content) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        String actualContent = oraAccountScreen.findContentsInElements(content);
        oraAccountScreen.verifyContentsInElements(content, actualContent);
    }

    @When("verify the broadcast message contains $content")
    public void checkContentInMessage(String content) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        String actualContent = oraAccountScreen.findContentsInElements(content);
        oraAccountScreen.checkContentsInElements(content, actualContent);
    }

    @Then("verify the opened URL with $contentURL and $identifier")
    public void verifyOpenedURL(String contentURL, String identifier) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.verifyURL_service_external(contentURL, identifier);
    }

    @Then("verify service account opened URL with $contentURL and $identifier by copy link")
    public void verifyOpenedURLByCopiedLink(String contentURL, String identifier) {
//        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
//        oraAccountScreen.verifyURL_service_external(contentURL, identifier);
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        openedPageScreen.verifyURL_service_external(contentURL, identifier);
    }

    @Then("verify copied Link URL with $contentURL and $identifier sent by campaign")
    public void verifyCopiedLinkSentbyCampaign(String contentURL, String identifier) {
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        openedPageScreen.verifyURL_service_external_send_by_campaign(contentURL, identifier);
    }


    @Then("checked the opened website with $contentURL and $identifier")
    public void checkedOpenedWebAddress(String contentURL, String identifier) {
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        MobileAssert.assertTrue("The Opened URL is not as expected", openedPageScreen.isURLCorrect(contentURL, identifier));
    }

    @Then("verify URL with $contentURL and $identifier")
    public void verifyOpenedURL01(String contentURL, String identifier) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.verifyURL_service_internal(contentURL, identifier);
    }

    @Then("open and verify URL with $contentURL")
    public void verifyOpenedURL01(String contentURL) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.verifyURL_subscription(contentURL);
    }

    @Then("verify copied Link with $contentURL by copy link send by campaign")
    public void verifyCopiedLinkSendByCampaign(String contentURL) {
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        openedPageScreen.verifyURL_subscription_send_by_campaign(contentURL);
    }

    @Then("verify opened URL with $contentURL by copy link")
    public void verifyOpenedURL01ByCopiedLink(String contentURL) {
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        openedPageScreen.verifyURL_subscription(contentURL);
    }

    @Then("verify there is no copy link")
    public void verifyNoCopyLink() {
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        openedPageScreen.waitForShort(60 * 3);
        openedPageScreen.verifyURLNoCopyLink();
    }

    @Then("verify APP page is present")
    public void verifyAPPPagePresent() {
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        //openedPageScreen.waitForShort(30);
        openedPageScreen.getAPPPageTitle().waitForElementPresent(30);
        openedPageScreen.verifyAPPPageTitlePresent();
    }


    @Then("closed the opened web site")
    public void closeOpenedWebsite() {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.closeWebSite();
    }
}
