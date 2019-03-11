package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by grcao on 6/13/2018.
 */

@Component
public class MenuManagementConPage extends TestAppPageBase {

    private HTMLDiv searchTagArrow = new HTMLDiv("xpath=//div[@id='ojChoiceId_wx-tag-select']//a[contains(@class,'oj-select-arrow')]");
    private HTMLInput tagInputBox = new HTMLInput("xpath=//div[@id='oj-listbox-drop']//input[@title=\"Search field\"]");
    private HTMLDiv genderArrow = new HTMLDiv("xpath=//div[@id='oj-select-choice-wx-gender-select']/a[contains(@class,'oj-select-arrow')]");
    private HTMLDiv addMenuIcon = new HTMLDiv("xpath=//div[@class='wx-mobile-menu-preview ']//li[@class='wx-flex-center wx-menu-item']");
    private HTMLDiv addMenuOneSubMenuIcon = new HTMLDiv("xpath=//div[@id='subMenuSection0']//a[contains(@class,'wx-font-icon')]");

    private HTMLDiv inputMenuName = new HTMLDiv("xpath=//input[@id='inputMenuName']");

    private HTMLDiv webPageRadio = new HTMLDiv("xpath=//input[@id='view']/parent::*/parent::*");
    private HTMLDiv miniProgramRadio = new HTMLDiv("xpath=//input[@id='miniprogram']");

    //Text Message
    private HTMLDiv textArea = new HTMLDiv("xpath=//textarea[@id='wx-message-content']");
    private HTMLDiv searchFieldBox = new HTMLDiv("xpath=//input[@id='field-search-input']");

    //Web Page
    private HTMLInput webPageURL = new HTMLInput("xpath=//input[@id='inputPageAddress']");
    private HTMLDiv identifierArrow = new HTMLDiv("xpath=//div[@id='wx-menu-config-content']//a[contains(@class,'oj-select-arrow')]");

    private HTMLDiv saveAndPublishBtn = new HTMLDiv("id=buttonSubmit");

    private HTMLDiv filterTag = new HTMLDiv("id=ojChoiceId_wx-tag-select_selected");
    private HTMLDiv filterGender = new HTMLDiv("id=ojChoiceId_wx-gender-select_selected");
    private HTMLDiv subMenuText = new HTMLDiv("xpath=//div[@id='customMenuDetail']//label[text()='Sub-Menu Name']");
    private HTMLDiv savedTextMsgContent = new HTMLDiv("xpath=//textarea[@id='wx-message-content']");
    private HTMLDiv savedMenuOneName = new HTMLDiv("id=mainMenuItem0");

    private HTMLDiv backBtn = new HTMLDiv("xpath=//button[@id='buttonCancel']");

    //div[@id='clean-main-menu-dialog']//div[@class='oj-message-detail']
    private HTMLDiv clearFristLevelMenuMsg = new HTMLDiv("xpath=//div[@id='clean-main-menu-dialog']//div[@class='oj-message-detail']");
    private HTMLDiv confirmClearFristLevelMenuMsg = new HTMLDiv("xpath=//button[@id='btn-yes']");

    private HTMLDiv deleteMenuIcon = new HTMLDiv("xpath=//div[@id='customMenuDetail']//a[contains(@class, \"icon-func-trash\")]");
    private HTMLDiv confirmDeleteMenuMessage = new HTMLDiv("xpath=//div[@id='ojDialogWrapper-deleteMenuDialog_layer']//div[@class='oj-message-detail']/span[@id='main-panel']");
    private HTMLDiv confirmDeleteMenuIcon = new HTMLDiv("xpath=//div[@id='ojDialogWrapper-deleteMenuDialog_layer']//button[@id='btn-save']");
    private HTMLDiv addAtLeastOneMsg = new HTMLDiv("xpath=//wx-toast[@id='wx-toast']//span[contains(@class, \"wx-toast-message-detail\")]");


    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public HTMLDiv getSearchTagArrow() {
        searchTagArrow.setPage(page());
        return searchTagArrow;
    }

    public HTMLDiv getGenderArrow() {
        genderArrow.setPage(page());
        return genderArrow;
    }

    public HTMLInput getTagInputBox() {
        tagInputBox.setPage(page());
        return tagInputBox;
    }

    public HTMLDiv getTagItem(String tagName) {
        String locator = String.format("xpath=//div[contains(@aria-label,'%s')]", tagName);
        //div[@id='tag-preference']//label[contains(text(),"asdfasdf1234")]
        HTMLDiv tagItem = new HTMLDiv(locator);
        tagItem.setPage(page());
        return tagItem;
    }

    public HTMLDiv getGender(String itemName) {
        String locator = String.format("xpath=//ul[@id='oj-listbox-results-wx-gender-select']//div[contains(text(),'%s')]", itemName);
        //div[@id='tag-preference']//label[contains(text(),"asdfasdf1234")]
        HTMLDiv element = new HTMLDiv(locator);
        element.setPage(page());
        return element;
    }

    public HTMLDiv getMsgType(String itemName) {
        String locator = String.format("xpath=//li[contains(@data-content,'%s')]", itemName);
        //div[@id='tag-preference']//label[contains(text(),"asdfasdf1234")]
        HTMLDiv element = new HTMLDiv(locator);
        element.setPage(page());
        return element;
    }

    public HTMLDiv getAddMenuIcon() {
        addMenuIcon.setPage(page());
        return addMenuIcon;
    }

