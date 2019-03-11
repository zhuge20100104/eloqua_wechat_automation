package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLCheckbox;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by grace on 8/31/2017.
 */

@Component
public class EmailEditPage extends TestAppPageBase {

    private HTMLDiv sendingOptionBtn;
    private HTMLCheckbox allowEmailBox;

    private HTMLInput selectEmailInput;
    private HTMLDiv listedEmailItems;
    private HTMLDiv exceptedEmail;


    @PostConstruct
    public void init() {
        registerComp(selectEmailInput);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    private int getListedEmailNumbers() {
        return getListedEmailItems().getMethodProp(".size()").AsInt();
    }

    public HTMLDiv getExceptedEmail(int index) {
        exceptedEmail.setLocator(String.format(exceptedEmail.getRawLocator(), index));
        return exceptedEmail;
    }


    public List<String> getListedEmailLists() {
        List<String> accountList = new ArrayList<>();
        int numbers = getListedEmailNumbers();
        for (int i = 0; i < numbers; i++) {
            accountList.add(getExceptedEmail(i).getText());
        }
        return accountList;
    }

    public int getEmailIndex(String EmailName) {
        List<String> accounts = getListedEmailLists();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).contentEquals(EmailName))
                //contains(EmailName))
                return i;
        }
        return -1;
    }

    public void waitForSomeTime(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setExceptedEmail(HTMLDiv exceptedEmail) {
        this.exceptedEmail = exceptedEmail;
    }

    public HTMLDiv getExceptedEmail() {
        return exceptedEmail;
    }

    public void setListedEmailItems(HTMLDiv listedEmailItems) {
        this.listedEmailItems = listedEmailItems;
    }

    public HTMLDiv getListedEmailItems() {
        return listedEmailItems;
    }

    public void setSelectEmailInput(HTMLInput selectEmailInput) {
        this.selectEmailInput = selectEmailInput;

    }

    public HTMLInput getSelectEmailInput() {
        return selectEmailInput;
    }


    public void setSendingOptionBtn(HTMLDiv sendingOptionBtn) {
        this.sendingOptionBtn = sendingOptionBtn;
    }

    public HTMLDiv getSendingOptionBtn() {
        return sendingOptionBtn;
    }

    public void setAllowEmailBox(HTMLCheckbox allowEmailBox) {
        this.allowEmailBox = allowEmailBox;
    }

    public HTMLCheckbox getAllowEmailBox() {
        return allowEmailBox;
    }


}
