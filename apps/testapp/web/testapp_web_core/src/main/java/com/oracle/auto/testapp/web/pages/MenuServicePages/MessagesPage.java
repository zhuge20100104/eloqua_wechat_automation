package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLTextArea;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 7/23/2017.
 */

@Component
public class MessagesPage extends TestAppPageBase {
    private HTMLDiv accountListBtn;
    private HTMLDiv accountList;
    private HTMLDiv exceptedAccount;
    private HTMLDiv newMsgBtn;

    private HTMLDiv msgTitle;
    private HTMLDiv editMsgBtn;
    private HTMLDiv deleteMsgBtn;
    private HTMLDiv previewMsgBtn;

    private HTMLDiv currentAccountName = new HTMLDiv("xpath=//span[contains(@class,\"wx-appheader-avatar\")]/following-sibling::span[1]");

    public HTMLTextArea getContentURL(){
        HTMLTextArea contentURL = new HTMLTextArea("xpath=//textarea[@id='ContentUrl']");
        contentURL.setPage(page());
        return contentURL;

    }

    public HTMLDiv getIdentifier(){
        HTMLDiv element = new HTMLDiv("xpath= //span[@id='ojChoiceId_basicSelect_selected']");
        element.setPage(page());
        return element;

    }

    public HTMLDiv getEditPageSave(){
        HTMLDiv element = new HTMLDiv("xpath=//button[@id='wx-btn-msg-save']/div[@class='oj-button-label']");
        element.setPage(page());
        return element;

    }
    public HTMLDiv getExceptedMessageLinkIcon(String messageName){
        String locator=String.format("xpath=//span[text()='%s']/parent::*/parent::*//a[contains(@title,\"Link\")]",messageName);
        HTMLDiv msgItem=new HTMLDiv(locator);
        msgItem.setPage(page());
        return msgItem;
    }

    public HTMLDiv getExceptedMessageEditIcon(String messageName){
        String locator=String.format("xpath=//span[text()='%s']/parent::*/parent::*//a[contains(@title,\"Edit\")]",messageName);
        HTMLDiv msgItem=new HTMLDiv(locator);
        msgItem.setPage(page());
        return msgItem;
    }
    public HTMLDiv getExceptedMessageItem(String messageName){
        String locator=String.format("xpath=//span[text()='%s']",messageName);
        HTMLDiv msgItem=new HTMLDiv(locator);
        msgItem.setPage(page());
        return msgItem;
    }

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public HTMLDiv getCurrentAccountName() {
        currentAccountName.setPage(page());
        return currentAccountName;
    }

    public void setAccountListBtn(HTMLDiv accountListBtn) {
        this.accountListBtn = accountListBtn;
    }

    public HTMLDiv getAccountListBtn() {
        return accountListBtn;
    }

    public void setAccountList(HTMLDiv accountList) {
        this.accountList = accountList;
    }

    public HTMLDiv getAccountList() {
        return accountList;
    }

    public void setExceptedAccount(HTMLDiv exceptedAccount) {
        this.exceptedAccount = exceptedAccount;
    }

    public HTMLDiv getExceptedAccount() {
        return exceptedAccount;
    }

    public void setNewMsgBtn(HTMLDiv newMsgBtn) {
        this.newMsgBtn = newMsgBtn;
    }

    public HTMLDiv getNewMsgBtn() {
        return newMsgBtn;
    }

    public void setMsgTitle(HTMLDiv msgTitle) {
        this.msgTitle = msgTitle;
    }

    public HTMLDiv getMsgTitle() {
        return msgTitle;
    }


    public void setEditMsgBtn(HTMLDiv editMsgBtn) {
        this.editMsgBtn = editMsgBtn;
    }

    public HTMLDiv getEditMsgBtn() {
        return editMsgBtn;
    }

//    public HTMLDiv getExceptedMsg(int index) {
//        deleteBtn.setLocator(String.format(deleteBtn.getRawLocator(), index));
//        return deleteBtn;
//    }

    public void setDeleteMsgBtn(HTMLDiv deleteMsgBtn) {
        this.deleteMsgBtn = deleteMsgBtn;
    }

    public HTMLDiv getDeleteMsgBtn() {
        return deleteMsgBtn;
    }

    public void setPreviewMsgBtn(HTMLDiv previewMsgBtn) {
        this.previewMsgBtn = previewMsgBtn;
    }

    public HTMLDiv getPreviewMsgBtn() {
        return previewMsgBtn;
    }

    public List<String> getAccountLists() {
        List<String> accountList = new ArrayList<>();
        int numbers = getAccountList().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            accountList.add(getExceptedAccount(i).getText());
        }
        return accountList;
    }

    public int getAccountIndex(String value) {
        waitForSecond(2);
        List<String> accounts = getAccountLists();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).contains(value)) {
                System.out.println("The current account is: " + accounts.get(i));
                return i;
            }
        }
        return -1;
    }

    public HTMLDiv getExceptedAccount(int index) {
        exceptedAccount.setLocator(String.format(exceptedAccount.getRawLocator(), index));
        return exceptedAccount;
    }

    public void waitForSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
