package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLValueDisplay;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 7/20/2017.
 */

@Component
public class CampaignTemplateMessageConPage extends TestAppPageBase {
    private HTMLDiv searchBox;
    private HTMLDiv inputBox;
    private HTMLDiv searchBtn;

    private HTMLDiv exceptedMessage;
    private HTMLDiv listedMessages;

    private HTMLDiv submitBtn;
    private HTMLValueDisplay successfulMsg;
    private HTMLDiv templateSelectedIcon;
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
            if (accounts.get(i).contentEquals(messageValue))
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

    public void setListedMessages(HTMLDiv listedMessages) {
        this.listedMessages = listedMessages;
    }

    public HTMLDiv getListedMessages() {
        return listedMessages;
    }

    public void setSuccessfulMsg(HTMLValueDisplay successfulMsg) {
        this.successfulMsg = successfulMsg;
    }

    public HTMLValueDisplay getSuccessfulMsg() {
        return successfulMsg;
    }

    public HTMLDiv getTemplateSelectedIcon() {
        return templateSelectedIcon;
    }

    public void setTemplateSelectedIcon(HTMLDiv templateSelectedIcon) {
        this.templateSelectedIcon = templateSelectedIcon;
    }

    public void setSearchBox(HTMLDiv searchBox) {
        this.searchBox = searchBox;
    }

    public HTMLDiv getSearchBox() {
        return searchBox;
    }

    public void setInputBox(HTMLDiv inputBox) {
        this.inputBox = inputBox;
    }

    public HTMLDiv getInputBox() {
        return inputBox;
    }

    public void setSearchBtn(HTMLDiv searchBtn) {
        this.searchBtn = searchBtn;
    }

    public HTMLDiv getSearchBtn() {
        return searchBtn;
    }


    public void setSubmitBtn(HTMLDiv submitBtn) {
        this.submitBtn = submitBtn;
    }

    public HTMLDiv getSubmitBtn() {
        return submitBtn;
    }

    public void setCloseBtn(HTMLDiv closeBtn) {
        this.closeBtn = closeBtn;
    }

    public HTMLDiv getCloseBtn() {
        return closeBtn;
    }


    @PostConstruct
    public void init() {
        //registerComp(topOptions);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void waitLoadData() {
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void waitMilliSecond() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public boolean successfulMsgIsVisibleOrNot() {
        int index = 60;
        boolean status = false;
        for (int i = 0; i < index; i++) {
            if (getSuccessfulMsg().isReady()) {
                status = true;
                break;
            }
        }
        return status;
    }

}