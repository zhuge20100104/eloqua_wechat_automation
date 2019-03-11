package com.oracle.auto.testapp.tests.web.mobilepoc.steps;

import com.oracle.auto.mobile.assertions.MobileAssert;
import com.oracle.auto.mobile.driver.MobileDriverEx;
import com.oracle.auto.testapp.mobile.pages.AuthorizeConfirmPage;
import com.oracle.auto.testapp.mobile.pages.AuthorizeSuccessPage;
import com.oracle.auto.testapp.mobile.pages.LandingPage;
import com.oracle.auto.testapp.mobile.screens.wechat.HomeScreen;
import com.oracle.auto.testapp.mobile.screens.wechat.OraAccountScreen;
import com.oracle.auto.testapp.mobile.screens.wechat.ScanScreen;
import com.oracle.auto.testapp.web.model.WeChatFile;
import com.oracle.auto.testapp.web.pages.SettingsPages.OfficialAccountSettingPage;
import com.oracle.auto.testapp.web.pages.TestAppMobileScreenFactory;
import com.oracle.auto.web.utility.WebAssert;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Created by stepzhou on 7/25/2017.
 */
public class AuthorizeAccountSteps {
    private TestAppMobileScreenFactory mobileScreenFactory;

    public AuthorizeAccountSteps() {
        mobileScreenFactory = TestAppMobileScreenFactory.getInstance();
    }

    @Then("scan the first QRCode picture")
    public void scanFirstQRCode() {
        prepareToScanQRCode();
        selectQrCode();
    }

    public void prepareToScanQRCode() {
        clickTopRightOptions();
        clickScanButton();
        clickMoreOptions();
        clickSelectQrCodeButton();
    }

    @When("click the top right options button")
    public void clickTopRightOptions() {
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        homeScreen.getTopRightOptions().click();
    }


    @Then("click the scan button")
    public void clickScanButton() {
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        homeScreen.getScanBtn().click();
    }

    @When("click more options button")
    public void clickMoreOptions() {
        ScanScreen scanScreen = mobileScreenFactory.screenAs(ScanScreen.class);
        scanScreen.getMoreBtn().click();
    }

    @Then("click select qrcode from image library button")
    public void clickSelectQrCodeButton() {
        ScanScreen scanScreen = mobileScreenFactory.screenAs(ScanScreen.class);
        scanScreen.getSelectBtn().click();
        MobileDriverEx.waitFor(2);
    }

    @Then("select the qrcode")
    public void selectQrCode() {
        ScanScreen scanScreen = mobileScreenFactory.screenAs(ScanScreen.class);
        scanScreen.getSelectImage().click();
        MobileDriverEx.waitFor(10);
    }

    @Then("select the qrcode and wait longer")
    public void selectQRCodeforAutoReply() {
        ScanScreen scanScreen = mobileScreenFactory.screenAs(ScanScreen.class);
        scanScreen.getSelectImage().click();
        MobileDriverEx.waitFor(60);
    }


    @Then("check rich media message $title")
    public void verifyRichMediaMessageTitle(String title) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        MobileAssert.assertTrue("Failed to receive the message " + title, oraAccountScreen.getRichMessage(title).isPresent());
    }

    @Then("click the message $title")
    public void clickRichMessageTitle(String title) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.getRichMessage("查看全文").click();
    }

    @Then("verify if the opened address $contentURL is correct")
    public void verifyOpenedAddress(String contentURL) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.verifyURL_subscription(contentURL);
    }

    @Then("verify the autoreply message: $text")
    public void verifyAutoReplyMessage(String text) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        MobileDriverEx.waitFor(10);
        MobileAssert.assertTrue("Failed to get auto reply message.", oraAccountScreen.findLatestContent(text, 0));
    }

    @Then("verify message after type keyword: $text")
    public void verifyAutoReplyMessageWithKeyword(String text) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        MobileDriverEx.waitFor(20);
        MobileAssert.assertTrue("Failed to get auto reply message.", oraAccountScreen.findLatestContent(text, 1));
    }

    @Then("wait for $number minute(s)")
    public void waitForMins(String minutes) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        String time = minutes.toLowerCase();
        if (time.contains("one")) {
            MobileDriverEx.waitFor(60);
        } else if (time.contains("two")) {
            MobileDriverEx.waitFor(60 * 2);
        } else if (time.contains("three")) {
            MobileDriverEx.waitFor(60 * 3);
        } else if (time.contains("four")) {
            MobileDriverEx.waitFor(60 * 4);
        } else if (time.contains("five")) {
            MobileDriverEx.waitFor(60 * 5);
        }

    }

    @When("input keyword $keyword in the account")
    public void inputKeyword(String keyword) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        MobileDriverEx.waitFor(5);
        oraAccountScreen.getMessageBtn("消息").click();
        oraAccountScreen.getMessageField().setValue(keyword);
        oraAccountScreen.getSendBtn("发送").click();
        MobileDriverEx.waitFor(5);
    }

    @When("input keyword $keyword and wait longer")
    public void inputKeywordforAutoReplyOnly(String keyword) {
        inputKeyword(keyword);
        MobileDriverEx.waitFor(60);
    }

    @When("type texts $keyword in the account")
    public void typeKeyword(String keyword) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        if (oraAccountScreen.getMessageBtn("消息").isVisible()) {
            oraAccountScreen.getMessageBtn("消息").click();
        }
        oraAccountScreen.getMessageField().setValue(keyword);
        oraAccountScreen.getSendBtn("发送").click();
    }

    @Then("the message $message will appear")
    public void verifyMessage(String message) {
        AuthorizeConfirmPage authorizeConfirmPage = mobileScreenFactory.screenAs(AuthorizeConfirmPage.class);
        MobileAssert.assertEquals(message, authorizeConfirmPage.getAuthorizeTexts().getText());
    }

    @Then("select continue use button")
    public void continueToUse() {
        AuthorizeConfirmPage authorizeConfirmPage = mobileScreenFactory.screenAs(AuthorizeConfirmPage.class);
        authorizeConfirmPage.getContinueUse().click();
        MobileDriverEx.waitFor(10);
    }

    @Then("authorize success message $message will show up")
    public void authorizeSuccessPage(String message) {
        AuthorizeSuccessPage authorizeSuccessPage = mobileScreenFactory.screenAs(AuthorizeSuccessPage.class);
        MobileAssert.assertEquals(message, authorizeSuccessPage.getAuthorizeSuccess().getText());
    }

}
