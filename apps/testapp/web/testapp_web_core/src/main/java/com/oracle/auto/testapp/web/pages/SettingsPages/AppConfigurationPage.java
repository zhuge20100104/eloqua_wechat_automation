package com.oracle.auto.testapp.web.pages.SettingsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 7/12/2017.
 */

@Component
public class AppConfigurationPage extends TestAppPageBase {
    private HTMLDiv addAccountButton;
    private HTMLDiv deleteButton;
    private HTMLDiv editButton;
    private HTMLDiv accounts;


    private HTMLDiv closeBtn;
    private HTMLDiv saveBtn;
    private HTMLDiv successfulMsg;

    public HTMLDiv getDeleteButton(String accountName) {
        int index = getAccountIndex(accountName);
        deleteButton.setLocator(String.format(deleteButton.getRawLocator(), index));
        return deleteButton;
    }

    public HTMLDiv getEditButton(String accountName) {
        int index = getAccountIndex(accountName);
        editButton.setLocator(String.format(editButton.getRawLocator(), index));
        return editButton;
    }

    private HTMLDiv getAccounts(int index) {
        accounts.setLocator(String.format(accounts.getRawLocator() + "[%d]", index));
        return accounts;
    }

    public int getAccountIndex(String value) {
        List<String> accountList = getAccountsList();
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).contains(value))
                return i;
        }
        return -1;
    }

    private List<String> getAccountsList() {
        List<String> accountsList = new ArrayList<>();
        int accountNumbers = getAccountSize();
        for (int i = 0; i < accountNumbers; i++) {
            HTMLDiv selectedAccount = getAccounts(i);
            accountsList.add(selectedAccount.getText());
        }
        return accountsList;
    }

    private int getAccountSize() {
        return accounts.getMethodProp(".size()").AsInt();
    }

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void waitForSomeTime(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setAddAccountButton(HTMLDiv addAccountButton) {
        this.addAccountButton = addAccountButton;
    }

    public HTMLDiv getAddAccountButton() {
        return addAccountButton;
    }

    public void setAccounts(HTMLDiv accounts) {
        this.accounts = accounts;
    }

    public HTMLDiv getAccounts() {
        return accounts;
    }

    public void setDeleteButton(HTMLDiv deleteButton) {
        this.deleteButton = deleteButton;
    }

    public HTMLDiv getDeleteButton() {
        return deleteButton;
    }

    public void setEditButton(HTMLDiv editButton) {
        this.editButton = editButton;
    }

    public HTMLDiv getEditButton() {
        return editButton;
    }
    public void setSaveBtn(HTMLDiv saveBtn) {
        this.saveBtn = saveBtn;
    }

    public HTMLDiv getSaveBtn() {
        return saveBtn;
    }

    public void setCloseBtn(HTMLDiv closeBtn) {
        this.closeBtn = closeBtn;
    }

    public HTMLDiv getCloseBtn() {
        return closeBtn;
    }

    public void setSuccessfulMsg(HTMLDiv successfulMsg) {
        this.successfulMsg = successfulMsg;
    }

    public HTMLDiv getSuccessfulMsg() {
        return successfulMsg;
    }


}
