package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLCheckbox;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLTextArea;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 7/23/2017.
 */

@Component
public class MessagesConPage extends TestAppPageBase {
    private HTMLDiv newArticle;
    private HTMLDiv msgTitle;
    private HTMLTextArea msgAuthor;
    private HTMLDiv msgContentURL;
    private HTMLDiv contentArea;
    private HTMLDiv identifierBtn;
    private HTMLDiv identifierList;
    private HTMLDiv exceptedIdentifier;
    private HTMLDiv articleItem;

    private HTMLDiv selectCover;
    private HTMLDiv richMedia;
    private HTMLDiv okBtn;

    private HTMLCheckbox commentsBtn;
    private HTMLCheckbox allUsers;
    private HTMLCheckbox weChatFollowers;


    private HTMLDiv saveBtn;
    private HTMLDiv deleteBtn;
    private HTMLDiv previewBtn;
    private HTMLDiv previewQR;
    private HTMLDiv previewClose;

    private HTMLDiv confirmDeleteBtn;

    private HTMLDiv listedMsgs;
    private HTMLDiv exceptedMsg;
    private HTMLDiv msgsList;


    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public HTMLTextArea getIdentifierText() {
        HTMLTextArea identifierText = new HTMLTextArea("xpath=//div[contains(@class,'wx-flex-row')]//div[@id='oj-select-choice-basicSelect']");
        identifierText.setPage(page());
        return identifierText;
    }

    public HTMLDiv getMsgsList() {
        return msgsList;
    }

    public void setMsgsList(HTMLDiv msgsList) {
        this.msgsList = msgsList;
    }

    public HTMLDiv getExceptedMsgBox(int index) {
        msgsList.setLocator(String.format(msgsList.getRawLocator(), index));
        return msgsList;
    }

    public void setNewArticle(HTMLDiv newArticle) {
        this.newArticle = newArticle;
    }

    public HTMLDiv getNewArticle() {
        return newArticle;
    }

    public void setMsgTitle(HTMLDiv msgTitle) {
        this.msgTitle = msgTitle;
    }

    public HTMLDiv getMsgTitle() {
        return msgTitle;
    }

    public void setMsgAuthor(HTMLTextArea msgAuthor) {
        this.msgAuthor = msgAuthor;
    }

    public HTMLTextArea getMsgAuthor() {
        return msgAuthor;
    }

    public void setMsgContentURL(HTMLDiv msgContentURL) {
        this.msgContentURL = msgContentURL;
    }

    public HTMLDiv getMsgContentURL() {
        return msgContentURL;
    }

    public void setSelectCover(HTMLDiv selectCover) {
        this.selectCover = selectCover;
    }

    public HTMLDiv getSelectCover() {
        return selectCover;
    }

    public void setRichMedia(HTMLDiv richMedia) {
        this.richMedia = richMedia;
    }

    public HTMLDiv getRichMedia() {
        return richMedia;
    }

    public HTMLDiv getRandomRichMedia() {
        Random random = new Random();
        int richMediaIndex = random.nextInt(5);
        richMedia.setLocator(String.format(richMedia.getRawLocator(), richMediaIndex));
        return richMedia;
    }

    public void setOkBtn(HTMLDiv okBtn) {
        this.okBtn = okBtn;
    }

    public HTMLDiv getOkBtn() {
        return okBtn;
    }

    public HTMLCheckbox getCommentsBtn() {
        return commentsBtn;
    }

    public void setCommentsBtn(HTMLCheckbox commentsBtn) {
        this.commentsBtn = commentsBtn;
    }

    public void setAllUsers(HTMLCheckbox allUsers) {
        this.allUsers = allUsers;
    }

    public HTMLCheckbox getAllUsers() {
        return allUsers;
    }

    public void setWeChatFollowers(HTMLCheckbox weChatFollowers) {
        this.weChatFollowers = weChatFollowers;
    }

    public HTMLCheckbox getWeChatFollowers() {
        return weChatFollowers;
    }


    public void setContentArea(HTMLDiv contentArea) {
        this.contentArea = contentArea;
    }

    public HTMLDiv getContentArea() {
        return contentArea;
    }

    public void setIdentifierBtn(HTMLDiv identifierBtn) {
        this.identifierBtn = identifierBtn;
    }

