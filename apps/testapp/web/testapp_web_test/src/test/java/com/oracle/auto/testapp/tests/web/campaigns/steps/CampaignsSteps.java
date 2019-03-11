package com.oracle.auto.testapp.tests.web.campaigns.steps;

import com.oracle.auto.testapp.web.components.campaignscomps.LeftNavigator;
import com.oracle.auto.testapp.web.pages.*;
import com.oracle.auto.testapp.web.pages.AudiencePages.AudienceMainPage;
import com.oracle.auto.testapp.web.pages.CampaignPages.*;
import com.oracle.auto.web.pages.WebDriverEx;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebAssert;
import org.bytedeco.javacpp.opencv_ml;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by stepzhou on 6/30/2017.
 */

public class CampaignsSteps {
    private TestAppPageFactory factory;

    public CampaignsSteps() {
        factory = TestAppPageFactory.getInstance();
    }

    @When("choose a multiple steps blank campaign")
    public void chooseBlankCampaign() {
        moveCursorTo();
        clickCampageinsBtn();
        createMSCampaigns();
        selectBlankCampaign();
        clickChooseButton();
    }

    @Then("edit existed campaign: $campaignName")
    public void editExistedCampaign(String campaignName) {
        moveCursorTo();
        clickCampageinsBtn();
        searchAndClickCampaignByName(campaignName);

    }

    public void searchAndClickCampaignByName(String campaignName) {
        CampaignsPage campaignsPage = factory.pageAs(CampaignsPage.class);
        campaignsPage.page().waitForSecond(5);
        campaignsPage.getLeftNavigator().getSearchInputBox().setValue(campaignName);
        campaignsPage.getLeftNavigator().getExceptedCampaign(0).waitForElementReady(20);
        int index = campaignsPage.getLeftNavigator().getCampaignIndex(campaignName);
        campaignsPage.getLeftNavigator().getExceptedCampaign(index).moveToElement();
        campaignsPage.getLeftNavigator().getExceptedCampaign(index).doubleClick();
    }

    @When("move cursor to orchestration")
    public void moveCursorTo() {
        HomePage eloquaHomePage = factory.pageAs(HomePage.class);
        eloquaHomePage.getHomeTopNavigator().getOrchestrationMenu().getOrchestration().waitForElementReady(120);
        eloquaHomePage.getHomeTopNavigator().getOrchestrationMenu().getOrchestration().moveToElement();
    }

    @When("click campaigns button")
    public void clickCampageinsBtn() {
        HomePage eloquaHomePage = factory.pageAs(HomePage.class);
        eloquaHomePage.getHomeTopNavigator().getOrchestrationMenu().getCampaigns().click();
    }

    @When("create a multiple steps campaign")
    public void createMSCampaigns() {
        CampaignsPage campaignsPage = factory.pageAs(CampaignsPage.class);
        campaignsPage.getTopOptions().getCreateMSCampaign().click();
    }

    @Then("select blank campaign")
    public void selectBlankCampaign() {
        TemplateChooserPage templateChooserPage = factory.pageAs(TemplateChooserPage.class);
        templateChooserPage.page().waitForShort();
        templateChooserPage.getBlankCampaign().waitForElementReady(10);
        templateChooserPage.getBlankCampaign().click();
    }

    @Then("click choose button")
    public void clickChooseButton() {
        TemplateChooserPage templateChooserPage = factory.pageAs(TemplateChooserPage.class);
        templateChooserPage.getChoose().click();
    }

    @When("create a segment member with default segment")
    public void createASegmentMember() {
        clickSegmentMember();
        clickSegmentMemberEditor();
        testSelectSegment();
    }

    @When("choose segment member in campaign item list")
    public void clickSegmentMember() {
        String segmentMember = PropertyConfiger.instance().getEnvData("eloqua.campaign.segment.member.name", "");
        CampaignEditorPage campaignEditorPage = factory.pageAs(CampaignEditorPage.class);
        campaignEditorPage.getExceptedObject(0).waitForElementReady(60);
        selectOneItemInCampaignSteps(segmentMember, 680, 250);
    }

    @When("choose contact information synchronizer in campaign item list")
    public void clickWeChatContactInformationSync() {
        String wechatMsgSender = PropertyConfiger.instance().getEnvData("eloqua.campaign.contact.information.sync", "");
        selectOneItemInCampaignSteps(wechatMsgSender, 720, 450);
    }

