package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.testapp.web.components.campaignscomps.LeftNavigator;
import com.oracle.auto.testapp.web.components.campaignscomps.RecentAccessed;
import com.oracle.auto.testapp.web.components.campaignscomps.TopOptions;
import com.oracle.auto.testapp.web.components.campaignscomps.UsefulLinks;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import com.oracle.auto.web.comp.html.HTMLTextArea;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class MessageConPage extends TestAppPageBase {
    private HTMLTextArea textArea;
    private HTMLDiv customerSubmitBtn;
    private HTMLDiv broadcastSubmitBtn;

    private HTMLDiv selectInsertMessageBox;
    private HTMLDiv searchMessageBox;
    private HTMLDiv exceptedMessage;
    private HTMLDiv listedMessages;
    private HTMLDiv insertButton;

    private HTMLDiv closeBtn;


    private int getListedMessagesNumbers() {
        return getListedMessages().getMethodProp(".size()").AsInt();
    }

    public HTMLDiv getExceptedMessage(int index) {
        exceptedMessage.setLocator(String.format(exceptedMessage.getRawLocator(), index));
        return exceptedMessage;
    }

    public List<String> getListedMessageLists() {
        List<String> accountList = new ArrayList<>();
        int numbers = getListedMessagesNumbers();
        for (int i = 0; i < numbers; i++) {
            accountList.add(getExceptedMessage(i).getText());
        }
        return accountList;
    }

    public int getExceptedMessageIndex(String messageValue) {
        List<String> accounts = getListedMessageLists();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).contains(messageValue))
                return i;
        }
        return -1;
    }

    public void setExceptedMessage(HTMLDiv exceptedMessage) {
        this.exceptedMessage = exceptedMessage;
    }

    public HTMLDiv getExceptedMessage() {
        return exceptedMessage;
    }

    public void setSearchMessageBox(HTMLDiv searchMessageBox) {
        this.searchMessageBox = searchMessageBox;
    }

    public HTMLDiv getSearchMessageBox() {
        return searchMessageBox;
    }

    public void setListedMessages(HTMLDiv listedMessages) {
        this.listedMessages = listedMessages;
    }

    public HTMLDiv getListedMessages() {
        return listedMessages;
    }

    public void setSelectInsertMessageBox(HTMLDiv selectInsertMessageBox) {
        this.selectInsertMessageBox = selectInsertMessageBox;
    }

    public HTMLDiv getSelectInsertMessageBox() {
        return selectInsertMessageBox;
    }

    public void setBroadcastSubmitBtn(HTMLDiv broadcastSubmitBtn) {
        this.broadcastSubmitBtn = broadcastSubmitBtn;
    }

    public void setInsertButton(HTMLDiv insertButton) {
        this.insertButton = insertButton;
    }

    public HTMLDiv getInsertButton() {
        return insertButton;
    }

    public HTMLDiv getBroadcastSubmitBtn() {
        return broadcastSubmitBtn;
    }

    public void setCloseBtn(HTMLDiv closeBtn) {
        this.closeBtn = closeBtn;
    }

    public HTMLDiv getCloseBtn() {
        return closeBtn;
    }

    public void setCustomerSubmitBtn(HTMLDiv customerSubmitBtn) {
        this.customerSubmitBtn = customerSubmitBtn;
    }

    public HTMLDiv getCustomerSubmitBtn() {
        return customerSubmitBtn;
    }

    public void setTextArea(HTMLTextArea textArea) {
        this.textArea = textArea;
    }

    public HTMLTextArea getTextArea() {
        return textArea;
    }

    @PostConstruct
    public void init() {
        //registerComp(topOptions);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public boolean submitResult() {
        WebDriverSeleniumPageEx.ScriptExecuteResult ret = page().executeScriptEx("var comp=document.getElementsByClassName(\"alert-success\")[0];return comp.textContent");
        return ret.str().contains("success");
    }

    public void waitLoadData() {
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}