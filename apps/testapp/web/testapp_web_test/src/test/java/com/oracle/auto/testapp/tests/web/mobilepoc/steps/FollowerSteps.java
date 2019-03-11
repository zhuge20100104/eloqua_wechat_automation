package com.oracle.auto.testapp.tests.web.mobilepoc.steps;

import com.oracle.auto.mobile.components.elements.nativeview.NativeButton;
import com.oracle.auto.mobile.driver.MobileDriverEx;
import com.oracle.auto.mobile.driver.MobileDriverFactory;
import com.oracle.auto.mobile.exceptions.MobileExceptionBase;
import com.oracle.auto.testapp.mobile.pages.LandingPage;
import com.oracle.auto.testapp.mobile.screens.wechat.HomeScreen;
import com.oracle.auto.testapp.mobile.screens.wechat.OpenedPageScreen;
import com.oracle.auto.testapp.mobile.screens.wechat.OraAccountScreen;
import com.oracle.auto.testapp.mobile.screens.wechat.ScanScreen;
import com.oracle.auto.testapp.web.model.WeChatFile;
import com.oracle.auto.testapp.web.pages.TestAppMobileScreenFactory;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebAssert;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import com.oracle.auto.testapp.tests.web.mobilepoc.steps.AuthorizeAccountSteps;

import java.io.IOException;


/**
 * Created by stepzhou on 7/25/2017.
 */
public class FollowerSteps {
    private TestAppMobileScreenFactory mobileScreenFactory;
    String serviceAccount = PropertyConfiger.instance().getEnvData("eloqua.wechat.service.account", "");
    String subscriptionAccount = PropertyConfiger.instance().getEnvData("eloqua.wechat.subscription.account", "");
    String follow = "关注";
    String back = "返回";
    String subscriptionList = "订阅号";
    String unfollow = "不再关注";

    public FollowerSteps() {
        mobileScreenFactory = TestAppMobileScreenFactory.getInstance();
    }

    @Given("login wechat in mobile phone")
    public void loginWeChat() {
        mobileScreenFactory.resetApp(HomeScreen.class);
    }


    @When("push file $file to the phone")
    public void pushQRCodesToPhone(WeChatFile weChatFile) {
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        Runtime rt = Runtime.getRuntime();
        Process pr;
        try {
            //adb -s DLLKR17410005915 push X:\apps\testapp\web\testapp_web_test\data\autoreply_activity_qrcode_6923.png /storage/sdcard0/DCIM/Camera/autoreply_activity_qrcode_6923.png
            pr = rt.exec("adb -s " + MobileDriverFactory.instance().getDeviceName() + " push " + weChatFile.path + weChatFile.name + ".png" + " /storage/sdcard0/DCIM/Camera/" + weChatFile.name + ".png");
            pr.waitFor();
            //adb -s DLLKR17410005915 shell am broadcast -a android.intent.action.MEDIA_MOUNTED -d file:///storage/sdcard0/DCIM/Camera
            Process pr1 = rt.exec("adb -s " + MobileDriverFactory.instance().getDeviceName() + " shell am broadcast -a android.intent.action.MEDIA_MOUNTED -d file:///storage/sdcard0/DCIM/Camera");
            pr1.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new MobileExceptionBase(homeScreen.getMobileDriverEx(), "Failed to push the qrcodes to mobile phone.");
        }
    }

    @Given("test login wechat in mobile phone")
    public void testloginWeChat() {
        mobileScreenFactory.resetApp(HomeScreen.class);
    }

    @When("enter the oracle public account")
    public void enterPubAccount() {
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        //homeScreen.getOraAccount().tap(1,1);
        homeScreen.getOraAccount().click();
    }

    @When("click the subscription account $name")
    public void clickOradcAsubccount(String name) {
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        homeScreen.getOraAccount(subscriptionList).click();
        homeScreen.getOraAccount(name).click();
    }

    @When("click the service account $name")
    public void clickOradcAccount(String name) {
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        homeScreen.getOraAccount(name).click();
    }

    @Then("swipe to the top of the screen")
    public void swipeToTop() {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        //oraAccountScreen.swipeToTop();
    }

