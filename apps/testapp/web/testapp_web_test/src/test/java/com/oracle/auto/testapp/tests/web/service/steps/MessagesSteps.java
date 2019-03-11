package com.oracle.auto.testapp.tests.web.service.steps;


import com.oracle.auto.testapp.mobile.pages.MessagePage;
import com.oracle.auto.testapp.web.model.MessageArtical;
import com.oracle.auto.testapp.web.pages.CampaignPages.MessageConPage;
import com.oracle.auto.testapp.web.pages.HomePage;
import com.oracle.auto.testapp.web.pages.MenuServicePages.*;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebAssert;
import org.apache.bcel.generic.LAND;
import org.eclipse.jetty.util.log.Log;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import com.oracle.auto.testapp.mobile.pages.LandingPage;
import org.junit.Test;


/**
 * Created by grcao on 7/24/2017.
 */

public class MessagesSteps {
    private TestAppPageFactory factory;

    public MessagesSteps() {
        factory = TestAppPageFactory.getInstance();
    }

    @When("choose: $accountName")
    @Then("choose one account in menu service list: $accountName")
    public void SelectAccountinMenuService(String accountName) {
        clickAppCloudBtn();
        clickWeChatMsgBtn();
        selectAccountName(accountName);
    }

    @When("click the cloud app button in right panel")
    public void clickAppCloudBtn() {
        HomePage eloquaHomePage = factory.pageAs(HomePage.class);
        eloquaHomePage.getCloudAppBtn().waitForElementReady(30);
        eloquaHomePage.page().waitForSecond(2);
        eloquaHomePage.getCloudAppBtn().click();
        eloquaHomePage.getCloudAPPCloseBtn().waitForElementReady(10);
        if (!(eloquaHomePage.getCloudAPPCloseBtn().isPresent())) {
            eloquaHomePage.getCloudAppBtn().click();
        }
    }

    @Then("click weChat msg button")
    public void clickWeChatMsgBtn() {
        RightAppCloudPanelPage rightAppCloudPanelPage = factory.pageAs(RightAppCloudPanelPage.class);
        rightAppCloudPanelPage.getDesiredApp(PropertyConfiger.instance().getEnvData("eloqua.designed.app", "WeChat Message Gallery")).click();
        //rightAppCloudPanelPage.getWeChatMessageBtn().click();
        rightAppCloudPanelPage.waitForTime(20);
        rightAppCloudPanelPage.page().switchTabByIndex(1);
    }

    @Then("close menu service page")
    public void closeMenuServicePage() {
        MessagesPage messagesPage = factory.pageAs(MessagesPage.class);
        messagesPage.page().closeBrowserTab(1);
    }

    @Then("select account $accountName")
    public void selectAccountName(String accountName) {
        MessagesPage messagesPage = factory.pageAs(MessagesPage.class);
        if (!messagesPage.getCurrentAccountName().getText().equals(accountName)) {
            messagesPage.getAccountListBtn().click();
            messagesPage.getExceptedAccount(0).waitForElementReady(10);
            messagesPage.page().waitForSecond(3);
            int index = messagesPage.getAccountIndex(accountName);
            //  WebAssert.assertTrue("The index for " + accountName + " is: " + index, messagesPage.getExceptedAccount(index).isReady());
            messagesPage.getExceptedAccount(index).click();
            messagesPage.waitForSecond(3);
        }
    }

    @Then("verify account $accountName is not existed")
    public void verifyAccountNameNotExisted(String accountName) {
        MessagesPage messagesPage = factory.pageAs(MessagesPage.class);
        messagesPage.getAccountListBtn().click();
        messagesPage.page().waitForShort();
        int index = messagesPage.getAccountIndex(accountName);
        WebAssert.assertFalse(messagesPage.getExceptedAccount(index).isReady());
        messagesPage.page().waitForShort();
    }

    @Then("click auto reply management link")
    public void clickServiceAccountAutoReplyLink() {
        MenuServicePage menuServicePage = factory.pageAs(MenuServicePage.class);
        menuServicePage.goToAutoReplyPage();
        menuServicePage.waitForSecond(5);
    }

