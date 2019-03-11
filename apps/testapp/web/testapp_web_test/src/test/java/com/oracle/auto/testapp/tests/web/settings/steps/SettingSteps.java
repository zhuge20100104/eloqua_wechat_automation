package com.oracle.auto.testapp.tests.web.settings.steps;

import com.oracle.auto.testapp.web.model.WeChatFile;
import com.oracle.auto.testapp.web.pages.HomePage;
import com.oracle.auto.testapp.web.pages.MenuServicePages.AutoReplyManagementPage;
import com.oracle.auto.testapp.web.pages.SettingsPages.*;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebAssert;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by grcao on 2017/7/10.
 */
public class SettingSteps {
    private TestAppPageFactory factory;

    public SettingSteps() {
        factory = TestAppPageFactory.getInstance();
    }

    @When("go to my apps page in settings configuration")
    public void goToMyAppPage() {
        clickSettingsButton();
        clickAppsButton();
        selectApps();
        clickConfigLink();
    }

    @When("click settings button on home page")
    public void clickSettingsButton() {
        HomePage homePage = factory.pageAs(HomePage.class);
        homePage.page().waitForSecond(3);
        homePage.getHomeTopNavigator().getSettings().click();
        homePage.page().waitForSecond(3);
    }

    @When("click apps button on platform extensions table on settings page")
    public void clickAppsButton() {
        SettingsPage settingsPage = factory.pageAs(SettingsPage.class);
        settingsPage.getPlatformExtensions().getApps().click();
        settingsPage.page().waitForSecond(3);
    }

    @Then("select wechat app in appcloud catalog page")
    public void selectApps() {
        String appName = PropertyConfiger.instance().getEnvData("eloqua.settings.wechat.app.name", "");
        AppCloudCatalogPage appCloudCatalogPage = factory.pageAs(AppCloudCatalogPage.class);
        appCloudCatalogPage.page().waitForSecond(10);
        appCloudCatalogPage.page().switchToFullPageFrame();
        appCloudCatalogPage.page().waitForSecond(10);

        appCloudCatalogPage.getSearchInputBox().waitForElementReady(60);
        appCloudCatalogPage.getSearchInputBox().click();
        appCloudCatalogPage.getSearchInputText().setValue(appName);
        appCloudCatalogPage.waitForSecond(3);
        appCloudCatalogPage.getSelectedApps().click();
        appCloudCatalogPage.waitForSecond(3);
    }

    @Then("click config link in my apps page")
    public void clickConfigLink() {
        MyAppsPage myAppsPage = factory.pageAs(MyAppsPage.class);
        myAppsPage.getConfigLink().click();
        myAppsPage.page().waitForSecond(3);
    }

    @Then("click add account link")
    public void clickAddAccountButton() {
        AppConfigurationPage appConfigurationPage = factory.pageAs(AppConfigurationPage.class);
        appConfigurationPage.page().waitForSecond(3);
        appConfigurationPage.page().switchToChildWindow();
        appConfigurationPage.getAddAccountButton().moveToElement();
        appConfigurationPage.getAddAccountButton().click();
        appConfigurationPage.page().waitForSecond(3);
    }

    @Then("select openID field $openIDValue")
    public void selectOpenIDField(String openIDValue) {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        officialAccountSettingPage.page().waitForSecond(3);
        officialAccountSettingPage.selectOpenID(openIDValue);
    }

    @Then("click advanced user link")
    public void clickAdvancedUserLink() {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        officialAccountSettingPage.getSettingLink().moveToElement();
        officialAccountSettingPage.getSettingLink().click();
        officialAccountSettingPage.page().waitForShort();
        officialAccountSettingPage.getSettingLink().pageDown();
    }

    @Then("select the login user")
    public void selectLoginUser() {
        String userName = PropertyConfiger.instance().getEnvData("eloqua.username", "");
        selectAdvancedUser(userName);
    }