    @Then("click the landing page link according to content: $content")
    public void clickLandingPageLink(String content) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        MobileDriverEx.waitFor(20);
        oraAccountScreen.clickHere(content);
        // MobileDriverEx.waitFor(30);
    }

    @Then("click the landing page link")
    public void clickLandingPageLink() {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        MobileDriverEx.waitFor(5);
        oraAccountScreen.clickHere();
        // MobileDriverEx.waitFor(30);
    }

    @Then("click the landing page link then open in browser")
    public void clickLandingPageLinkInBrowser() {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        MobileDriverEx.waitFor(5);
        oraAccountScreen.clickHere();
        MobileDriverEx.waitFor(30);
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        openedPageScreen.getMoreBtn().click();
        openedPageScreen.getOpenInBrowser().click();

        MobileDriverEx.waitFor(20);
    }


    @Then("verify if welcome message $text can be received")
    public void verifyWelcomeMessageReceiving(String text) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.findContentsAndClick(text);
    }

    @Then("verify if not receive welcome message: $text")
    public void verifyWelcomeMessageNotReceived(String text) {
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        WebAssert.assertFalse(oraAccountScreen.verifyIfContentsNotFound(text));
    }


    @When("input first name $firstname")
    public void inputFirstName(String firstname) {
        MobileDriverEx.waitFor(10);//To wait the actual context is changed in the mobile side.
        LandingPage landingPage = mobileScreenFactory.screenAs(LandingPage.class);
        landingPage.getFirstName().waitForElementPresent(120);
        landingPage.getFirstName().setValue(firstname);
    }

    @Then("verify the opened URL without track option")
    public void verifyURLWithoutTrackOption() {
        MobileDriverEx.waitFor(10);
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.verifyURL_text_untrack();
    }

    @Then("verify the opened eloqua URL with track option")
    public void verifyURLWithTrackOption() {
        MobileDriverEx.waitFor(10);
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.verifyURL_text_track_eloqua_service();
    }

    @Then("verify the opened normal URL with track option")
    public void verifyNormalURLWithTrackOption() {
        MobileDriverEx.waitFor(10);
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.verifyURL_text_track_normal_service();
    }

    @Then("for subscription account verify the opened eloqua URL with track option")
    public void verifySubscriptionWithTrackOption() {
        MobileDriverEx.waitFor(10);
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.verifyURL_subscription_text_track_eloqua_service();
    }

    @Then("for subscription account verify the opened normal URL with track option")
    public void verifySubscriptionNormalWithTrackOption() {
        MobileDriverEx.waitFor(10);
        OraAccountScreen oraAccountScreen = mobileScreenFactory.screenAs(OraAccountScreen.class);
        oraAccountScreen.verifyURL_subscription_text_track_normal_service();
    }

    @Then("verify the opened eloqua URL with track option for service account")
    public void verifyServiceURLWithTrackOption() {
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        openedPageScreen.verifyURL_text_track_eloqua_service();
    }

    @Then("verify the opened normal URL with track option for service account")
    public void verifyServiceNormalURLWithTrackOption() {
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        openedPageScreen.verifyURL_text_track_normal_service();
    }

    @Then("verify the opened eloqua URL with track option for subscription account")
    public void verifySubscriptionEloquaURLWithTrackOption() {
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        openedPageScreen.verifyURL_subscription_text_track_eloqua_service();
    }

    @Then("verify the opened normal URL with track option for subscription account")
    public void verifySubscriptionNormalURLWithTrackOption() {
        OpenedPageScreen openedPageScreen = mobileScreenFactory.screenAs(OpenedPageScreen.class);
        openedPageScreen.verifyURL_subscription_text_track_normal_service();
    }

    @When("input last name $lastname")
    public void inputLastName(String lastname) {
        LandingPage landingPage = mobileScreenFactory.screenAs(LandingPage.class);
        landingPage.getLastName().setValue(lastname);
    }

    @When("input email address $email")
    public void inputEmailAddress(String email) {
        LandingPage landingPage = mobileScreenFactory.screenAs(LandingPage.class);
        landingPage.getEmailAddress().setValue(email);
    }

    @Then("click submit button")
    public void clickSubmitBtn() {
        LandingPage landingPage = mobileScreenFactory.screenAs(LandingPage.class);
        landingPage.getSubmitBtn().click();

        MobileDriverEx.waitFor(10);
    }

    @When("tap service account $account for a while")
    public void tapAccountForSometime(String account) {
        MobileDriverEx.waitFor(2);
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        homeScreen.getOraAccount(account).tap(1, 1000);
    }

    @Then("tap subscription account $account for a while")
    public void tapSubscriptionAccountForSometime(String account) {
        clickOradcAccount(subscriptionList);
        MobileDriverEx.waitFor(2);
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        homeScreen.getOraAccount(account).tap(1, 1000);
    }

    @Then("click unfollow button to unfollow the account")
    public void unfollowAccount() {
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        homeScreen.getUnfollow("取消关注").click();
    }

    @Then("click the confirm button to unfollow again")
    public void confirmNoMoreFollow() {
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        homeScreen.getConfirm(unfollow).click();
        homeScreen.waitForShort(5);
    }

    @Then("back to the home page")
    public void backToHomePage() {
        MobileDriverEx.waitFor(6);
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        homeScreen.getBack().click();
    }

    @Then("click back button again")
    public void backToHomePageSec() {
        MobileDriverEx.waitFor(2);
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        homeScreen.getBack().click();
    }

    @Then("user clicks the all images button")
    public void clickAllImagesButton() {
        MobileDriverEx.waitFor(2);
        ScanScreen scanScreen = mobileScreenFactory.screenAs(ScanScreen.class);
        scanScreen.getAllImagesBtn().click();
    }

    @Then("click the accounts image library")
    public void selectAccountsImageLibrary() {
        ScanScreen scanScreen = mobileScreenFactory.screenAs(ScanScreen.class);
        scanScreen.getAccountsImages().click();
    }

    @Then("select the qrcode of Service Account")
    public void selectdRdServiceAccount() {
        ScanScreen scanScreen = mobileScreenFactory.screenAs(ScanScreen.class);
        //MobileDriverEx.waitFor(2);
        scanScreen.getRdServiceAccount().click();
        //MobileDriverEx.waitFor(2);
    }

    @Then("select the qrcode of Subscription Account")
    public void selectSubscriptionAccount() {
        ScanScreen scanScreen = mobileScreenFactory.screenAs(ScanScreen.class);
        scanScreen.getSubscriptionAccount().click();
    }

    @Then("select the qrcode of client service account")
    public void selectClientServiceAccount() {
        ScanScreen scanScreen = mobileScreenFactory.screenAs(ScanScreen.class);
        scanScreen.getClientServiceAccount().click();
    }

    @Then("click the follow button")
    public void clickFollowButton() {
        MobileDriverEx.waitFor(2);
        ScanScreen scanScreen = mobileScreenFactory.screenAs(ScanScreen.class);
        scanScreen.getFollow(follow).waitForElementPresent(10);
        scanScreen.getFollow(follow).click();
        MobileDriverEx.waitFor(10);
    }

    @When("follow service account by scan QR code")
    public void followServiceAccountByScanQRCode() {
        prepareForFollowByScanQRCode();
        selectdRdServiceAccount();
        clickFollowButton();
    }

    @When("follow subscription account by scan QR code")
    public void followSubscriptionAccountByScanQRCode() {
        prepareForFollowByScanQRCode();
        selectSubscriptionAccount();
        clickFollowButton();
    }

    public void prepareForFollowByScanQRCode() {
        AuthorizeAccountSteps authorizeAccountSteps = new AuthorizeAccountSteps();
        authorizeAccountSteps.prepareToScanQRCode();
        clickAllImagesButton();
        selectAccountsImageLibrary();
    }

    private void unFollowSubscriptionAccount(String accountName) {
        tapSubscriptionAccountForSometime(accountName);
        unfollowAccount();
        confirmNoMoreFollow();
        backToHomePage();

    }

    private void unFollowServiceAccount(String accountName) {
        tapAccountForSometime(accountName);
        unfollowAccount();
        confirmNoMoreFollow();
        backToHomePage();
    }


    public boolean getAccountFollowUnfollowStatus(String accountName) {
        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
        if (accountName.contains(serviceAccount)) {
            if (homeScreen.getOraAccount(accountName).isPresent())
                return true;
        } else if (accountName.contains(subscriptionAccount)) {
            homeScreen.getOraAccount(subscriptionList).click();
            MobileDriverEx.waitFor(3);
            if (homeScreen.getOraAccount(accountName).isPresent()) {
                homeScreen.getBackPrevious(back).click();
                return true;
            }
            homeScreen.getBackPrevious(back).click();
        }
        return false;

    }

    @Given("ensure account $accountName is unfollowed")
    public boolean ensureAccountUnfollowed(String accountName) {
        if (getAccountFollowUnfollowStatus(accountName)) {
            if (accountName.contains(serviceAccount)) {
                unFollowServiceAccount(accountName);
            } else if (accountName.contains(subscriptionAccount)) {
                unFollowSubscriptionAccount(accountName);
            } else {
                return false;
            }
        } else {
            return true;
        }
        MobileDriverEx.waitFor(2);
        mobileScreenFactory.resetApp(HomeScreen.class);
        return true;
}

    @When("ensure account $accountName is followed")
    public boolean ensureAccountFollowed(String accountName) {
        if (getAccountFollowUnfollowStatus(accountName)) {
            return true;
        } else {
            if (accountName.contains(serviceAccount)) {
                followServiceAccountByScanQRCode();
            } else if (accountName.contains(subscriptionAccount)) {
                followSubscriptionAccountByScanQRCode();
            } else {
                return false;
            }
            MobileDriverEx.waitFor(2);
            mobileScreenFactory.resetApp(HomeScreen.class);
        }
        return true;


//        HomeScreen homeScreen = mobileScreenFactory.screenAs(HomeScreen.class);
//        homeScreen.getTopRightOptions().click();
//        homeScreen.getScanBtn().click();
//        ScanScreen scanScreen = mobileScreenFactory.screenAs(ScanScreen.class);
//        scanScreen.getMoreBtn().click();
//        scanScreen.getSelectBtn().click();
//        MobileDriverEx.waitFor(2);
//        scanScreen.getAllImagesBtn().click();
//        scanScreen.getAccountsImages().click();
//        MobileDriverEx.waitFor(2);
//        if (accountName.contains(serviceAccount)) {
//            scanScreen.getRdServiceAccount().click();
//        } else if (accountName.contains(subscriptionAccount)) {
//            scanScreen.getSubscriptionAccount().click();
//        } else {
//            return false;
//        }
//        MobileDriverEx.waitFor(5);
//        scanScreen.getFollow(follow).click();
//        MobileDriverEx.waitFor(2);
//        mobileScreenFactory.resetApp(HomeScreen.class);
//
//        return true;
    }

}