    public HTMLDiv getIdentifierBtn() {
        return identifierBtn;
    }

    public void setIdentifierList(HTMLDiv identifierList) {
        this.identifierList = identifierList;
    }

    public HTMLDiv getIdentifierList() {
        return identifierList;
    }

    public void setExceptedIdentifier(HTMLDiv exceptedIdentifier) {
        this.exceptedIdentifier = exceptedIdentifier;
    }

    public HTMLDiv getExceptedIdentifier() {
        return exceptedIdentifier;
    }

    //Get excepted identifer item
    public HTMLDiv getExceptedIdentifier(int index) {
        exceptedIdentifier.setLocator(String.format(exceptedIdentifier.getRawLocator(), index));
        return exceptedIdentifier;
    }

    public List<String> getListedIdentifierLists() {
        List<String> accountList = new ArrayList<>();
        int numbers = getIdentifierList().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            accountList.add(getExceptedIdentifier(i).getText());
        }
        return accountList;
    }

    public int getIdentifierIndex(String identifierName) {
        switch (identifierName) {
            case "None":
            case "无":
                return 0;
            case "Eloqua ID":
                return 1;
            case "WeChat ID":
            case "微信 ID":
                return 2;
            case "WeChat Profile":
            case "微信信息":
                return 3;
            default:
                return -1;

        }
    }


    public void setArticleItem(HTMLDiv articleItem) {
        this.articleItem = articleItem;
    }

    public HTMLDiv getArticleItem() {
        return articleItem;
    }

    public HTMLDiv getArticleItem(int index) {
        articleItem.setLocator(String.format(articleItem.getRawLocator(), index));
        return articleItem;
    }

    public void setSaveBtn(HTMLDiv saveBtn) {
        this.saveBtn = saveBtn;
    }

    public HTMLDiv getSaveBtn() {
        return saveBtn;
    }

    public void setDeleteBtn(HTMLDiv deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    public HTMLDiv getDeleteBtn() {
        return deleteBtn;
    }

    public int getMessageIndex(String value) {
        List<String> accounts = getListsMessages();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public int getOneMessageIndex(String value) {
        List<String> accountList = new ArrayList<>();
        int numbers = getListedMsgs().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            if ((getExceptedMsg(i).getText()).contains(value)) {
                return i;
            }
        }
        return -1;
    }

    public List<String> getListsMessages() {
        List<String> accountList = new ArrayList<>();
        int numbers = getListedMsgs().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            accountList.add(getExceptedMsg(i).getText());
        }
        return accountList;
    }

    public void setListedMsgs(HTMLDiv listedMsgs) {
        this.listedMsgs = listedMsgs;
    }

    public HTMLDiv getListedMsgs() {
        return listedMsgs;
    }

    public void setExceptedMsg(HTMLDiv exceptedMsg) {
        this.exceptedMsg = exceptedMsg;
    }

    public HTMLDiv getExceptedMsg() {
        return exceptedMsg;
    }

    public HTMLDiv getExceptedMsg(int index) {
        exceptedMsg.setLocator(String.format(exceptedMsg.getRawLocator(), index));
        return exceptedMsg;
    }

    public HTMLDiv deleteExceptedMsg(int index) {
        deleteBtn.setLocator(String.format(deleteBtn.getRawLocator(), index));
        return deleteBtn;
    }

    public void setPreviewBtn(HTMLDiv previewBtn) {
        this.previewBtn = previewBtn;
    }

    public HTMLDiv getPreviewBtn() {
        return previewBtn;
    }

    public HTMLDiv getExceptedMsgPreviewBtn(int index) {
        previewBtn.setLocator(String.format(previewBtn.getRawLocator(), index));
        return previewBtn;
    }

    public void setPreviewQR(HTMLDiv previewQR) {
        this.previewQR = previewQR;
    }

    public HTMLDiv getPreviewQR() {
        return previewQR;
    }

    public void setPreviewClose(HTMLDiv previewClose) {
        this.previewClose = previewClose;
    }

    public HTMLDiv getPreviewClose() {
        return previewClose;
    }

    public void setConfirmDeleteBtn(HTMLDiv confirmDeleteBtn) {
        this.confirmDeleteBtn = confirmDeleteBtn;
    }

    public HTMLDiv getConfirmDeleteBtn() {
        return confirmDeleteBtn;
    }

    public void waitForSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