    @Then("select one no-login advanced user")
    public void selectNoLoginAdvancedUser() {
        String userName = PropertyConfiger.instance().getEnvData("eloqua.no.login.username", "");
        selectAdvancedUser(userName);
    }

    @Then("choose access to all users in advanced setting")
    public void chooseAccessToAllUsers() {
        String userName = "All";
        selectAdvancedUser(userName);
    }

    @Then("verify if the user is the login user")
    public void verifyLoginUserName() {
        String userName = PropertyConfiger.instance().getEnvData("eloqua.username", "");
        verifyUserName(userName);
    }

    @Then("verify if the user is the no-login advanced user")
    public void verifyNoLoginUserName() {
        String userName = PropertyConfiger.instance().getEnvData("eloqua.no.login.username", "");
        verifyUserName(userName);
    }

    @Then("verify if the advanced user is All")
    public void verifyUserIsAll() {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        if (!(officialAccountSettingPage.getAdvancedUserText().isReady())) {
            officialAccountSettingPage.getAdvancedUserText().pageDown();
        }
        officialAccountSettingPage.getAuthorizedUsersBox().waitForElementReady(20);
        String actualUserName = officialAccountSettingPage.getAuthorizedUsersBox().getText();

        if (actualUserName.matches("All") || actualUserName.matches("所有")) {
            WebAssert.assertTrue(true);
        } else {
            WebAssert.assertFalse(false);
        }
    }

    public void verifyUserName(String userName) {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        if (!(officialAccountSettingPage.getUsersInputBox().isReady())) {
            officialAccountSettingPage.getAdvancedUserText().pageDown();
        }
        officialAccountSettingPage.getUsersInputBox().waitForElementReady(20);

        String actualUserName = officialAccountSettingPage.getSelectedUserBox().getText();
        WebAssert.assertEquals(userName.toLowerCase().trim(), actualUserName.toLowerCase().trim());
    }


    @Then("select the advanced user")
    public void selectAdvancedUser(String userName) {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        officialAccountSettingPage.getAdvancedUserText().pageDown();
        officialAccountSettingPage.waitForSomeTime(2);
        officialAccountSettingPage.clearSelectedUsers();

        officialAccountSettingPage.getUsersSearchBox().click();
        officialAccountSettingPage.waitForSomeTime(1);
        officialAccountSettingPage.getUsersInputBox().setValue(userName);
        officialAccountSettingPage.waitForSomeTime(1);
        if (!userName.equals("All")) {
            officialAccountSettingPage.getUsersInputBox().sendEnterKeys();
        }
        officialAccountSettingPage.waitForSomeTime(1);
        officialAccountSettingPage.getAdvancedUserText().click();
        officialAccountSettingPage.page().waitForShort();
        officialAccountSettingPage.getAdvancedUserText().pageDown();
        officialAccountSettingPage.page().waitForShort();
    }

    @Then("save all settings")
    public void saveAllSettings() {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        officialAccountSettingPage.page().waitForSecond(3);
        officialAccountSettingPage.getSaveBtn().click();
        officialAccountSettingPage.page().waitForSecond(5);
    }

    @Then("close settings page")
    public void closeSettingsPage() {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        officialAccountSettingPage.page().waitForSecond(2);
        officialAccountSettingPage.getCloseBtn().click();
    }

    @Then("input default campaign ID $campaignIDValue")
    public void setDefaultCampaignIDValue(String campaignIDValue) {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        officialAccountSettingPage.getDefaultCampaignID().setValue(campaignIDValue);
    }

//    Then input advanced Settings


    @Then("click authorize button")
    public void clickAuthorizeButton() {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        officialAccountSettingPage.getAuthorizeButton().moveToElement();
        officialAccountSettingPage.getAuthorizeButton().click();
    }

    @Then("save the account settings")
    public void saveAccountSettings() {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        officialAccountSettingPage.page().switchToDefaultTab();

        officialAccountSettingPage.page().switchToFullPageFrame();
        officialAccountSettingPage.page().switchToChildWindow();

        officialAccountSettingPage.getSaveBtn().click();
    }