    @When("choose wechat message sender in campaign item list")
    public void clickWeChatMsgSender() {
        String wechatMsgSender = PropertyConfiger.instance().getEnvData("eloqua.campaign.wechat.msg.sender.name", "");
        selectOneItemInCampaignSteps(wechatMsgSender, 720, 450);
    }

    @When("double click email btn in campaign item list")
    public void clickEmail() {
        String emailMember = PropertyConfiger.instance().getEnvData("eloqua.campaign.email.member.name", "");
        selectOneItemInCampaignSteps(emailMember, 720, 450);
    }

    public void selectOneItemInCampaignSteps(String itemName, int xOfferSet, int yOfferSet) {
        CampaignEditorPage campaignEditorPage = factory.pageAs(CampaignEditorPage.class);

        //double click to choose
        int index = campaignEditorPage.getObjectIndex(itemName);
        campaignEditorPage.getExceptedObject(index).doubleClick();
        campaignEditorPage.page().waitForShort();

        //drag and drop in drawer container
        index = campaignEditorPage.getDrawerItemIndex(itemName);
        campaignEditorPage.dragAndDropItems(index, xOfferSet, yOfferSet);
    }

    @When("scroll to the bottom")
    public void scrollToBottom() {
        CampaignEditorPage campaignEditorPage = factory.pageAs(CampaignEditorPage.class);
        campaignEditorPage.scrollToBottom();
    }

    @When("connect segment members and message sender")
    @Then("connect segment members and contact information sync")
    public void connect() {
        CampaignEditorPage campaignEditorPage = factory.pageAs(CampaignEditorPage.class);
        campaignEditorPage.connSegMemMesSender();
    }

    @Then("double click segment member in drawer container")
    public void clickSegmentMemberEditor() {
        String segmentMember = PropertyConfiger.instance().getEnvData("eloqua.campaign.segment.member.name", "");
        CampaignEditorPage campaignEditorPage = factory.pageAs(CampaignEditorPage.class);
        campaignEditorPage.page().waitForShort();
        int index = campaignEditorPage.getDrawerItemIndex(segmentMember);
        campaignEditorPage.getExceptedDrawerItem(index).doubleClick();
    }

    @Then("select a segment")
    public void testSelectSegment() {
        String segmentName = PropertyConfiger.instance().getEnvData("eloqua.campaign.segment.name", "");

        SegConfigurePage segConfigurePage = factory.pageAs(SegConfigurePage.class);
        //Segment name must be the same
        segConfigurePage.getSelectSegmentInput().setValue(segmentName);
        segConfigurePage.waitForSomeTime(6);
        int index = segConfigurePage.getSegmentIndex(segmentName);
        segConfigurePage.getExceptedSegment(index).click();

        //Click the other element to back the campaign editor page.
        CampaignEditorPage campaignEditorPage = factory.pageAs(CampaignEditorPage.class);
        campaignEditorPage.getSaveBtn().doubleClick();
    }

    @Then("double click wechat message sender in drawer container")
    public void doubleClickMessageSender() {
        String wechatMsgSender = PropertyConfiger.instance().getEnvData("eloqua.campaign.wechat.msg.sender.name", "");
        doubleClickItemInDrawerContainer(wechatMsgSender);
    }

    @Then("double click email in drawer container")
    public void doubleClickEmail() {
        String emailMember = PropertyConfiger.instance().getEnvData("eloqua.campaign.email.member.name", "");
        doubleClickItemInDrawerContainer(emailMember);
    }

    @Then("double click contact information in drawer container")
    public void doubleClickContactInformation() {
        String itemMember = PropertyConfiger.instance().getEnvData("eloqua.campaign.contact.information.sync", "");
        doubleClickItemInDrawerContainer(itemMember);
    }


    @Then("select a email with resend option")
    public void selectEmail() {
        String emailName = PropertyConfiger.instance().getEnvData("eloqua.campaign.email.name", "");
        EmailEditPage emailEditPage = factory.pageAs(EmailEditPage.class);

        //Email name must be the same
        emailEditPage.getSelectEmailInput().setValue(emailName);
        emailEditPage.waitForSomeTime(2);
        int index = emailEditPage.getEmailIndex(emailName);
        emailEditPage.getExceptedEmail(index).click();

        emailEditPage.getSendingOptionBtn().click();
        emailEditPage.waitForSomeTime(1);
        emailEditPage.getAllowEmailBox().check();

        //Click the other element to back the campaign editor page.
        CampaignEditorPage campaignEditorPage = factory.pageAs(CampaignEditorPage.class);
        campaignEditorPage.getSaveBtn().doubleClick();
    }

