package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLCheckbox;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class CloudActionConPage extends TestAppPageBase {

    private HTMLDiv accounts;
    private HTMLDiv selectedAccount;
    private HTMLCheckbox richMediaMsg;
    private HTMLCheckbox textMsg;

    private HTMLCheckbox ticketMsg;
    private HTMLInput nextBtn;
    private HTMLInput byPassBtn;

    private HTMLCheckbox broadcastMsg;
    private HTMLCheckbox customerMsg;
    private HTMLCheckbox templateMsg;
    private HTMLCheckbox allFollowers;
    private HTMLCheckbox allFollowersStatus;
    private HTMLCheckbox segment;
    private HTMLCheckbox tag;
    private HTMLDiv tagName = new HTMLDiv("xpath=//label[contains(@class,\"wx-tag-radio-set\")]");

    //define for the subscription account
    private HTMLCheckbox broadcastMsgSub;
    private HTMLCheckbox customerMsgSub;
    private HTMLDiv listItems;
    private HTMLDiv officalAccount;
    private HTMLDiv ticketMedia;
    private HTMLCheckbox broadcastMsgCheckBox;

    public HTMLDiv getTagName() {
        tagName.setPage(page());
        return tagName;
    }

    public HTMLDiv getTagName(String type) {
//        String locator=String.format("xpath=//div[@id='oj-listbox-drop']//div[text()='%s']",type);
//div[@id='wechat-tags']//label[text()='%s']
      //  String locator = String.format("xpath=//div[@id='wechat-tags']//label[text()='%s']", type);
        String locator = String.format("xpath=//div[@id='wechat-tags']//label[contains(text(),'%s')]", type);
        HTMLDiv msgType = new HTMLDiv(locator);
        msgType.setPage(page());
        return msgType;
    }

    public HTMLCheckbox getBroadcastMsgCheckBox() {
        return broadcastMsgCheckBox;
    }

    public void setBroadcastMsgCheckBox(HTMLCheckbox broadcastMsgCheckBox) {
        this.broadcastMsgCheckBox = broadcastMsgCheckBox;
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

    public void setBroadcastMsg(HTMLCheckbox broadcastMsg) {
        this.broadcastMsg = broadcastMsg;
    }

    public HTMLCheckbox getBroadcastMsg() {
        return broadcastMsg;

    }

    public void setCustomerMsg(HTMLCheckbox customerMsg) {
        this.customerMsg = customerMsg;
    }

    public HTMLCheckbox getCustomerMsg() {
        return customerMsg;
    }

    public void setTemplateMsg(HTMLCheckbox templateMsg) {
        this.templateMsg = templateMsg;
    }

    public HTMLCheckbox getTemplateMsg() {
        return templateMsg;
    }

    public void setAllFollowers(HTMLCheckbox allFollowers) {
        this.allFollowers = allFollowers;
    }

    public HTMLCheckbox getAllFollowers() {
        return allFollowers;
    }

    public HTMLCheckbox getSegment() {
        return segment;
    }

    public void setSegment(HTMLCheckbox segment) {
        this.segment = segment;
    }

    public HTMLCheckbox getTag() {
        return tag;
    }

    public void setTag(HTMLCheckbox tag) {
        this.tag = tag;
    }


    public HTMLCheckbox getAllFollowersStatus() {
        return allFollowersStatus;
    }

    public void setAllFollowersStatus(HTMLCheckbox allFollowersStatus) {
        this.allFollowersStatus = allFollowersStatus;
    }

    public HTMLCheckbox getBroadcastMsgSub() {
        return broadcastMsgSub;
    }

    public void setBroadcastMsgSub(HTMLCheckbox broadcastMsgSub) {
        this.broadcastMsgSub = broadcastMsgSub;
    }

    public HTMLCheckbox getCustomerMsgSub() {
        return customerMsgSub;
    }

    public void setCustomerMsgSub(HTMLCheckbox customerMsgSub) {
        this.customerMsgSub = customerMsgSub;
    }


    public void setNextBtn(HTMLInput nextBtn) {
        this.nextBtn = nextBtn;
    }

    public HTMLInput getNextBtn() {
        return nextBtn;
    }

    public HTMLInput getByPassBtn() {
        return byPassBtn;
    }

    public void setByPassBtn(HTMLInput byPassBtn) {
        this.byPassBtn = byPassBtn;
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

    public HTMLCheckbox getTicketMsg() {
        return ticketMsg;
    }

    public void setTicketMsg(HTMLCheckbox ticketMsg) {
        this.ticketMsg = ticketMsg;
    }

    public HTMLDiv getTicketMedia(int index) {
        ticketMedia.setLocator(String.format(ticketMedia.getRawLocator(), index));
        return ticketMedia;
    }

    public HTMLDiv getTicketMedia() {
        return ticketMedia;
    }

    public void setTicketMedia(HTMLDiv ticketMedia) {
        this.ticketMedia = ticketMedia;
    }


    public void setTextMsg(HTMLCheckbox textMsg) {
        this.textMsg = textMsg;
    }

    public HTMLCheckbox getTextMsg() {
        return textMsg;
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