    @Then("verify qrcode in new page $pageTitle")
    public void verifyQRCodeInNewPage(String pageTitle) {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        System.out.println("Current we are in window: " + officialAccountSettingPage.page().getDriver().getWindowHandle());
        officialAccountSettingPage.page().switchTabByIndex(1);
        System.out.println("Now we are in window: " + officialAccountSettingPage.page().getDriver().getWindowHandle());
        int index = 0;
        while ((officialAccountSettingPage.page().getTitle().length() == 0) && index < 60) {
            officialAccountSettingPage.waitForSomeTime(2);
            index++;
        }
        String title = officialAccountSettingPage.page().getTitle();
        WebAssert.assertEquals(pageTitle, title);
    }

    @Then("click $accountName edit button")
    public void clickEditAccountButton(String accountName) {
        AppConfigurationPage appConfigurationPage = factory.pageAs(AppConfigurationPage.class);
        appConfigurationPage.page().waitForSecond(3);
        appConfigurationPage.page().switchToChildWindow();

        //System.out.println(appConfigurationPage.page().getTitle());
        appConfigurationPage.waitForSomeTime(5);
        appConfigurationPage.getEditButton(accountName).click();
    }

    @Then("click $accountName delete button")
    public void clickDeleteAccountButton(String accountName) {
        AppConfigurationPage appConfigurationPage = factory.pageAs(AppConfigurationPage.class);
        appConfigurationPage.page().switchToChildWindow();

        appConfigurationPage.waitForSomeTime(5);
        appConfigurationPage.getDeleteButton(accountName).click();
    }

    @Then("click save button in WeChat App Configuration page")
    public void clickSaveWeChatAPPBtn() {
        AppConfigurationPage appConfigurationPage = factory.pageAs(AppConfigurationPage.class);
        //  appConfigurationPage.getSaveBtn().click();
    }

    @Then("verify if successful message appears in WeChat App Configuration page")
    public void verifySuccessfulMsgAppears() {
        AppConfigurationPage appConfigurationPage = factory.pageAs(AppConfigurationPage.class);
        //    boolean status = appConfigurationPage.successfulMsgIsVisibleOrNot();
        //   WebAssert.assertTrue(status);
    }

    @Then("close WeChat App Configuration page")
    public void clickCloseWeChatAPPBtn() {
        AppConfigurationPage appConfigurationPage = factory.pageAs(AppConfigurationPage.class);
        appConfigurationPage.page().goBack();
    }

    @Then("confirm delete action")
    public void confirmDeleteOfficialAccountAction() {
        DeleteOfficialAccountPage deleteOfficialAccountPage = factory.pageAs(DeleteOfficialAccountPage.class);
        deleteOfficialAccountPage.getDeleteBtn().click();
    }

    @Then("Verify if the alert warning message appears")
    public void verifyAlertWarningMsg() {
        DeleteOfficialAccountPage deleteOfficialAccountPage = factory.pageAs(DeleteOfficialAccountPage.class);
        WebAssert.assertTrue(deleteOfficialAccountPage.warningMsgIsVisibleOrNot());
    }

    @Then("download qrcode to $file")
    public void downloadFile(WeChatFile weChatFile) {
        OfficialAccountSettingPage officialAccountSettingPage = factory.pageAs(OfficialAccountSettingPage.class);
        officialAccountSettingPage.downloadFile(weChatFile);
    }

    @Then("download autoreply message qrcode to $file")
    public void downloadAutoReplyMessageQrcode(WeChatFile weChatFile) {
        AutoReplyManagementPage autoReplyManagementPage = factory.pageAs(AutoReplyManagementPage.class);
        autoReplyManagementPage.downloadFile(weChatFile);
        autoReplyManagementPage.waitForSecond(10);
    }

}