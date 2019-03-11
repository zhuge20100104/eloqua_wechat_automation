package com.oracle.auto.testapp.tests.web.emails.steps;

import com.oracle.auto.testapp.web.pages.AssetsPages.*;
import com.oracle.auto.testapp.web.pages.CampaignPages.ActivateCampaignPage;
import com.oracle.auto.testapp.web.pages.HomePage;
import com.oracle.auto.testapp.web.pages.MenuServicePages.WelcomeMessageConPage;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebAssert;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.Date;

/**
 * Created by grace on 8/30/2017.
 */

public class EmailsSteps {
    private TestAppPageFactory factory;

    public EmailsSteps() {
        factory = TestAppPageFactory.getInstance();
    }


    @When("click the emails button")
    public void clickEmailsButton() {
        HomePage eloquaHomePage = factory.pageAs(HomePage.class);
        eloquaHomePage.page().waitForSecond(2);
        eloquaHomePage.getHomeTopNavigator().getAssetMenu().getEmails().click();
    }

    @Then("create a design blank email")
    public void createDesignBlankPage() {
        EmailsMainPage emailsMainPage = factory.pageAs(EmailsMainPage.class);
        emailsMainPage.getCreateEmailBtn().click();
        //emailsMainPage.getDesignEmailLink().click();
        emailsMainPage.page().waitForSecond(2);
        emailsMainPage.getBlankEmailBtn().click();
        emailsMainPage.page().waitForSecond(1);
        emailsMainPage.getChooseBtn().click();

    }

    @Then("click cloud content button in emails page")
    public void clickCloudContentBtn() {
        EmailsPageConPage emailsPageConPage = factory.pageAs(EmailsPageConPage.class);
        emailsPageConPage.page().waitForShort();
        emailsPageConPage.getCloudContact().click();
        // emailsPageConPage.getTextBtn().doubleClick();
    }


    @Then("select one cloud content for email: $cloudContentIssue")
    public void selectCloudContentIssue(String cloudContentIssue) {
        EmailsPageConPage emailsPageConPage = factory.pageAs(EmailsPageConPage.class);
        emailsPageConPage.page().waitForSecond(5);
        emailsPageConPage.getSearchBox().setValue(cloudContentIssue);
        emailsPageConPage.page().waitForSecond(5);
        int index = emailsPageConPage.getExceptedIssueIndex(cloudContentIssue);

        emailsPageConPage.dragOpenLandingPage();
    }

    @Then("input the email QRCode: $QRCode and select: $accountName")
    public void editEmailQRcodePagewithQRCodeAccount(String QRCode, String accountName) {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        emailQRCodeServicePage.page().waitForSecond(5);
        emailQRCodeServicePage.page().switchToDefaultFrame();
        emailQRCodeServicePage.page().switchToCloudContentConfigurationFrame();
        emailQRCodeServicePage.page().waitForSecond(2);
        emailQRCodeServicePage.getQRCodeName().setValue(QRCode);
        selectServiceAccount(accountName);
    }

    @Then("select email text message type")
    public void editEmailQRcodePage() {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        emailQRCodeServicePage.checkTextType();
        emailQRCodeServicePage.page().waitForSecond(1);
    }

    @Then("select email rich media message type")
    public void selectRichMediaMsgType() {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        emailQRCodeServicePage.checkRichMediaType();
        emailQRCodeServicePage.page().waitForSecond(1);
    }

    @Then("click email compact button")
    public void clickCompactBtn() {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        emailQRCodeServicePage.getCompactBtn().click();
    }

    @Then("click email medium button")
    public void clickMediumBtn() {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        emailQRCodeServicePage.getMediumBtn().click();
    }

    @Then("click email notable button")
    public void clickNotableBtn() {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        emailQRCodeServicePage.getNotableBtn().click();
    }

    @Then("click email next button")
    public void clickNextBtn() {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        emailQRCodeServicePage.getNextBtn().click();
    }

    @Then("input text for QRCode message: $text")
    public void inputText(String text) {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        emailQRCodeServicePage.page().waitForSecond(2);
        emailQRCodeServicePage.getTextArea().waitForElementEnabled(10);
//        emailQRCodeServicePage.page().executeScript("document.getElementById(\"hiddenDiv\").style.display=\"flex\"");
//        emailQRCodeServicePage.page().executeScript("document.getElementById(\"currentMessageContent\").type=\"\"");
//        //emailQRCodeServicePage.page().executeScript("document.getElementById(\"currentMessageContent\").innerText=\"asdfasdf\"");
//        emailQRCodeServicePage.getHiddenTextBox().setValue("testtesttest");
       // emailQRCodeServicePage.getTextArea().insertLineBreak();
        emailQRCodeServicePage.getTextArea().setValue(text);
        emailQRCodeServicePage.getTextArea().insertLineBreak();
        Date date = new Date();
        emailQRCodeServicePage.getTextArea().insertValue("Send in " + date.toString());
        emailQRCodeServicePage.getTextArea().insertLineBreak();
    }