    @Then("choose email in drawer container: $emailName")
    public void selectEmail(String emailName) {
        EmailEditPage emailEditPage = factory.pageAs(EmailEditPage.class);

        //Email name must be the same
        emailEditPage.getSelectEmailInput().setValue(emailName);
        emailEditPage.waitForSomeTime(2);
        int index = emailEditPage.getEmailIndex(emailName);
        emailEditPage.getExceptedEmail(index).click();

        emailEditPage.getSendingOptionBtn().click();
        emailEditPage.waitForSomeTime(1);
        emailEditPage.getAllowEmailBox().check();

        //Click the other element to back the campaign editor page.
        CampaignEditorPage campaignEditorPage = factory.pageAs(CampaignEditorPage.class);
        campaignEditorPage.getSaveBtn().doubleClick();
    }

    public void doubleClickItemInDrawerContainer(String itemName) {
        CampaignEditorPage campaignEditorPage = factory.pageAs(CampaignEditorPage.class);
        campaignEditorPage.page().waitForSecond(10);
        int index = campaignEditorPage.getDrawerItemIndex(itemName);
        campaignEditorPage.page().waitForShort();
        campaignEditorPage.getExceptedDrawerItem(index).doubleClick();
    }

    @Then("click the configure cloud action button")
    public void configureCloudAction() {
        MessageSenderPage messageSenderPage = factory.pageAs(MessageSenderPage.class);
        messageSenderPage.getEditBtn().waitForElementReady(60);
        messageSenderPage.page().waitForSecond(5);
        messageSenderPage.getEditBtn().click();
    }

    @When("select customer message type")
    public void selectCustomerMessageType() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.page().waitForSecond(2);
        cloudActionConPage.getCustomerMsg().clickToCheck();
        cloudActionConPage.page().waitForShort();
    }

    @When("select subscription customer message type")
    public void selectCustomerMessageTypeSubscription() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.page().waitForSecond(2);
        cloudActionConPage.getCustomerMsgSub().clickToCheck();
    }

    @Then("verify if it is customer message type")
    public void verifyIsCustomerType() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.page().waitForShort();
        String selectedFlag = cloudActionConPage.getCustomerMsg().getTheAttribute("class");
        WebAssert.assertTrue("flag is: " + selectedFlag, selectedFlag.contains("oj-selected"));
        //  WebAssert.assertTrue("It is not customer message type", cloudActionConPage.getCustomerMsg().isChecked());
    }

    @Then("verify if it is subscription customer message type")
    public void verifyIsCustomerTypeSubscription() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.page().waitForShort();
        String selectedFlag = cloudActionConPage.getCustomerMsgSub().getTheAttribute("class");
        WebAssert.assertTrue("flag is: " + selectedFlag, selectedFlag.contains("oj-selected"));
        //  WebAssert.assertTrue("It is not customer message type", cloudActionConPage.getCustomerMsg().isChecked());
    }


    @When("select broadcast message type")
    public void selectBroadcastMessageType() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.getBroadcastMsg().clickToCheck();
    }

    @When("select subscription broadcast message type")
    public void selectBroadcastMessageTypeSubscription() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.getBroadcastMsgSub().clickToCheck();
    }

    @Then("verify if it is broadcast message type")
    public void verifyIsBroadcastType() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        String selectedFlag = cloudActionConPage.getBroadcastMsg().getTheAttribute("class");
        WebAssert.assertTrue("flag is: " + selectedFlag, (selectedFlag.contains("oj-enabled") || selectedFlag.contains("oj-selected")));
//        WebAssert.assertTrue("It is not broadcast message type", stat);
//        WebAssert.assertTrue("It is not broadcast message type", cloudActionConPage.getBroadcastMsg().isChecked());
    }

    @Then("verify if it is subscription broadcast message type")
    public void verifyIsBroadcastTypeSubscription() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        String selectedFlag = cloudActionConPage.getBroadcastMsgSub().getTheAttribute("class");
        WebAssert.assertTrue("flag is: " + selectedFlag, (selectedFlag.contains("oj-enabled") || selectedFlag.contains("oj-selected")));