    //Auto Reply begin
    @Then("input message full name to search $autoReplyMsgName")
    public void searchMsgForAutoReplyManagement(String autoReplyMsgName) {
        AutoReplyManagementPage autoReplyManagementPage = factory.pageAs(AutoReplyManagementPage.class);
        autoReplyManagementPage.page().waitForSecond(3);
        autoReplyManagementPage.getSearchInputBox().click();
        autoReplyManagementPage.getSearchInputBox().doClearText();
        autoReplyManagementPage.getSearchInputBox().setValue(autoReplyMsgName);
        autoReplyManagementPage.waitForSecond(2);
    }

    @Then("click auto reply rich media message $autoReplyMsgName edit button")
    public void clickATRichMediaEditBtn(String autoReplyMsgName) {
        AutoReplyManagementPage autoReplyManagementPage = factory.pageAs(AutoReplyManagementPage.class);
        int index = autoReplyManagementPage.getMessageIndex(autoReplyMsgName);
        autoReplyManagementPage.editExceptedMsg(index).click();
        autoReplyManagementPage.page().waitForSecond(3);
    }

    @Then("click button to delete auto reply rich media message $autoReplyMsgName")
    public void clickATRichMediaDeleteBtn(String autoReplyMsgName) {
        AutoReplyManagementPage autoReplyManagementPage = factory.pageAs(AutoReplyManagementPage.class);
        int index = autoReplyManagementPage.getMessageIndex(autoReplyMsgName);
        autoReplyManagementPage.waitForSecond(2);
        autoReplyManagementPage.deleteExceptedMsg(index).click();
    }

    @Then("click view button to view QR code with auto reply rich media message $autoReplyMsgName")
    public void clickATRichMediaViewBtn(String autoReplyMsgName) {
        AutoReplyManagementPage autoReplyManagementPage = factory.pageAs(AutoReplyManagementPage.class);
        int index = autoReplyManagementPage.getMessageIndex(autoReplyMsgName);
        autoReplyManagementPage.viewExceptedMsg(index).click();
    }

    @Then("confirm delete auto reply rich media message")
    public void confirmDeleteATRichMediaMsg() {
        AutoReplyManagementPage autoReplyManagementPage = factory.pageAs(AutoReplyManagementPage.class);
        autoReplyManagementPage.waitForSecond(2);
        autoReplyManagementPage.getConfirmDeleteRMMsg().click();
    }


    @Then("click configure new auto reply button")
    public void clickConfigureNewATBtn() {
        AutoReplyManagementPage autoReplyManagementPage = factory.pageAs(AutoReplyManagementPage.class);
        autoReplyManagementPage.waitForSecond(2);
        autoReplyManagementPage.getConfigureBtn().click();
        autoReplyManagementPage.waitForSecond(3);
    }

    @Then("input auto reply msg name $autoReplyMsgName")
    public void inputAutoReplyMsgName(String autoReplyMsgName) {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.waitForSecond(2);
        autoReplyManagementConPage.getAutoReplyName().doClearText();
        autoReplyManagementConPage.getAutoReplyName().setValue(autoReplyMsgName);
    }

    @Then("input auto reply keyword $keyword")
    public void setAutoReplyKeyword(String keyword) {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.waitForSecond(2);
        autoReplyManagementConPage.getKeyword().setValue(keyword);
    }

    @Then("input auto reply msg keyword $autoReplyMsgKewWord")
    public void inputAutoRplyMsgKeyword(String autoReplyMsgKewWord) {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.getAutoReplyKeyword().setValue(autoReplyMsgKewWord);
    }

    @Then("click rich media msg button in auto reply message")
    public void clickRichMediaMsgButton() {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.getRichMediaMsgBtn().click();
    }

    @Then("click chat msg button in auto reply message")
    public void clickChatMsgButton() {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        // autoReplyManagementConPage.getChatBtn().click();
        autoReplyManagementConPage.getChatTestBtn().click();
    }

    @Then("click text message button in auto reply message")
    public void clickTextMsgButton() {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.getTextMsgBtn().click();
    }

    @Then("click activity message button in auto reply message")
    public void clickActivityMsgButton() {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.getActivityBtn().click();
        autoReplyManagementConPage.page().waitForSecond(2);
    }

    @Then("choose one rich media for auto reply message")
    public void chooseRichMediaARMsg() {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.waitForSecond(1);
        autoReplyManagementConPage.getRandomRichMedia().click();
        autoReplyManagementConPage.waitForSecond(2);
    }

    @Then("choose the first rich media for auto reply message")
    public void chooseFirstRichMediaARMsg() {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.waitForSecond(1);
        autoReplyManagementConPage.getFirstRichMedia().click();
        autoReplyManagementConPage.waitForSecond(2);
    }

