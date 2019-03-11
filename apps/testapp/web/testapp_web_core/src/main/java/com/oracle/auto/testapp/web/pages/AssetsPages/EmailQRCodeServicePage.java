package com.oracle.auto.testapp.web.pages.AssetsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grcao on 3/1/2017.
 */

@Component
public class EmailQRCodeServicePage extends TestAppPageBase {

    private HTMLDiv QRCodeName;

    private HTMLDiv listItems;
    private HTMLDiv officalAccount;
    private HTMLDiv accounts;
    private HTMLDiv selectedAccount;


    private HTMLCheckbox richMediaMsg;
    private HTMLCheckbox textMsg;

    private HTMLDiv welcomeMsgEnableBtn;
    private HTMLDiv disabledStatus;
    private HTMLDiv enableStatus;
    private HTMLDiv disableStatus;

    private HTMLInput compactBtn;
    private HTMLInput mediumBtn;
    private HTMLInput notableBtn;
    private HTMLInput nextBtn;

    private HTMLTextArea textArea;
    private HTMLDiv richMediaMsgTitle;
    private HTMLDiv richMediaMsgTitles;

    private HTMLInput hiddenTextBox;

    private HTMLDiv textSubmitBtn;
    private HTMLInput submitBtn;
    private HTMLDiv closeBtn;

    private HTMLValueDisplay successfulMsg;

    public HTMLDiv getQRCodeName() {
        return QRCodeName;
    }

    public void setQRCodeName(HTMLDiv QRCodeName) {
        this.QRCodeName = QRCodeName;
    }


    public List<String> getAccountLists() {
        List<String> accountList = new ArrayList<>();
        int numbers = getAccountNumbers();
        for (int i = 0; i < numbers; i++) {
            accountList.add(getOfficalAccount(i).getText());
        }
        return accountList;
    }

    private int getAccountNumbers() {
        return getListItems().getMethodProp(".size()").AsInt();
    }