    public HTMLDiv getAddMenuOneSubMenuIcon() {
        addMenuOneSubMenuIcon.setPage(page());
        return addMenuOneSubMenuIcon;
    }

    public HTMLDiv getInputMenuName() {
        inputMenuName.setPage(page());
        return inputMenuName;
    }

    public HTMLDiv getTextArea() {
        textArea.setPage(page());
        return textArea;
    }

    public HTMLDiv getSearchFieldBox() {
        searchFieldBox.setPage(page());
        return searchFieldBox;
    }

    public HTMLDiv getField(String itemName) {
        String locator = String.format("xpath=//div[@id='field-list']//span[text()='%s']", itemName);
        //div[@id='tag-preference']//label[contains(text(),"asdfasdf1234")]
        HTMLDiv element = new HTMLDiv(locator);
        element.setPage(page());
        return element;
    }

    public HTMLDiv getSaveAndPublishBtn() {
        saveAndPublishBtn.setPage(page());
        return saveAndPublishBtn;
    }


    public void selectTag(String tagName) {
        page.waitForSecond(10);
        getSearchTagArrow().click();
        getTagInputBox().setValue(tagName);
        getTagItem(tagName).click();
    }

    //Male, Female, All
    public void selectGender(String itemName) {
        page.waitForSecond(2);
        getGenderArrow().click();
        getGender(itemName).click();
    }

    public void clickTextMessage() {
        getMsgType("text").click();
    }

    public void clickImageMessage() {
        getMsgType("image").click();
    }

    public void inputTextField(String text, String field) {
        getTextArea().click();
        getTextArea().setValue(text);
        getTextArea().click();
        getSearchFieldBox().setValue(field);
        getField(field).click();
    }

    public HTMLDiv getFilterTag() {
        filterTag.setPage(page());
        return filterTag;
    }

    public HTMLDiv getFilterGender() {
        filterGender.setPage(page());
        return filterGender;
    }

    public HTMLDiv getSubMenuText() {
        subMenuText.setPage(page());
        return subMenuText;
    }

    public HTMLDiv getSavedTextMsgContent() {
        savedTextMsgContent.setPage(page());
        return savedTextMsgContent;
    }

    public String getTextMsgAreaAttribute() {
        return getSavedTextMsgContent().getAttribute("data-bind");
    }

    public HTMLDiv getSavedMenuOneName() {
        savedMenuOneName.setPage(page());
        return savedMenuOneName;
    }

    public HTMLDiv getMenuOneSubMName(String itemName) {
        String locator = String.format("xpath=//ul[@id='subMenuItems0']//span[text()='%s']", itemName);
        //div[@id='tag-preference']//label[contains(text(),"asdfasdf1234")]
        HTMLDiv element = new HTMLDiv(locator);
        element.setPage(page());
        return element;
    }


    public HTMLDiv getBackBtn() {
        backBtn.setPage(page());
        return backBtn;
    }

    public void clickBackBtn() {
        getBackBtn().click();
    }


    public HTMLDiv getClearFristLevelMenuMsg() {
        clearFristLevelMenuMsg.setPage(page());
        return clearFristLevelMenuMsg;
    }

    public HTMLDiv getConfirmClearFristLevelMenuMsg() {
        confirmClearFristLevelMenuMsg.setPage(page());
        return confirmClearFristLevelMenuMsg;
    }

    public HTMLDiv getDeleteMenuIcon() {
        deleteMenuIcon.setPage(page());
        return deleteMenuIcon;
    }

    public HTMLDiv getConfirmDeleteMenuMessage() {
        confirmDeleteMenuMessage.setPage(page());
        return confirmDeleteMenuMessage;
    }

    public HTMLDiv getConfirmDeleteMenuIcon() {
        confirmDeleteMenuIcon.setPage(page());
        return confirmDeleteMenuIcon;
    }

    public HTMLDiv getAddAtLeastOneMsg() {
        addAtLeastOneMsg.setPage(page());
        return addAtLeastOneMsg;
    }

    public HTMLDiv getFirstLevelOneMenu(String itemName) {
        String locator = String.format("xpath=//div[@id='mainMenuItem0']//span[@title='%s']", itemName);
        HTMLDiv element = new HTMLDiv(locator);
        element.setPage(page());
        return element;
    }

    public HTMLDiv getFirstLevelSubMenu(String itemName) {
        String locator = String.format("xpath=//div[@id='subMenuSection0']//span[@title='%s']", itemName);
        //div[@id='tag-preference']//label[contains(text(),"asdfasdf1234")]
        HTMLDiv element = new HTMLDiv(locator);
        element.setPage(page());
        return element;
    }


    public HTMLDiv getWebPageRadio() {
        webPageRadio.setPage(page());
        return webPageRadio;
    }

    public HTMLDiv getMiniProgramRadio() {
        miniProgramRadio.setPage(page());
        return miniProgramRadio;
    }

    public HTMLInput getWebPageURL() {
        webPageURL.setPage(page());
        return webPageURL;
    }

    public void clickWebPageRadio() {
        getWebPageRadio().click();
        //getWebPageRadio().clickWithoutCheck();
    }

    public void clickMiniProgramRadio() {
        getMiniProgramRadio().click();
    }

    public HTMLDiv getIdentifierArrow() {
        identifierArrow.setPage(page());
        return identifierArrow;
    }

    public HTMLDiv getIdentifier(String itemName) {
        String locator = String.format("xpath=//div[@id='oj-listbox-drop']//div[text()='%s']", itemName);
        HTMLDiv element = new HTMLDiv(locator);
        element.setPage(page());
        return element;
    }
}