    @Then("click save and close btn to save auto reply msg")
    public void saveAndCloseARMsg() {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.page().upScrollBar();
        autoReplyManagementConPage.getSaveBtn().waitForElementReady(5);
        autoReplyManagementConPage.getSaveBtn().click();
        autoReplyManagementConPage.page().waitForSecond(3);
    }

    @Then("verify auto reply message $autoReplyMsgName is existed")
    public void verifyARMessasgeIsExisted(String autoReplyMsgName) {
        AutoReplyManagementPage autoReplyManagementPage = factory.pageAs(AutoReplyManagementPage.class);
        searchMsgForAutoReplyManagement(autoReplyMsgName);
        int index = autoReplyManagementPage.getMessageIndex(autoReplyMsgName);
        WebAssert.assertEquals(autoReplyMsgName, autoReplyManagementPage.getExceptedMsg(index).getText());
    }

    @Then("verify auto reply message $autoReplyMsgName is not existed")
    public void verifyARMessasgeIsNotExisted(String autoReplyMsgName) {
        AutoReplyManagementPage autoReplyManagementPage = factory.pageAs(AutoReplyManagementPage.class);
        autoReplyManagementPage.waitForSecond(3);
        searchMsgForAutoReplyManagement(autoReplyMsgName);
        int index = autoReplyManagementPage.getMessageIndex(autoReplyMsgName);
        WebAssert.assertEquals(-1, index);
    }

    @Then("verify if QR code with auto reply rich media message appears")
    public void verifyARRichMediaQRCodeAppears() {
        AutoReplyManagementPage autoReplyManagementPage = factory.pageAs(AutoReplyManagementPage.class);
        autoReplyManagementPage.getViewQRCode().waitForElementReady(20);
        WebAssert.assertTrue(autoReplyManagementPage.getViewQRCode().isReady());
        autoReplyManagementPage.getCloseQRCode();
    }
    //Auto Reply end

    //text message begin
    @Then("input $text configuration for auto reply text message")
    public void inputTextConfigurationForARTextMessage(String text) {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.getTextMessageConfigurationBox().click();
        autoReplyManagementConPage.getTextMessageConfigurationBox().doClearText();
        autoReplyManagementConPage.getTextMessageConfigurationBox().insertValue(text);
    }

    @Then("insert $text configuration for auto reply text message")
    public void insertTextConfigurationForARTextMessage(String text) {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.getTextMessageConfigurationBox().click();
        autoReplyManagementConPage.getTextMessageConfigurationBox().insertValue(text);
    }

    @Then("insert $insertItem in insert field for auto reply text message")
    public void insertItemForARTextMessage(String insertItem) {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.page().waitForSecond(1);
        autoReplyManagementConPage.getTextMessageConfigurationBox().click();
        autoReplyManagementConPage.page().waitForSecond(1);

        autoReplyManagementConPage.getInsertFieldSearchBox().setValue(insertItem);
        int index = autoReplyManagementConPage.getInsertItemIndex(insertItem);
        autoReplyManagementConPage.getExceptedInsertItem(index).click();
        autoReplyManagementConPage.getCloseInsertField().click();
    }

    @Then("insert a line break for auto reply text message")
    public void insertLineBreakConfigurationForARTextMessage() {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.getTextMessageConfigurationBox().click();
        autoReplyManagementConPage.getTextMessageConfigurationBox().insertLineBreak();
    }

    @Then("close insert field for auto reply text message")
    public void closeInsertFieldForARTextMessage() {
        AutoReplyManagementConPage autoReplyManagementConPage = factory.pageAs(AutoReplyManagementConPage.class);
        autoReplyManagementConPage.getCloseInsertField().waitForElementReady(10);
        if (autoReplyManagementConPage.getCloseInsertField().isReady()) {
            autoReplyManagementConPage.getCloseInsertField().click();
        }
    }
    //Test message end

    //Activity message begin
    @Then("choose object item $objectItem")
    public void chooseObjectItem(String objectItem) {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        activityManagementConPage.page().waitForSecond(5);
        activityManagementConPage.getSelectObjectArrow().click();
        activityManagementConPage.page().waitForShort();
        activityManagementConPage.getObjectSearchBox().setValue(objectItem);
        activityManagementConPage.page().waitForShort();
        int index = activityManagementConPage.getObjectIndex(objectItem);
        activityManagementConPage.getExceptedObject(index).click();

        //if not the Contact Object, there will be alert popup
        if (activityManagementConPage.getConfirmObjectChangedBtn().isReady()) {
            activityManagementConPage.getConfirmObjectChangedBtn().click();
        }
    }