    @Then("select rich media message with title: $richMediaMessageTitle")
    public void selectRichMediaMesssage(String richMediaMessageTitle) {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        emailQRCodeServicePage.page().waitForSecond(5);
        emailQRCodeServicePage.getExceptedRichMediaMessage(0).waitForElementReady(10);
        int index = emailQRCodeServicePage.getRichMediaMessageIndex(richMediaMessageTitle);
        emailQRCodeServicePage.getExceptedRichMediaMessage(index).click();
    }

    @Then("submit and close the QRCode email box")
    public void submitCloseQRCodeEmailBox() {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        emailQRCodeServicePage.page().waitForSecond(2);
        // emailQRCodeServicePage.getTextSubmitBtn().click();
       // emailQRCodeServicePage.page().executeScriptEx("var element=BDD_jQuery(\"#submit\")[0];element.click()");
        emailQRCodeServicePage.page().executeScriptEx("var element=BDD_jQuery(\".oj-button-primary\")[0];element.click()");
        emailQRCodeServicePage.page().waitForSecond(5);

        // emailQRCodeServicePage.page().executeScriptEx("var element=BDD_jQuery(\".cloud-content-config-close-icon\")[0];element.click()");
        emailQRCodeServicePage.page().switchToDefaultFrame();
        emailQRCodeServicePage.getCloseBtn().click();

    }

    @Then("disable email Welcome Message")
    public void disableWelcomeMessage() {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        if (emailQRCodeServicePage.getEnableStatus().isReady()) {
            WebAssert.assertFalse(emailQRCodeServicePage.getDisableStatus().isReady());
            emailQRCodeServicePage.getWelcomeMsgEnableBtn().click();
        }
    }

    @Then("enable email Welcome Message")
    public void enableWelcomeMessage() {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        if (emailQRCodeServicePage.getDisableStatus().isReady()) {
            emailQRCodeServicePage.getWelcomeMsgEnableBtn().click();
        }
    }

    @Then("save email with name: $emailName and subject: $emailSubject")
    public void saveEmailWithNameSubject(String emailName, String emailSubject) {
        EmailsPageConPage emailsPageConPage = factory.pageAs(EmailsPageConPage.class);
        emailsPageConPage.getEmailSubject().setValue(emailSubject);
        emailsPageConPage.page().waitForShort();
//        emailsPageConPage.page().executeScriptEx("var el = document.getElementsByTagName(\"pre\")[0]; el.innerHTML=\""+emailSubject+"\";");
//        emailsPageConPage.page().waitForShort();

        selectEmailGroup(PropertyConfiger.instance().getEnvData("eloqua.campaign.email.group", ""));

        emailsPageConPage.getSaveEmailBtn().click();
        emailsPageConPage.getInputEmailName().setValue(emailName);
        emailsPageConPage.getSavePopupEmailName().click();

        emailsPageConPage.page().waitForShort();
        emailsPageConPage.getSaveEmailBtn().click();
        emailsPageConPage.page().waitForSecond(5);
    }


    public void selectEmailGroup(String emailGroupName) {
        EmailsPageConPage emailsPageConPage = factory.pageAs(EmailsPageConPage.class);
        emailsPageConPage.page().waitForSecond(2);
        emailsPageConPage.getEmailGroupBtn().click();
        emailsPageConPage.page().waitForSecond(2);
        //int index = emailsPageConPage.getEmailGroupIndex(emailGroupName);
        emailsPageConPage.getEmailGroup(0).click();
        emailsPageConPage.page().waitForSecond(2);
    }

    public void selectServiceAccount(String accountName) {
        EmailQRCodeServicePage emailQRCodeServicePage = factory.pageAs(EmailQRCodeServicePage.class);
        emailQRCodeServicePage.getAccounts().click();
        int index = emailQRCodeServicePage.getAccountIndex(accountName);
        emailQRCodeServicePage.getOfficalAccount(index).click();
    }


    @Then("verify if email is sent successfully")
    public void verifyIfTextMsgSentSuccessfully1() {
        ActivateCampaignPage activateCampaignPage = factory.pageAs(ActivateCampaignPage.class);

        int i = 0;
        //wait for 0.5 minutes
        while (i < 10) {
            activateCampaignPage.page().waitForShort();
            //activateCampaignPage.getRefreshBtn().click();
            i = i + 1;
        }
    }
}

