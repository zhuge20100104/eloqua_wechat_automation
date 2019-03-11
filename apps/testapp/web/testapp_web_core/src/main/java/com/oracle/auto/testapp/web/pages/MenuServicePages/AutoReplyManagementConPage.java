package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import com.oracle.auto.web.comp.html.HTMLTextArea;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 7/27/2017.
 */

@Component
public class AutoReplyManagementConPage extends TestAppPageBase {

    private HTMLDiv autoReplyName;
    private HTMLDiv autoReplyKeyword;

    private HTMLDiv richMediaMsgBtn;
    private HTMLDiv textMsgBtn;
    private HTMLDiv activityBtn;
    private HTMLDiv chatBtn;

    private HTMLDiv bypassBox;
    private HTMLDiv richMedia;

    private HTMLTextArea textMessageConfigurationBox;
    private HTMLDiv insertFieldSearchBox;
    private HTMLDiv listedInsertItems;
    private HTMLDiv exceptedInsertItem;
    private HTMLDiv closeInsertField;

    private HTMLDiv saveBtn;
    private HTMLInput keyword;

//    public HTMLDiv getCloseInsertField(){
//        closeInsertField.setPage(page());
//        return closeInsertField;
////            WebElement ele = page.getDriver().findElement(null);
////            ele.click();
////            ele.sendKeys("");
//
//    }


    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    private HTMLDiv chatTestBtn = new HTMLDiv("BDD_jFindByAttr(\"label\",\"for\",\"qr-type-chat\")[0]");

    public HTMLDiv getChatTestBtn() {
        chatTestBtn.setPage(page());
        return chatTestBtn;
    }


    public HTMLDiv getChatBtn() {
        return chatBtn;
    }

    public void setChatBtn(HTMLDiv chatBtn) {
        this.chatBtn = chatBtn;
    }


    public void setKeyword(HTMLInput keyword) {
        this.keyword = keyword;
    }

    public HTMLInput getKeyword() {
        return keyword;
    }

    public void setAutoReplyName(HTMLDiv autoReplyName) {
        this.autoReplyName = autoReplyName;
    }

    public HTMLDiv getAutoReplyName() {
        return autoReplyName;
    }

    public void setAutoReplyKeyword(HTMLDiv autoReplyKeyword) {
        this.autoReplyKeyword = autoReplyKeyword;
    }

    public HTMLDiv getAutoReplyKeyword() {
        return autoReplyKeyword;
    }

    public void setRichMediaMsgBtn(HTMLDiv richMediaMsgBtn) {
        this.richMediaMsgBtn = richMediaMsgBtn;
    }

    public HTMLDiv getRichMediaMsgBtn() {
        return richMediaMsgBtn;
    }


    public void setTextMsgBtn(HTMLDiv textMsgBtn) {
        this.textMsgBtn = textMsgBtn;
    }

    public HTMLDiv getTextMsgBtn() {
        return textMsgBtn;
    }

    public void setActivityBtn(HTMLDiv activityBtn) {
        this.activityBtn = activityBtn;
    }

    public HTMLDiv getActivityBtn() {
        return activityBtn;
    }

    public void setBypassBox(HTMLDiv bypassBox) {
        this.bypassBox = bypassBox;
    }

    public HTMLDiv getBypassBox() {
        return bypassBox;
    }

    public HTMLDiv getRichMedia() {
        return richMedia;
    }

    public void setRichMedia(HTMLDiv richMedia) {
        this.richMedia = richMedia;
    }


    public HTMLDiv getRandomRichMedia() {
        Random random = new Random();
        int richMediaIndex = random.nextInt(3);
        richMedia.setLocator(String.format(richMedia.getRawLocator(), richMediaIndex));
        return richMedia;
    }

    public HTMLDiv getFirstRichMedia() {
        richMedia.setLocator(String.format(richMedia.getRawLocator(), 0));
        return richMedia;
    }

    public void setSaveBtn(HTMLDiv saveBtn) {
        this.saveBtn = saveBtn;
    }

    public HTMLDiv getSaveBtn() {
        return saveBtn;
    }


    public void setTextMessageConfigurationBox(HTMLTextArea textMessageConfigurationBox) {
        this.textMessageConfigurationBox = textMessageConfigurationBox;
    }

    public HTMLTextArea getTextMessageConfigurationBox() {
        return textMessageConfigurationBox;
    }

    public void setInsertFieldSearchBox(HTMLDiv insertFieldSearchBox) {
        this.insertFieldSearchBox = insertFieldSearchBox;
    }

    public HTMLDiv getInsertFieldSearchBox() {
        return insertFieldSearchBox;
    }

    public void setListedInsertItems(HTMLDiv listedInsertItems) {
        this.listedInsertItems = listedInsertItems;
    }

    public HTMLDiv getListedInsertItems() {
        return listedInsertItems;
    }

    public void setExceptedInsertItem(HTMLDiv exceptedInsertItem) {
        this.exceptedInsertItem = exceptedInsertItem;
    }

    public HTMLDiv getExceptedInsertItem() {
        return exceptedInsertItem;
    }

    public void setCloseInsertField(HTMLDiv closeInsertField) {
        this.closeInsertField = closeInsertField;
    }

    public HTMLDiv getCloseInsertField() {
        return closeInsertField;
    }

    public int getInsertItemIndex(String value) {
        List<String> items = getListsInsertTextItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public List<String> getListsInsertTextItems() {
        List<String> itemList = new ArrayList<>();
        int numbers = getListedInsertItems().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            itemList.add(getExceptedInsertItem(i).getText());
        }
        return itemList;
    }

    public HTMLDiv getExceptedInsertItem(int index) {
        exceptedInsertItem.setLocator(String.format(exceptedInsertItem.getRawLocator(), index));
        return exceptedInsertItem;
    }

    public void selectFieldItem(String insertItem) {
        getInsertFieldSearchBox().setValue(insertItem);
        int index = getInsertItemIndex(insertItem);
        getExceptedInsertItem(index).click();
        getCloseInsertField().click();
    }


    public void waitForSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