    @Then("click add rule button")
    public void clickAddRuleBtn() {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        activityManagementConPage.page().waitForSecond(6);
        //activityManagementConPage.getAddRuleBtn().click();

        activityManagementConPage.page().executeScriptEx("var element=BDD_jQuery(\".wx-plus-active-icon\")[0];element.click()");
        // activityManagementConPage.page().executeScriptEx("var element=BDD_jQuery(\"#add-rule\")[0];element.click()");

    }

    @Then("select condition value: $index, $conditionItem, $conditionSymbol, $conditionValue")
    public void addCondition(int index, String conditionItem, String conditionSymbol, String conditionValue) {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);

        //if add more conditions then click the condition button
        if (index != 0) {
            activityManagementConPage.getAddConditionBtn().click();
        }
        activityManagementConPage.page().downScrollBar();
        //choose object condition item
        activityManagementConPage.waitForSecond(2);
        activityManagementConPage.getConditionArrow(index).click();
        activityManagementConPage.waitForSecond(2);
        activityManagementConPage.getConditionSearchBox().setValue(conditionItem);
        activityManagementConPage.waitForSecond(2);
        int conditionIndex = activityManagementConPage.getConditionIndex(conditionItem);
        activityManagementConPage.waitForSecond(2);
        activityManagementConPage.getExceptedCondition(conditionIndex).click();

        //choose condition symbol
        activityManagementConPage.waitForSecond(2);
        activityManagementConPage.getSymbolArrow(index).click();
        activityManagementConPage.waitForSecond(2);
        activityManagementConPage.getConditionSymbolIndex(conditionSymbol).click();