    public int getAccountIndex(String value) {
        //return getAccountLists().indexOf(value);
        List<String> accounts = getAccountLists();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public HTMLDiv getOfficalAccount(int index) {
        officalAccount.setLocator(String.format(officalAccount.getRawLocator(), index));
        return officalAccount;
    }


    public void setOfficalAccount(HTMLDiv officalAccount) {
        this.officalAccount = officalAccount;
    }

    public HTMLDiv getOfficalAccount() {
        return officalAccount;
    }

    public void setListItems(HTMLDiv listItems) {
        this.listItems = listItems;
    }

    public HTMLDiv getListItems() {
        return listItems;
    }


    public HTMLInput getCompactBtn() {
        return compactBtn;
    }

    public void setCompactBtn(HTMLInput compactBtn) {
        this.compactBtn = compactBtn;
    }

    public HTMLInput getMediumBtn() {
        return mediumBtn;
    }

    public void setMediumBtn(HTMLInput mediumBtn) {
        this.mediumBtn = mediumBtn;
    }

    public HTMLInput getNotableBtn() {
        return notableBtn;
    }

    public void setNotableBtn(HTMLInput notableBtn) {
        this.notableBtn = notableBtn;
    }

    public void setNextBtn(HTMLInput nextBtn) {
        this.nextBtn = nextBtn;
    }

    public HTMLInput getNextBtn() {
        return nextBtn;
    }

    public void setAccounts(HTMLDiv accounts) {
        this.accounts = accounts;
    }

    public HTMLDiv getAccounts() {
        return accounts;
    }

    public void setSelectedAccount(HTMLDiv selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public HTMLDiv getSelectedAccount() {
        return selectedAccount;
    }

    public void setRichMediaMsg(HTMLCheckbox richMediaMsg) {
        this.richMediaMsg = richMediaMsg;
    }

    public HTMLCheckbox getRichMediaMsg() {
        return richMediaMsg;
    }

    public void setWelcomeMsgEnableBtn(HTMLDiv welcomeMsgEnableBtn) {
        this.welcomeMsgEnableBtn = welcomeMsgEnableBtn;
    }

    public HTMLDiv getWelcomeMsgEnableBtn() {
        return welcomeMsgEnableBtn;
    }

    public void setDisabledStatus(HTMLDiv disabledStatus) {
        this.disabledStatus = disabledStatus;
    }

    public HTMLDiv getDisabledStatus() {
        return disabledStatus;
    }

    public void setEnableStatus(HTMLDiv enableStatus) {
        this.enableStatus = enableStatus;
    }

    public HTMLDiv getEnableStatus() {
        return enableStatus;
    }

    public void setDisableStatus(HTMLDiv disableStatus) {
        this.disableStatus = disableStatus;
    }

    public HTMLDiv getDisableStatus() {
        return disableStatus;
    }


    public void setTextMsg(HTMLCheckbox textMsg) {
        this.textMsg = textMsg;
    }

    public HTMLCheckbox getTextMsg() {
        return textMsg;
    }

    public void checkTextType() {
        getTextMsg().clickToCheck();
    }

    public void checkRichMediaType() {
        getRichMediaMsg().clickToCheck();
    }

    public void setTextArea(HTMLTextArea textArea) {
        this.textArea = textArea;
    }

    public HTMLTextArea getTextArea() {
        return textArea;
    }

    public HTMLInput getHiddenTextBox() {
        return hiddenTextBox;
    }

    public void setHiddenTextBox(HTMLInput hiddenTextBox) {
        this.hiddenTextBox = hiddenTextBox;
    }

    public HTMLDiv getRichMediaMsgTitle() {
        return richMediaMsgTitle;
    }

    public void setRichMediaMsgTitle(HTMLDiv richMediaMsgTitle) {
        this.richMediaMsgTitle = richMediaMsgTitle;
    }

    public HTMLDiv getRichMediaMsgTitles() {
        return richMediaMsgTitles;
    }

    public void setRichMediaMsgTitles(HTMLDiv richMediaMsgTitles) {
        this.richMediaMsgTitles = richMediaMsgTitles;
    }

    public int getRichMediaMessageIndex(String title) {
        int numbers = getRichMediaMsgTitles().getMethodProp(".size()").AsInt();
        int index = -1;
        for (int j = 0; j < numbers; j++) {
            if (getExceptedRichMediaMessage(j).getText().contains(title)) {
                index = j;
                break;
            }
        }
        return index;
    }

    public HTMLDiv getExceptedRichMediaMessage(int index) {
        richMediaMsgTitle.setLocator(String.format(richMediaMsgTitle.getRawLocator(), index));
        return richMediaMsgTitle;
    }

    public HTMLDiv getTextSubmitBtn() {
        return textSubmitBtn;
    }

    public void setTextSubmitBtn(HTMLDiv textSubmitBtn) {
        this.textSubmitBtn = textSubmitBtn;
    }

    public void setCloseBtn(HTMLDiv closeBtn) {
        this.closeBtn = closeBtn;
    }

    public HTMLDiv getCloseBtn() {
        return closeBtn;
    }

    public void setSuccessfulMsg(HTMLValueDisplay successfulMsg) {
        this.successfulMsg = successfulMsg;
    }

    public HTMLValueDisplay getSuccessfulMsg() {
        return successfulMsg;
    }

    public boolean successfulMsgIsVisibleOrNot() {
        int index = 30 * 60;
        boolean status = false;
        for (int i = 0; i < index; i++) {
            if (getSuccessfulMsg().isReady()) {

                status = true;
                break;
            } else {
                page().waitMilliSecond();
            }
        }

        return status;
    }


    @PostConstruct
    public void init() {
        //   registerComp(broadcastMsg);
        //registerComp(textMsg);
        //registerComp(nextBtn);
        injectPageToChildComponents(this);
        waitForPageReady();
    }


}