//        WebAssert.assertTrue("It is not broadcast message type", stat);
//        WebAssert.assertTrue("It is not broadcast message type", cloudActionConPage.getBroadcastMsg().isChecked());
    }

    @When("select template message type")
    public void selectTemplateMessageType() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.getTemplateMsg().clickToCheck();
    }

    @Then("verify if it is template message type")
    public void verifyIsTemplateType() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.page().waitForShort();
        String selectedFlag = cloudActionConPage.getTemplateMsg().getTheAttribute("class");
        WebAssert.assertTrue("flag is: " + selectedFlag, selectedFlag.contains("oj-selected"));
        //WebAssert.assertTrue("It is not template message type", cloudActionConPage.getTemplateMsg().isChecked());
    }

    @When("select weChat official account $accountName")
    public void selectServiceAccount(String accountName) {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.page().switchToCloudContentConfigurationFrame();
        cloudActionConPage.page().waitForSecond(15);
        cloudActionConPage.getAccounts().click();
        cloudActionConPage.page().waitForSecond(5);
        int index = cloudActionConPage.getAccountIndex(accountName);
        cloudActionConPage.page().waitForSecond(2);
        cloudActionConPage.getOfficalAccount(index).click();
        cloudActionConPage.page().waitForSecond(2);
    }

    @Then("verify if it is weChat official account $accountName")
    public void verifyIsExceptedAccount(String accountName) {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.page().switchToCloudContentConfigurationFrame();
        cloudActionConPage.getNextBtn().waitForElementReady(120);
        String currentAccount = cloudActionConPage.getAccounts().getText();
        //WebAssert.assertEquals("Not excepted account: " + accountName + ", but now is: " + currentAccount, accountName, currentAccount);
        WebAssert.assertTrue("Not excepted account: " + accountName + ", but now is: " + currentAccount, currentAccount.contains(accountName));
    }

    @When("uncheck the all followers box")
    public void uncheckAllFollowersBox() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        //cloudActionConPage.getAllFollowers().click();
        cloudActionConPage.getAllFollowers().clickToUncheck();
    }

    @When("check the segment box")
    public void checkSegmentBox() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.getSegment().clickToCheck();
    }

    @When("check the all followers box")
    public void checkAllFollowersBox() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.getAllFollowers().clickToCheck();
        cloudActionConPage.page().waitForSecond(2);
        // cloudActionConPage.page().executeScriptEx("var element=BDD_jQuery(\"#sendToAll\")[0];element.click();");
    }

    @Then("check the tag box")
    public void checkTagBox() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.getTag().clickToCheck();
        cloudActionConPage.page().waitForSecond(2);
        // cloudActionConPage.page().executeScriptEx("var element=BDD_jQuery(\"#sendToAll\")[0];element.click();");
    }

    @Then("in action service verify if the tag existed: $tagName")
    public void checkIfTagExisted(String tagName) {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.getTagName(tagName).waitForElementReady(30);
        cloudActionConPage.getTagName(tagName).click();
        Assert.assertTrue("The tag is not existed", cloudActionConPage.getTagName(tagName).isPresent());
    }


    @Then("verify if it is to all followers")
    public void verifyIsToAllFollowers() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        // WebAssert.assertTrue("It is not to all followers", cloudActionConPage.getAllFollowers().isChecked());
        String selectedFlag = cloudActionConPage.getAllFollowersStatus().getTheAttribute("class");
        WebAssert.assertTrue(selectedFlag.contains("oj-selected"));
    }

    @Then("verify if it is not to all followers")
    public void verifyIsNotToAllFollowers() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        //WebAssert.assertFalse("It is to all followers", cloudActionConPage.getAllFollowers().isChecked());
        String selectedFlag = cloudActionConPage.getAllFollowers().getTheAttribute("class");
        WebAssert.assertFalse(selectedFlag.contains("oj-selected"));
    }

    @Then("verify if it is to segment contacts")
    public void verifyIsToSegmentContacts() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        //WebAssert.assertFalse("It is to all followers", cloudActionConPage.getAllFollowers().isChecked());
        String selectedFlag = cloudActionConPage.getSegment().getTheAttribute("class");
        WebAssert.assertTrue(selectedFlag.contains("oj-selected"));
    }

    @When("select the text message type")
    public void selectTextMessageType() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.page().waitForShort();
        cloudActionConPage.getTextMsg().clickToCheck();
        cloudActionConPage.page().waitForShort();
    }

    @Then("verify if it is text message type")
    public void verifyIsTextType() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        // WebAssert.assertTrue("It is not text message type", cloudActionConPage.getTextMsg().isChecked());
        String selectedFlag = cloudActionConPage.getTextMsg().getTheAttribute("class");
        WebAssert.assertTrue(selectedFlag.contains("oj-selected"));
    }

    @When("select the rich media message type")
    public void selectRichMediaMessageType() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.getRichMediaMsg().clickToCheck();
    }

    @Then("verify if it is rich media message type")
    public void verifyIsRichMediaType() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        String selectedFlag = cloudActionConPage.getRichMediaMsg().getTheAttribute("class");
        WebAssert.assertTrue(selectedFlag.contains("oj-selected"));
    }

    @When("select the ticket message type")
    public void selectTicketMessageType() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.getTicketMsg().clickToCheck();
    }

    @When("get ticket")
    public void getTicket() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.getTicketMedia(0).click();
    }

    @Then("click next button")
    public void clickNextButton() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.page().waitForShort();
        cloudActionConPage.getNextBtn().click();
        //cloudActionConPage.getByPassBtn().click();
        cloudActionConPage.page().waitForSecond(3);
    }

    @Then("uncheck byPass")
    public void uncheckByPass() {
        CloudActionConPage cloudActionConPage = factory.pageAs(CloudActionConPage.class);
        cloudActionConPage.getByPassBtn().waitForElementEnabled(10);
        cloudActionConPage.getByPassBtn().click();
        cloudActionConPage.page().waitForSecond(1);
    }

    //Rich media message content begin
    @When("select rich media content")
    public void selectRichMediaMessages() {
        RichMediaMessageConPage richMediaMessageConPage = factory.pageAs(RichMediaMessageConPage.class);
        // richMediaMessageConPage.getMediaNews().waitForElementEnabled(120);
        richMediaMessageConPage.getMediaNews().moveToElement();
        richMediaMessageConPage.getMediaNews().click();
    }

    @When("select rich media content: $mediaTitle")
    public void selectRichMediaMessages(String mediaTitle) {
        RichMediaMessageConPage richMediaMessageConPage = factory.pageAs(RichMediaMessageConPage.class);
        richMediaMessageConPage.page().waitForSecond(8);
        int index = richMediaMessageConPage.getNewsMediaIndex(mediaTitle);
        richMediaMessageConPage.getExceptedMedia(index).click();
    }

    @Then("verify if media is selected")
    public void verifyIfMediaSelectedOrNot() {
        RichMediaMessageConPage richMediaMessageConPage = factory.pageAs(RichMediaMessageConPage.class);
        richMediaMessageConPage.getMediaNews().moveToElement();
        boolean status = richMediaMessageConPage.getMediaNewsSelected().isReady();
        // status = richMediaMessageConPage.getMediaNewsSelected().isPresent();
        WebAssert.assertTrue(status);
    }

    @Then("confirm news media is selected")
    public void confirmNewsMediaSelected() {
        RichMediaMessageConPage richMediaMessageConPage = factory.pageAs(RichMediaMessageConPage.class);
        boolean status = richMediaMessageConPage.getMediaNewsSelected().isReady();
        WebAssert.assertTrue("News media is not selected", richMediaMessageConPage.getMediaNewsSelected().isReady());
    }

    @Then("save and input campaign name: $campaign")
    public void saveAndActiveCampaign(String campaignName) {
        closeActionConfPage();
        saveConfiguration();
        inputCampaignName(campaignName);
    }

    @Then("click submit rich media message")
    public void submitRichMediaMsg() {
        RichMediaMessageConPage richMediaMessageConPage = factory.pageAs(RichMediaMessageConPage.class);
        richMediaMessageConPage.page().waitForSecond(3);
        richMediaMessageConPage.getSubmitBtn().click();
    }

    @Then("verify if successful message appears")
    public void verifySuccessfulMsgAppears() {
        RichMediaMessageConPage richMediaMessageConPage = factory.pageAs(RichMediaMessageConPage.class);
        WebAssert.assertTrue(richMediaMessageConPage.successfulMsgIsVisibleOrNot());
        // richMediaMessageConPage.page().executeScript("document.getElementsByClassName(\"alert-success\")[0].style.display=\"block\"");
    }
    //Rich media message content end

    //Text message content begin
    @When("type the wechat message $content")
    public void typeMessage(String content) {
        MessageConPage messageConPage = factory.pageAs(MessageConPage.class);
        messageConPage.page().waitForShort();
        messageConPage.getTextArea().insertValue(content);
        messageConPage.page().waitForShort();
    }

    @Then("verify the text message content: $content")
    public void verifyTextMessageContent(String content) {
        MessageConPage messageConPage = factory.pageAs(MessageConPage.class);
        messageConPage.page().waitForShort();
        String currentContent = messageConPage.getTextArea().getValue();
        WebAssert.assertEquals("Excepted text message is: " + content + ", but now is: " + currentContent, content, currentContent);
    }

    @Then("verify text message content: $content, $insertMessage")
    public void verifyTextMessageContentWithInsert(String content, String insertMessage) {
        MessageConPage messageConPage = factory.pageAs(MessageConPage.class);
        messageConPage.page().waitForShort();
        String currentContent = messageConPage.getTextArea().getValue();
        // String excepted = content + insertMessage;
        WebAssert.assertContains("Excepted text message is: " + content + ", but now is: " + currentContent, content, currentContent);
        WebAssert.assertContains("Excepted text message is: " + insertMessage + ", but now is: " + currentContent, insertMessage, currentContent);

        // WebAssert.assertEquals("Excepted text message is: " + excepted + ", but now is: " + currentContent, excepted, currentContent);
    }

    @Then("verify text message contents: $content1, $insertMessage, $content2")
    public void verifyTextMessageContentsWithInsert(String content1, String insertMessage, String content2) {
        MessageConPage messageConPage = factory.pageAs(MessageConPage.class);
        messageConPage.page().waitForShort();
        String currentContent = messageConPage.getTextArea().getValue();
        //String excepted = content1 + insertMessage + content2;
        WebAssert.assertContains("Excepted text message is: " + content1 + ", but now is: " + currentContent, content1, currentContent);
        WebAssert.assertContains("Excepted text message is: " + insertMessage + ", but now is: " + currentContent, insertMessage, currentContent);
        WebAssert.assertContains("Excepted text message is: " + content2 + ", but now is: " + currentContent, content2, currentContent);
        //WebAssert.assertEquals("Excepted text message is: " + excepted + ", but now is: " + currentContent, excepted, currentContent);
    }

    @Then("select insert message $insertMessage")
    public void selectInsertMessage(String insertMessage) {
        MessageConPage messageConPage = factory.pageAs(MessageConPage.class);
        messageConPage.page().waitForShort();
        messageConPage.getSelectInsertMessageBox().click();
        messageConPage.getSearchMessageBox().setValue(insertMessage);
        messageConPage.page().waitForShort();
        int index = messageConPage.getExceptedMessageIndex(insertMessage);
        messageConPage.getExceptedMessage(index).click();
        messageConPage.getInsertButton().click();
        messageConPage.page().waitForShort();
    }

    @When("click the broadcast submit button")
    public void clickBroadcastSubmitButton() {
        MessageConPage messageConPage = factory.pageAs(MessageConPage.class);
        messageConPage.page().waitForSecond(2);
        messageConPage.getBroadcastSubmitBtn().click();
        messageConPage.page().waitForSecond(2);

    }

    @Then("click the customer submit button")
    @When("click the customer submit button")
    public void clickCustomerSubmitButton() {
        MessageConPage messageConPage = factory.pageAs(MessageConPage.class);
        messageConPage.page().waitForSecond(3);
        messageConPage.getCustomerSubmitBtn().click();
        messageConPage.page().waitForSecond(2);
    }

    @Then("close the cloud action configuration page")
    public void closeActionConfPage() {
        MessageConPage messageConPage = factory.pageAs(MessageConPage.class);
        messageConPage.page().switchToDefaultFrame();
        messageConPage.getCloseBtn().click();
        messageConPage.page().waitForSecond(2);
    }

    @When("click save button to save the configuration")
    public void saveConfiguration() {
        CampaignEditorPage campaignEditorPage = factory.pageAs(CampaignEditorPage.class);
        campaignEditorPage.getSaveBtn().doubleClick();
        campaignEditorPage.getSaveBtn().click();
        campaignEditorPage.page().waitForSecond(5);
    }
    //Text message content end

    //Template message content begin
    @When("search the template message $templateMsgName")
    public void searchTemplateMessage(String templateMsgName) {
        CampaignTemplateMessageConPage templateMessageConPage = factory.pageAs(CampaignTemplateMessageConPage.class);
        templateMessageConPage.getSearchBox().click();
        templateMessageConPage.getInputBox().setValue(templateMsgName);
        templateMessageConPage.page().waitForSecond(2);
        templateMessageConPage.getSearchBtn().click();
        templateMessageConPage.page().waitForSecond(2);
    }

    @Then("select excepted $templateMsgName message")
    public void selectExceptedTemplateMessage(String templateMsgName) {
        CampaignTemplateMessageConPage templateMessageConPage = factory.pageAs(CampaignTemplateMessageConPage.class);
        templateMessageConPage.page().waitForSecond(5);
        int index = templateMessageConPage.getExceptedMessageIndex(templateMsgName);
        templateMessageConPage.getExceptedMessage(index).click();
    }

    @Then("click submit template message")
    public void submitTemplateMsg() {
        CampaignTemplateMessageConPage templateMessageConPage = factory.pageAs(CampaignTemplateMessageConPage.class);
        templateMessageConPage.page().waitForSecond(2);
        templateMessageConPage.getSubmitBtn().click();
        templateMessageConPage.page().waitForSecond(2);
    }

    @Then("verify if template message is selected successfully")
    public void verifyIfSuccessfulMsgAppears() {
        CampaignTemplateMessageConPage templateMessageConPage = factory.pageAs(CampaignTemplateMessageConPage.class);
        WebAssert.assertTrue(templateMessageConPage.successfulMsgIsVisibleOrNot());
    }

    @Then("confirm template message is selected")
    public void confirmTemplateIsSelected() {
        CampaignTemplateMessageConPage templateMessageConPage = factory.pageAs(CampaignTemplateMessageConPage.class);
        WebAssert.assertTrue("No template message is not selected", templateMessageConPage.getTemplateSelectedIcon().isReady());
    }
    //Template message content end

    @Then("input the campaign name $campaign and save")
    public void inputCampaignName(String campaign) {
        CampaignChooserPage campaignChooserPage = factory.pageAs(CampaignChooserPage.class);
        campaignChooserPage.page().waitForSecond(2);
        campaignChooserPage.getCampaignName().setValue(campaign);
        campaignChooserPage.page().waitForSecond(2);
        campaignChooserPage.getSaveBtn().click();
        campaignChooserPage.getErrorMsgBox().waitForElementReady(20);
        campaignChooserPage.getErrorMsgBox().waitForElementNotReady(30);
        campaignChooserPage.page().waitForSecond(40);
    }

    @Then("activate the campaign")
    public void activateCampaignAction() {
        CampaignEditorPage campaignEditorPage = factory.pageAs(CampaignEditorPage.class);
        campaignEditorPage.getActivateBtn().click();

        ActivateCampaignPage activateCampaignPage = factory.pageAs(ActivateCampaignPage.class);
        activateCampaignPage.getActivateBtn().click();
        activateCampaignPage.getActivateConfirm().click();
        activateCampaignPage.page().waitForShort();
    }

    //@Then("verify is text message is sent successfully or not")
    public void verifyIfTextMsgSentSuccessfully() {
        ActivateCampaignPage activateCampaignPage = factory.pageAs(ActivateCampaignPage.class);
        int i = 0;
//        boolean test1 = activateCampaignPage.getContentsEnteredFlag().isVisible();
//        boolean test2 = activateCampaignPage.getContentsEnteredFlag().isPresent();
//        boolean test3 = activateCampaignPage.getContentsEnteredFlag().isReady();
//        boolean test4 =  activateCampaignPage.getContentsEnteredFlag().isEnabled();

        activateCampaignPage.page().waitForShort();
        activateCampaignPage.getRefreshBtn().click();

        activateCampaignPage.page().switchToCloudContentFrame();
        // activateCampaignPage.page().switchToCloudContentFrame();
        boolean test2 = activateCampaignPage.getContentsEnteredFlag().isPresent();

        activateCampaignPage.page().switchToDefaultFrame();

        activateCampaignPage.getRefreshBtn().click();
        activateCampaignPage.page().switchToChildWindow();
        boolean test = activateCampaignPage.getContentsEnteredFlag().isPresent();
        activateCampaignPage.page().switchToDefaultFrame();
        //wait at least 5 minutes for the contact enter flag appears in segment
        while (i <= (30 * 5)) {
            if (activateCampaignPage.getContentsEnteredFlag().isPresent()) {
                break;
            } else {
                activateCampaignPage.page().waitForShort();
                activateCampaignPage.getRefreshBtn().click();
                i++;
            }
        }
        WebAssert.assertTrue("The count flag doesn't appear in WeChat Sender", activateCampaignPage.getContentsEnteredFlag().isPresent());

        //wait at least 5 minutes for the count flag appears in WeChat Sender
        while (i <= (30 * 60 * 5)) {
            if (activateCampaignPage.getCountFlag().isPresent()) {

                break;
            } else {
                activateCampaignPage.page().waitMilliSecond();
                activateCampaignPage.getRefreshBtn().click();
                i++;
            }
        }
        WebAssert.assertTrue("The count flag doesn't appear in WeChat Sender", activateCampaignPage.getCountFlag().isPresent());
    }

    @Then("verify if test message is sent successfully")
    public void verifyIfTextMsgSentSuccessfully1() {
        ActivateCampaignPage activateCampaignPage = factory.pageAs(ActivateCampaignPage.class);

        int i = 0;
        //wait for 3 minutes
        while (i < 60) {
            activateCampaignPage.page().waitForShort();
            //activateCampaignPage.getRefreshBtn().click();
            i = i + 1;
        }
    }


    @Then("add a Tag: $tagName in contact information service")
    public void checkTaggingFollowersBox(String tagName) {
        ContactInfoSyncConPage contactInfoSyncConPage = factory.pageAs(ContactInfoSyncConPage.class);
        contactInfoSyncConPage.getTaggingFollowers().clickToCheck();
        contactInfoSyncConPage.getAddTagIcon().click();
        contactInfoSyncConPage.getTagName().setValue(tagName);
        contactInfoSyncConPage.getSaveBtn().click();
    }

    @Then("add duplicated tag in contact information service")
    public void addDuplicatedTag() {
        ContactInfoSyncConPage contactInfoSyncConPage = factory.pageAs(ContactInfoSyncConPage.class);
        contactInfoSyncConPage.getTaggingFollowers().clickToCheck();
        contactInfoSyncConPage.getTaggingFollowers().clickToCheck();
        String tagName = contactInfoSyncConPage.getFirstTagName().getText();
        checkTaggingFollowersBox(tagName);
    }

    @Then("track duplicated tag error message in contact information service")
    public void trackDuplicatedTagError() {
        ContactInfoSyncConPage contactInfoSyncConPage = factory.pageAs(ContactInfoSyncConPage.class);
        WebAssert.assertContains("","Failed to create tag with duplicated tag name, please rename the new tag.",contactInfoSyncConPage.getErrorMsg().getText());

    }

    @Then("select the removed Tag: $tagName in contact information service")
    public void selectTheTagRemovedforFollowers(String tagName) {
        ContactInfoSyncConPage contactInfoSyncConPage = factory.pageAs(ContactInfoSyncConPage.class);
        contactInfoSyncConPage.getRemoveTag().clickToCheck();
        contactInfoSyncConPage.getTagName(tagName).click();
    }


    @Then("in contact information service verify if tag is existed: $tagName")
    public void verifyTagExisted(String tagName) {
        ContactInfoSyncConPage contactInfoSyncConPage = factory.pageAs(ContactInfoSyncConPage.class);
        contactInfoSyncConPage.getTaggingFollowers().clickToCheck();
        contactInfoSyncConPage.getAddTagIcon().waitForElementReady(30);
        contactInfoSyncConPage.getTagName(tagName).click();
        Assert.assertEquals("Tag existed", contactInfoSyncConPage.getTagName(tagName).getText().equalsIgnoreCase(tagName));
    }


    @When("check remove tag box")
    public void checkRemoveTagBox() {
        ContactInfoSyncConPage contactInfoSyncConPage = factory.pageAs(ContactInfoSyncConPage.class);
        contactInfoSyncConPage.getTaggingFollowers().clickToCheck();
    }

}
