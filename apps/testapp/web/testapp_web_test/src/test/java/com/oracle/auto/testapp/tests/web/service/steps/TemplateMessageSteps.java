package com.oracle.auto.testapp.tests.web.service.steps;

import com.oracle.auto.testapp.web.pages.MenuServicePages.*;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebAssert;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import com.oracle.auto.testapp.web.model.ActionResultNotificatoinTemplate;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by grcao on 8/24/2017.
 */

public class TemplateMessageSteps {
    private TestAppPageFactory factory;

    public TemplateMessageSteps() {
        factory = TestAppPageFactory.getInstance();
    }

    @Then("click template message link")
    public void clickTemplateMessageLinkServiceAccount() {
        MenuServicePage menuServicePage = factory.pageAs(MenuServicePage.class);
        menuServicePage.goToTemplateMessagePage();
        menuServicePage.waitForSecond(5);
    }


    @Then("click map button in template message page")
    public void clickEditTemplateMsgBtn() {
        TemplateMessagePage templateMessagePage = factory.pageAs(TemplateMessagePage.class);
        templateMessagePage.getMapBtn().waitForElementReady(30);
        templateMessagePage.getMapBtn().click();
        templateMessagePage.page().waitForShort();
    }

    @Then("input template name: $templateMsgName in the Template Message Con page")
    public void enableTemplateMessage(String templateMsgName) {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        templateMessageConPage.page().waitForSecond(3);
        templateMessageConPage.getTemplateName().setValue(templateMsgName);

    }

    @When("create a Action Result template: $actionResultNotification")
    public void createActionResultNotificationTemplate(ActionResultNotificatoinTemplate actionResult) {
        //活动审核结果通知
        clickEditTemplateMsgBtn();
        selectTemplateField(actionResult.templateField);
        enableTemplateMessage(actionResult.templateMsgName);

        inputTemplateTitle(actionResult.title);
        inputTemplateContents1(actionResult.bottomTitle);

        inputTemplateBottomLink(actionResult.link);
        selectIdentifierItem(actionResult.identifier);
        if (actionResult.appID != null && actionResult.pagePath != null) {
            inputAPPIDPagePath(actionResult.appID, actionResult.pagePath);
        }
    }

    @Then("input APP ID and page path")
    public void inputAppIDandPagePath() {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        String appID = PropertyConfiger.instance().getEnvData("service.template.message.APP.ID", "");
        String pagePath = PropertyConfiger.instance().getEnvData("service.template.message.page.path", "");

        templateMessageConPage.getAppID().setValue(appID);
        templateMessageConPage.getPagePath().setValue(pagePath);
    }

    @Then("select template field: $templateField")
    public void selectTemplateField(String templateField) {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        templateMessageConPage.getTemplateBox().waitForElementReady(10);
        templateMessageConPage.page().waitForShort();

        templateMessageConPage.getTemplateBoxArrow().click();
        templateMessageConPage.page().waitForShort();

        if (!(templateMessageConPage.getTemplateSearchBox().isReady())) {
            templateMessageConPage.getTemplateBoxArrow().click();
            templateMessageConPage.page().waitForShort();
        }

        templateMessageConPage.getTemplateSearchBox().setValue(templateField);
        templateMessageConPage.page().waitForSecond(1);
        int index = templateMessageConPage.getTemplateIndex(templateField);
        templateMessageConPage.getExceptedTemplate(index).click();

    }

    @Then("input template first text: $first")
    public void inputTemplateFirst(String frist) {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        templateMessageConPage.getFirst().setValue(frist);
    }

    @Then("input template title: $title")
    public void inputTemplateTitle(String title) {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        templateMessageConPage.getContentUpper().setValue(title);
    }