        //set condition value
        if (activityManagementConPage.getConditionValue(index).isReady()) {
            activityManagementConPage.waitForSecond(2);
            activityManagementConPage.getConditionValue(index).setValue(conditionValue);
        }
    }

    @Then("add one update field: $fieldIte, $fieldValue")
    public void addOneFieldUpdate(String fieldItem, String fieldValue) {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        activityManagementConPage.getFieldUpdateBtn().click();
        activityManagementConPage.page().downScrollBar();

        //choose update field item
        activityManagementConPage.getFieldArrow().click();
        if (activityManagementConPage.getUpdateFieldSearchBox().isReady()) {
            activityManagementConPage.getUpdateFieldSearchBox().setValue(fieldItem);
        }
        activityManagementConPage.waitForSecond(2);
        int index = activityManagementConPage.getFieldIndex(fieldItem);
        activityManagementConPage.getExceptedField(index).click();

        //set field value
        activityManagementConPage.getFieldValue().setValue(fieldValue);
    }

    @Then("add text message sending: $msgType, $textMsg, $textInsertMsg")
    public void addRuleMsgSendingTextContent(String msgType, String textMsg, String textInsertMsg) {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        prepareToAddRuleMsgSendingContent(msgType);
        activityManagementConPage.getRuleMsgTextInputBox().setValue(textMsg);
        activityManagementConPage.getRuleMsgTextSearchBox().setValue(textInsertMsg);
        activityManagementConPage.page().waitForSecond(2);
        activityManagementConPage.getRuleMsgTextInputBox().click();
        activityManagementConPage.page().waitForSecond(2);
        activityManagementConPage.getRuleMsgTextExceptedInsertItem(textInsertMsg).click();
        activityManagementConPage.page().waitForShort();
        activityManagementConPage.getRuleMsgTextCloseBtn().click();
        activityManagementConPage.page().waitForShort();
    }

    @Then("add rich media message sending: $msgType")
    public void addRuleMsgSendingRichMediaContent(String msgType) {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        prepareToAddRuleMsgSendingContent(msgType);
        activityManagementConPage.getRandomRichMediaNews().click();
        activityManagementConPage.getRuleMsgTextCloseBtn().click();

    }

    @Then("add template message sending: $msgType")
    public void addRuleMsgSendingTemplateContent(String msgType) {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        prepareToAddRuleMsgSendingContent(msgType);
        activityManagementConPage.getRuleTemplateNews(0).waitForElementReady(60);
        activityManagementConPage.getRandomTemplateNews().click();
        activityManagementConPage.getRuleMsgTextCloseBtn().click();

    }


    public void prepareToAddRuleMsgSendingContent(String msgType) {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        activityManagementConPage.getRuleMsgSendBtn().click();
        activityManagementConPage.page().downScrollBar();

        activityManagementConPage.getRuleMsgTypeArrow().click();
        activityManagementConPage.getRuleMsgTypeIndex(msgType).click();
        activityManagementConPage.getRuleMsgEditBtn().click();
    }

    @Then("add default rule text message sending: $msgType, $textMsg, $textInsertMsg")
    public void addDefaultRuleMsgSendingTextContent(String msgType, String textMsg, String textInsertMsg) {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        prepareToAddDefaultRuleMsgSendingContent(msgType);
        activityManagementConPage.page().downScrollBar();
        activityManagementConPage.getDefaultRuleMsgTextInputBox().setValue(textMsg);

        activityManagementConPage.getDefaultRuleMsgTextSearchBox().setValue(textInsertMsg);
        activityManagementConPage.waitForSecond(2);
        activityManagementConPage.getDefaultRuleMsgTextExceptedInsertItem(textInsertMsg).click();

        activityManagementConPage.getDefaultRuleMsgTextCloseBtn().click();

    }

    @Then("add default rule rich media message sending: $msgType")
    public void addDefaultRuleMsgSendingRichMediaContent(String msgType) {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        prepareToAddDefaultRuleMsgSendingContent(msgType);
        activityManagementConPage.getRandomRichMediaNews().click();
        activityManagementConPage.getRuleMsgTextCloseBtn().click();

    }

    @Then("add default rule template message sending: $msgType")
    public void addDefaultRuleMsgSendingTemplateContent(String msgType) {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        prepareToAddDefaultRuleMsgSendingContent(msgType);
        activityManagementConPage.getRuleTemplateNews(0).waitForElementReady(60);
        activityManagementConPage.getRandomTemplateNews().click();
        activityManagementConPage.getRuleMsgTextCloseBtn().click();

    }

    public void prepareToAddDefaultRuleMsgSendingContent(String msgType) {
        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        activityManagementConPage.getDefaultRuleMsgSendBtn().click();

        activityManagementConPage.getDefaultRuleMsgTypeArrow().click();
        activityManagementConPage.getDefaultRuleMsgTypeIndex(msgType).click();
        activityManagementConPage.getDefaultRuleMsgEditBtn().click();


    }


    //Activity message end


    //Message begin
    @Then("click auto reply rich media message edit button")
    public void editARRichMediaMsg() {
        AutoReplyManagementPage autoReplyManagementPage = factory.pageAs(AutoReplyManagementPage.class);
        autoReplyManagementPage.getRMEditBtn().click();
        autoReplyManagementPage.waitForSecond(2);
    }

    @Then("edit existed message")
    public void selectExistedMesssage() {
        MessagesPage messagesPage = factory.pageAs(MessagesPage.class);

        messagesPage.getMsgTitle().waitForElementReady(10);
        messagesPage.page().executeScript("document.getElementsByClassName(\"wx-icon-bar\")[0].style.display=\"flex\"");
        messagesPage.getEditMsgBtn().click();
    }

    @Then("delete existed message")
    public void deleteExistedMsg() {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.getExceptedMsgBox(0).waitForElementReady(10);
        messagesConPage.getExceptedMsgBox(0).click();
        //messagesConPage.page().executeScript("document.getElementsByClassName(\"wx-icon-bar\")[0].style.display=\"flex\"");
        messagesConPage.deleteExceptedMsg(0).click();
        messagesConPage.getConfirmDeleteBtn().click();
    }

    @Then("delete all unused existed messages: $msgTitle")
    public void deleteUnusedExistedMsgs(String msgTitle) {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.getExceptedMsgBox(0).waitForElementReady(60);
        int index = 1;
        while (index != -1) {
            index = messagesConPage.getOneMessageIndex(msgTitle);
            if (index != -1) {
                messagesConPage.getExceptedMsgBox(index).click();
                messagesConPage.deleteExceptedMsg(index).click();
                messagesConPage.page().waitForSecond(1);
                messagesConPage.getConfirmDeleteBtn().click();
                messagesConPage.page().waitForSecond(5);
            } else {
                break;
            }

        }
    }

    @Then("preview existed service account message")
    public void previewServiceAccountMsg() {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.getExceptedMsgBox(0).waitForElementReady(10);
        messagesConPage.getExceptedMsgBox(0).click();
        messagesConPage.getPreviewBtn().click();
        messagesConPage.waitForSecond(2);
    }

    @Then("verify if QR code appears")
    public void verifyIfQRCodeExisted() {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        WebAssert.assertTrue(messagesConPage.getPreviewQR().isReady());
        messagesConPage.getPreviewClose().click();
    }

    @Then("click Message Page")
    public void goToMessagePage() {
        MenuServicePage menuServicePage = factory.pageAs(MenuServicePage.class);
        menuServicePage.goToMessagePage();
    }

    @Then("click new message button")
    public void clickNewMsgBtn() {
        MessagesPage messagesPage = factory.pageAs(MessagesPage.class);
        messagesPage.getNewMsgBtn().click();
        messagesPage.page().waitForShort();
    }

    @Then("edit message Link and identifier: $msgTitle, $contentURL, $identifier")
    public void eidtLinkandIdentifier(String msgTitle, String contentURL, String identifier) {
        MessagesPage messagesPage = factory.pageAs(MessagesPage.class);
        messagesPage.getExceptedMessageItem(msgTitle).click();
        messagesPage.getExceptedMessageLinkIcon(msgTitle).click();
        messagesPage.getContentURL().setValue(contentURL);
        selectIdentifierItem(identifier);
        messagesPage.getEditPageSave().click();
    }

    @Then("click article item to edit Article $index")
    public void clickArticleItemBtn(int index) {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.waitForSecond(2);
        messagesConPage.getArticleItem(index).click();
        messagesConPage.waitForSecond(2);
    }

    @Then("click article button to add new article")
    public void clickNewArticleBtn() {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.getNewArticle().click();
    }

    @Then("create article: $articleContent")
    public void createAtricle(MessageArtical articalContent) {
        inputMsgTitle(articalContent.title);
        inputMsgAuthor(articalContent.author);
        inputContentInFrame(articalContent.content);
        inputMsgContentURL(articalContent.contentURL);
        if (articalContent.accountName.equals(PropertyConfiger.instance().getEnvData("eloqua.wechat.service.account", ""))) {
            selectIdentifierItem(articalContent.identifier);
        }
        selectRichMediaMsg();

    }

    @Then("create new message with articles: $index, $title, $author, $content, $contentURL, $identifier")
    public void addNewMessage(int index, String title, String author, String content, String contentURL, String identifier){
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        if (index==0){
            clickNewMsgBtn();
        }else {
            clickNewArticleBtn();
            clickArticleItemBtn(index);
        }
        inputMsgTitle(title);
        inputMsgAuthor(author);
        inputContentInFrame(content);
        inputMsgContentURL(contentURL);
        selectIdentifierItem(identifier);
        selectRichMediaMsg();
    }

    @Then("message $title input firstly")
    public void inputMsgTitle(String title) {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.getMsgTitle().click();
        messagesConPage.getMsgTitle().setValue(title);
    }

    @Then("secondly input message $author")
    public void inputMsgAuthor(String author) {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        //   messagesConPage.page().executeScriptEx("var element=BDD_jQuery(\"#author-editor2\")[0];var para=document.createElement('p');var node=document.createTextNode(\"grace test\");para.appendChild(node);element.appendChild(para)");
        messagesConPage.getMsgAuthor().click();
        messagesConPage.getMsgAuthor().setValue(author);
    }

    public String getLandingPageURL(String contentURL) {
        String landingPage;
        if (contentURL.contains("%service.account.JS.landing.page%")) {
            landingPage = PropertyConfiger.instance().getEnvData("%service.account.JS.landing.page%", "");
        } else if (contentURL.contains("SubscriptionAccountLandingPage")) {
            landingPage = PropertyConfiger.instance().getEnvData("subscription.account.JS.landing.page", "");
        } else {
            landingPage = contentURL;
        }
        return landingPage;
    }



    @Then("input message content URL $contentURL")
    public void inputMsgContentURL(String contentURL) {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        String URL = PropertyConfiger.instance().getEnvData("service.account.JS.landing.page", "");
        messagesConPage.getMsgContentURL().moveToElement();
        messagesConPage.getMsgContentURL().setValue(contentURL);

    }

    @Then("select $identifier item")
    public void selectIdentifierItem(String identifier) {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.getIdentifierBtn().click();
        messagesConPage.waitForSecond(2);
        int index = messagesConPage.getIdentifierIndex(identifier);
        messagesConPage.getExceptedIdentifier(index).click();
    }

    @Then("select rich media content on the popup cover page")
    public void selectRichMediaMsg() {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.page().executeScriptEx("var element=BDD_jQuery(\".wx-article-title-image-action\")[0];element.click()");
        messagesConPage.waitForSecond(5);
        messagesConPage.getRandomRichMedia().click();
        messagesConPage.getOkBtn().click();
    }

    @Then("choose only weChat followers")
    public void chooseOnlyFollowers() {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.getCommentsBtn().check();
        messagesConPage.getWeChatFollowers().check();
    }

    @Then("choose all weChat users")
    public void chooseAllUsers() {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.getCommentsBtn().check();
        messagesConPage.getAllUsers().check();
    }

    @Then("input content in frame $content")
    public void inputContentInFrame(String contents) {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.waitForSecond(2);
        String addContentScript = "var para=document.createElement('p');var node=document.createTextNode('" + contents + "');para.appendChild(node);var element=CKEDITOR.instances['content_editor3'].document.$.getElementsByClassName('cke_editable')[0];element.appendChild(para)";
        //String addContentScript = "var para=document.createElement('p');var node=document.createTextNode('" + contents + "');para.appendChild(node);var element=CKEDITOR.instances['editor-welcome-message'].document.$.getElementsByClassName('cke_editable')[0];element.innerHTML='';element.appendChild(para)";
        messagesConPage.page().executeScriptEx(addContentScript);
        messagesConPage.waitForSecond(2);
    }

    @Then("click save button")
    public void clickSaveBtn() {
        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.getSaveBtn().click();
    }

    @Then("verify message $title is existed")
    public void verifyMsgTitleIsExisted(String msgTitle) {
        MessagesPage messagesPage = factory.pageAs(MessagesPage.class);
        String currentTitle = messagesPage.getMsgTitle().getText();
        WebAssert.assertEquals(msgTitle, currentTitle);
    }

    @Then("verify $messageName, $contentURL and $identifier are as excepted")
    public void verifyMsgContentURLIdentifier(String messageName, String contentURL, String identifier) {
        MessagesPage messagePage = factory.pageAs(MessagesPage.class);
        messagePage.getExceptedMessageItem(messageName).click();
        messagePage.getExceptedMessageEditIcon(messageName).click();

        String asdf = messagePage.getIdentifier().getText();
        // String saa =  messagePage.getContentURL().getValue();
        //  WebAssert.assertTrue(messagesConPage.getMsgContentURL().getText().equals(contentURL));
        WebAssert.assertTrue(messagePage.getIdentifier().getText().equals(identifier));

    }
    //Messages end


    //Chat begin
    @Then("choose form: $formName to load and select openID")
    public void loadFormAndChooseOpenID(String formName) {
        loadForm(formName);
        chooseOpenID();
    }

    @Then("load form: $formName")
    public void loadForm(String formName) {
        AutoReplyChatManagementConPage autoReplyChatManagementConPage = factory.pageAs(AutoReplyChatManagementConPage.class);
        autoReplyChatManagementConPage.page().waitForSecond(5);
        autoReplyChatManagementConPage.getFormBox().waitForElementReady(20);
        autoReplyChatManagementConPage.getFormBox().click();
        autoReplyChatManagementConPage.getInputForm().setValue(formName);
        int index = autoReplyChatManagementConPage.getFormIndex(formName);
        autoReplyChatManagementConPage.getExceptedForm(index).click();
        if (autoReplyChatManagementConPage.getConfirmReload().isVisible()) {
            autoReplyChatManagementConPage.getConfirmReload().click();
        }
        autoReplyChatManagementConPage.getLoadBtn().click();
        autoReplyChatManagementConPage.page().waitForSecond(3);
    }

    @Then("confirm reload form")
    public void confirmLoadForm() {
        AutoReplyChatManagementConPage autoReplyChatManagementConPage = factory.pageAs(AutoReplyChatManagementConPage.class);
        autoReplyChatManagementConPage.getConfirmReload().click();
    }

    @Then("choose openID")
    public void chooseOpenID() {
        AutoReplyChatManagementConPage autoReplyChatManagementConPage = factory.pageAs(AutoReplyChatManagementConPage.class);
        autoReplyChatManagementConPage.openIDRadio().waitForElementReady(10);
        autoReplyChatManagementConPage.openIDRadio().click();
    }

    @Then("choose $minutes and input error message: $errorMsg")
    public void selectOneMinutesandInputErrorMessage(String minutes, String errorMsg) {
        AutoReplyChatManagementConPage autoReplyChatManagementConPage = factory.pageAs(AutoReplyChatManagementConPage.class);
        String time = minutes.toLowerCase();
        if (time.contains("one")) {
            autoReplyChatManagementConPage.getMinutesTimeOut(60).click();
        } else if (time.contains("two")) {
            autoReplyChatManagementConPage.getMinutesTimeOut(60 * 2).click();
        } else if (time.contains("three")) {
            autoReplyChatManagementConPage.getMinutesTimeOut(60 * 3).click();
        } else if (time.contains("four")) {
            autoReplyChatManagementConPage.getMinutesTimeOut(60 * 4).click();
        } else if (time.contains("five")) {
            autoReplyChatManagementConPage.getMinutesTimeOut(60 * 5).click();
        }

        autoReplyChatManagementConPage.getErrorMsgTimeOut().setValue(minutes + " " + errorMsg);

    }

    @Then("add Thanks message with text type: $textMsg, $textInsertMsg")
    public void addThanksMsgTextContent(String textMsg, String textInsertMsg) {
        AutoReplyChatManagementConPage autoReplyChatManagementConPage = factory.pageAs(AutoReplyChatManagementConPage.class);
        autoReplyChatManagementConPage.getThanksMsgArrow().click();
        autoReplyChatManagementConPage.getMsgType("Text Message").click();
        autoReplyChatManagementConPage.getThanksMsgEdit().click();

        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        //activityManagementConPage.page().downScrollBar();
        activityManagementConPage.getDefaultRuleMsgTextInputBox().setValue(textMsg);
        activityManagementConPage.getDefaultRuleMsgTextSearchBox().setValue(textInsertMsg);
        activityManagementConPage.waitForSecond(2);
        activityManagementConPage.getDefaultRuleMsgTextExceptedInsertItem(textInsertMsg).click();
        activityManagementConPage.getDefaultRuleMsgTextCloseBtn().click();

    }

    @Then("add Submission message with text type: $textMsg, $textInsertMsg")
    public void addSubmissionTextMessage(String textMsg, String textInsertMsg) {
        AutoReplyChatManagementConPage autoReplyChatManagementConPage = factory.pageAs(AutoReplyChatManagementConPage.class);
        autoReplyChatManagementConPage.page().waitForSecond(3);
        autoReplyChatManagementConPage.page().downScrollBar();
        autoReplyChatManagementConPage.getSubmissionMsgArrow().click();
        autoReplyChatManagementConPage.getMsgType("Text Message").click();
        autoReplyChatManagementConPage.getSubmissionMsgEdit().click();

        ActivityManagementConPage activityManagementConPage = factory.pageAs(ActivityManagementConPage.class);
        activityManagementConPage.getDefaultRuleMsgTextInputBox().setValue(textMsg);
        activityManagementConPage.getDefaultRuleMsgTextSearchBox().setValue(textInsertMsg);
        activityManagementConPage.waitForSecond(2);
        activityManagementConPage.getDefaultRuleMsgTextExceptedInsertItem(textInsertMsg).click();
        activityManagementConPage.getDefaultRuleMsgTextCloseBtn().click();

    }

    @Then("Verify error messages appear for Chat content")
    public void verifyChatErrorMsg() {
        AutoReplyChatManagementConPage autoReplyChatManagementConPage = factory.pageAs(AutoReplyChatManagementConPage.class);
        String errorMessages = autoReplyChatManagementConPage.getChatErrorMessage().getText();
        WebAssert.assertTrue("", errorMessages.contains("Error"));
        WebAssert.assertTrue("", errorMessages.contains("Please check one form field as WeChat Open ID"));
        WebAssert.assertTrue("", errorMessages.contains("Please enter the error message for time out"));
        WebAssert.assertTrue("", errorMessages.contains("Please select a thanks message for complete"));
        WebAssert.assertTrue("", errorMessages.contains("Please select a message for submission error"));
    }
    //Chat end

}
