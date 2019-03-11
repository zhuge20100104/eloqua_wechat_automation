package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 7/27/2017.
 */

@Component
public class MenuServicePage extends TestAppPageBase {
//    private HTMLDiv accountListBtn;
//    private HTMLDiv accountList;
//    private HTMLDiv exceptedAccount;

    private HTMLDiv autoReplyLinkServiceAccount;
    private HTMLDiv welcomeMsgLinkSubscriptionAccount;
    private HTMLDiv welcomeMsgLinkServiceAccount;

    private HTMLDiv imagesLink;

    private HTMLDiv templateLinkServiceAccount;
    private HTMLDiv messageGallaryLink;
    private HTMLDiv messageLink;

    private HTMLDiv toolkit = new HTMLDiv("id=library-toolkit");
    private HTMLDiv menuManagement = new HTMLDiv("xpath=//li[@id='library-custom-menu']//span[text()=\"Menu Management\"]");

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public HTMLDiv getToolkit() {
        toolkit.setPage(page());
        return toolkit;
    }

    public HTMLDiv getMenuManagement() {
        menuManagement.setPage(page());
        return menuManagement;
    }

    public HTMLDiv getMessageLink() {
        return messageLink;
    }

    public void setMessageLink(HTMLDiv messageLink) {
        this.messageLink = messageLink;
    }

    public HTMLDiv getImagesLink() {
        return imagesLink;
    }

    public void setImagesLink(HTMLDiv imagesLink) {
        this.imagesLink = imagesLink;
    }

    public HTMLDiv getMessageGallaryLink() {
        return messageGallaryLink;
    }

    public void setMessageGallaryLink(HTMLDiv messageGallaryLink) {
        this.messageGallaryLink = messageGallaryLink;
    }

    public void setAutoReplyLinkServiceAccount(HTMLDiv autoReplyLinkServiceAccount) {
        this.autoReplyLinkServiceAccount = autoReplyLinkServiceAccount;
    }

    public HTMLDiv getAutoReplyLinkServiceAccount() {
        return autoReplyLinkServiceAccount;
    }

    public void setTemplateLinkServiceAccount(HTMLDiv templateLinkServiceAccount) {
        this.templateLinkServiceAccount = templateLinkServiceAccount;
    }

    public HTMLDiv getTemplateLinkServiceAccount() {
        return templateLinkServiceAccount;
    }

    public void setWelcomeMsgLinkServiceAccount(HTMLDiv welcomeMsgLinkServiceAccount) {
        this.welcomeMsgLinkServiceAccount = welcomeMsgLinkServiceAccount;
    }

    public HTMLDiv getWelcomeMsgLinkServiceAccount() {
        return welcomeMsgLinkServiceAccount;
    }

    public void setWelcomeMsgLinkSubscriptionAccount(HTMLDiv welcomeMsgLinkSubscriptionAccount) {
        this.welcomeMsgLinkSubscriptionAccount = welcomeMsgLinkSubscriptionAccount;
    }

    public HTMLDiv getWelcomeMsgLinkSubscriptionAccount() {
        return welcomeMsgLinkSubscriptionAccount;
    }
//    public List<String> getAccountLists() {
//        List<String> accountList = new ArrayList<>();
//        int numbers = getAccountList().getMethodProp(".size()").AsInt();
//        for (int i = 0; i < numbers; i++) {
//            accountList.add(getExceptedAccount(i).getText());
//        }
//        return accountList;
//    }
//
//    public int getAccountIndex(String value) {
//        List<String> accounts = getAccountLists();
//        for (int i = 0; i < accounts.size(); i++) {
//            if (accounts.get(i).contains(value))
//                return i;
//        }
//        return -1;
//    }
//
//    public HTMLDiv getExceptedAccount(int index) {
//        exceptedAccount.setLocator(String.format(exceptedAccount.getRawLocator(), index));
//        return exceptedAccount;
//    }

    public void clickMessageGallary() {
        if (getMessageGallaryLink().isReady()) {
            getMessageGallaryLink().click();
        }
    }

    public void goToMessagePage() {
        clickMessageGallary();
        getMessageLink().click();
    }

    public void goToTemplateMessagePage() {
        clickMessageGallary();
        getTemplateLinkServiceAccount().click();
    }

    public void goToImagePage() {
        clickMessageGallary();
        getImagesLink().click();
    }

    public void goToAutoReplyPage() {
        getAutoReplyLinkServiceAccount().click();
    }

    public void waitForSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