    //Used for 活动审核结果通知
    @Then("input template field contents: theme, time, result, $bottomTitle")
    public void inputTemplateContents1(String bottomTitle) {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        cal.add(Calendar.DATE, 4);
        date = cal.getTime();
        templateMessageConPage.getTheme().setValue(String.format("%tY", date) + "年" + "主題活动 Red");
        templateMessageConPage.getThemeColorBtn().click();
        templateMessageConPage.page().waitForSecond(2);
        templateMessageConPage.getColorRed().click();
        templateMessageConPage.getTemplateName().click();
        //  System.out.println(String.format("%tY", date) + "年" + String.format("%tm", date) + "月" + String.format("%td", date) + "日");
        templateMessageConPage.getTime().setValue(String.format("%tY", date) + "年" + String.format("%tm", date) + "月" + String.format("%td", date) + "日审核结果 Orange");
        templateMessageConPage.getTimeColorBtn().click();
        templateMessageConPage.page().waitForSecond(2);
        templateMessageConPage.getColorOrange().click();
        templateMessageConPage.getTemplateName().click();

        if (PropertyConfiger.instance().getEnvData("eloqua.wechat.service.account", "").equals("甲骨文客户体验演示")) {
            templateMessageConPage.getResult().setValue("Passed");
        }
        templateMessageConPage.getContentBottom().setValue(bottomTitle);
        templateMessageConPage.getResultColorBtn().click();
        templateMessageConPage.page().waitForSecond(2);
        templateMessageConPage.getColorBlue().click();
        templateMessageConPage.getTemplateName().click();
    }

    //Used for 用户登录提醒
    @Then("input template field contents: time, ip, $bottomTitle")
    public void inputTemplateContents2(String bottomTitle) {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        Calendar cal = Calendar.getInstance();
        templateMessageConPage.getTime2().setValue(cal.get(Calendar.YEAR) + "年");
        templateMessageConPage.getIp().setValue("128.16.200.100");
        templateMessageConPage.getContent2Bottom().setValue(bottomTitle);
    }

    @Then("input template bottom link: $link")
    public void inputTemplateBottomLink(String link) {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        templateMessageConPage.getTemplateLink().setValue(link);
    }

    @Then("select $identifier item for template message")
    public void selectIdentifierItem(String identifier) {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        templateMessageConPage.getIdentifierBtn().click();
        templateMessageConPage.page().waitForSecond(1);
        int index = templateMessageConPage.getIdentifierIndex(identifier);
        templateMessageConPage.getExceptedIdentifier(index).click();
    }

    public void inputAPPIDPagePath(String appID, String pagePath) {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        if (appID.length() != 0) {
            templateMessageConPage.getAppID().setValue(appID);
        }
        if (pagePath.length() != 0) {
            templateMessageConPage.getPagePath().setValue(pagePath);
        }
    }


    @Then("click preview template button")
    public void clickTemplatePreviewBtn() {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        templateMessageConPage.getPreviewBtn().click();
        templateMessageConPage.page().waitForSecond(5);
    }

    @Then("verify if QR code message appears for unsaved template message")
    public void verifyQRCodeAppears() {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        WebAssert.assertTrue(templateMessageConPage.getQRCode().isReady());
        templateMessageConPage.getCloseQRCode().click();
    }

    @Then("click save template button")
    public void clickTemplateSaveBtn() {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        templateMessageConPage.page().waitForSecond(2);
        templateMessageConPage.getSaveBtn().click();
        templateMessageConPage.page().waitForSecond(10);
    }

    @Then("verify template message $templateMsgName is deleted")
    public void verifyTemplateMessasgeIsDeleted(String templateMsgName) {
        TemplateMessagePage templateMessagePage = factory.pageAs(TemplateMessagePage.class);
        searchMsgForTemplateMessage(templateMsgName);
        int index = templateMessagePage.getMessageIndex(templateMsgName);
        WebAssert.assertEquals(-1, index);
    }

    @Then("verify template message $templateMsgName is existed")
    public void verifyTemplateMessasgeIsExisted(String templateMsgName) {
        TemplateMessagePage templateMessagePage = factory.pageAs(TemplateMessagePage.class);
        searchMsgForTemplateMessage(templateMsgName);
        int index = templateMessagePage.getMessageIndex(templateMsgName);
        WebAssert.assertEquals(templateMsgName, templateMessagePage.getExceptedMsgTitle(index).getText());
    }

    @Then("verify template message field is as excepted: $templateField")
    public void verifyTemplateMessasgeFiledIsAsExcepted(String templateField) {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        WebAssert.assertEquals(templateField, templateMessageConPage.getTemplateBox().getText());
    }

    @Then("verify if URL is as excepted: $URL")
    public void verifyURL(String URL) {
        TemplateMessageConPage templateMessageConPage = factory.pageAs(TemplateMessageConPage.class);
        String currentURL = templateMessageConPage.getTemplateLink().getValue();
        WebAssert.assertEquals(currentURL, URL);
    }

    //Auto Reply begin
    @Then("input message full name to search $templateMsgName")
    public void searchMsgForTemplateMessage(String templateMsgName) {
        TemplateMessagePage templateMessagePage = factory.pageAs(TemplateMessagePage.class);
        templateMessagePage.getSearchInputBox().click();
        templateMessagePage.getSearchInputBox().doClearText();
        templateMessagePage.getSearchInputBox().setValue(templateMsgName);
        templateMessagePage.page().waitForShort();
        // templateMessagePage.getSearchInputBox().sendEnterKeys();
    }

    @Then("click template message $templateMsgName edit button")
    public void clickATRichMediaEditBtn(String templateMsgName) {
        TemplateMessagePage templateMessagePage = factory.pageAs(TemplateMessagePage.class);
        searchMsgForTemplateMessage(templateMsgName);
        templateMessagePage.page().waitForSecond(2);
        int index = templateMessagePage.getMessageIndex(templateMsgName);
        templateMessagePage.getExceptedMsg(index).click();
        templateMessagePage.editExceptedMsg(index).click();
    }


    @Then("delete existed template message: $templateMsgName")
    public void deleteExistedTemplateMsg(String templateMsgName) {
        TemplateMessagePage templateMessagePage = factory.pageAs(TemplateMessagePage.class);
        searchMsgForTemplateMessage(templateMsgName);
        int index = templateMessagePage.getMessageIndex(templateMsgName);
        templateMessagePage.getExceptedMsg(index).click();
        templateMessagePage.page().waitForSecond(1);
        templateMessagePage.deleteExceptedMsg(index).click();
        templateMessagePage.page().waitForSecond(1);
        templateMessagePage.getConfirmDeleteBtn().click();
        templateMessagePage.page().waitForSecond(10);
    }

    @Then("clean automation template messages: $templateMsgName")
    public void deleteAutoTestTemplateMsgs(String templateMsgName) {
        TemplateMessagePage templateMessagePage = factory.pageAs(TemplateMessagePage.class);
        templateMessagePage.getExceptedMsg(0).waitForElementReady(60);
        int index = 1;
        while (index != -1) {
            index = templateMessagePage.getMessageIndex(templateMsgName);
            if (index != -1) {
                templateMessagePage.getExceptedMsg(index).click();
                templateMessagePage.page().waitForSecond(1);
                templateMessagePage.deleteExceptedMsg(index).click();
                templateMessagePage.page().waitForSecond(1);
                templateMessagePage.getConfirmDeleteBtn().click();
                templateMessagePage.page().waitForSecond(5);
            } else {
                break;
            }

        }
    }


    @Then("click preview button for existed template message: $templateMsgName")
    public void clickPreviewTemplateMsgBtn(String templateMsgName) {
        TemplateMessagePage templateMessagePage = factory.pageAs(TemplateMessagePage.class);
        searchMsgForTemplateMessage(templateMsgName);
        int index = templateMessagePage.getMessageIndex(templateMsgName);
        templateMessagePage.getExceptedMsg(index).click();
        templateMessagePage.previewExceptedMsg(index).click();
    }

    @Then("verify if QR code appears for template message: $templateMsgName")
    public void verifyIfQRCodeExisted(String templateMsgName) {
        TemplateMessagePage templateMessagePage = factory.pageAs(TemplateMessagePage.class);
        clickPreviewTemplateMsgBtn(templateMsgName);

        templateMessagePage.getViewQRCode().waitForElementReady(60);
        WebAssert.assertTrue(templateMessagePage.getViewQRCode().isReady());
        templateMessagePage.getCloseQRCode().click();
    }
}
