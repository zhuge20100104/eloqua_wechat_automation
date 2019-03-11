package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by grcao on 6/13/2018.
 */

@Component
public class MenuManagementPage extends TestAppPageBase {

    private HTMLDiv addNewMenu = new HTMLDiv("id=btnCustomMenuNew");

    private HTMLDiv personalizedMenuLists = new HTMLDiv("xpath=//div[@id='filmStrip']//div[contains(@class,'oj-filmstrip-container')][2]");
    //  private HTMLDiv deleteFirstMenuIcon = new HTMLDiv("xpath=//div[@id='filmStrip']//div[contains(@class,'oj-filmstrip-container')][2]//a[@class='icon-func-trash wx-font-icon']");
    private HTMLDiv confirmDeletePersonalizedMenu = new HTMLDiv("xpath=//div[@id='ojDialogWrapper-deleteMenuDialog']//button[@id='btn-save']");
    private HTMLDiv confirmDeletePersMenuMsg = new HTMLDiv("xpath=//div[@id='ojDialogWrapper-deleteMenuDialog']//span[@id='main-panel']");

    //  private HTMLDiv reviewFirstMenuIcon = new HTMLDiv("xpath=//div[@id='filmStrip']//div[contains(@class,'oj-filmstrip-container')][2]//a[@class='icon-eye wx-font-icon']");


    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }


    public HTMLDiv getAddNewMenu() {
        addNewMenu.setPage(page());
        return addNewMenu;
    }

    public HTMLDiv getPersonalizedMenu(String personalizedMenuName) {
        //String locator = String.format("xpath=//span[text()='%s']/ancestor::*//div[text()=\"Personalized Menu\"]//parent::*//div[contains(@class,\"wx-mobile-content\")]", personalizedMenuName);
        String locator = String.format("xpath=//span[text()='%s']", personalizedMenuName);
        HTMLDiv firstPersonalizedMenu = new HTMLDiv(locator);
        firstPersonalizedMenu.setPage(page());
        return firstPersonalizedMenu;
    }

    public HTMLDiv getPersonalizedMenuContent(String personalizedMenuName) {
        String locator = String.format("xpath=//span[text()='%s']/ancestor::*//div[text()=\"Personalized Menu\"]//parent::*//div[contains(@class,\"wx-mobile-content\")]", personalizedMenuName);
        //String locator = String.format("xpath=//span[text()='%s']", personalizedMenuName);
        HTMLDiv firstPersonalizedMenu = new HTMLDiv(locator);
        firstPersonalizedMenu.setPage(page());
        return firstPersonalizedMenu;
    }

    public HTMLDiv getPersonalizedMenuLists() {
        personalizedMenuLists.setPage(page());
        return personalizedMenuLists;
    }

    public HTMLDiv getDeleteFirstMenuIcon(String personalizedMenuName) {
        String locater = String.format("xpath=//span[text()='%s']/ancestor::*//div[text()=\"Personalized Menu\"]//parent::*//a[@class='icon-func-trash wx-font-icon']", personalizedMenuName);
        HTMLDiv deletePersMenuIcon = new HTMLDiv(locater);
        deletePersMenuIcon.setPage(page());
        return deletePersMenuIcon;
    }

    public HTMLDiv getReviewFirstMenuIcon(String personalizedMenuName) {
        String locater = String.format("xpath=//span[text()='%s']/ancestor::*//div[text()=\"Personalized Menu\"]//parent::*//a[@class='icon-eye wx-font-icon']", personalizedMenuName);
        HTMLDiv reviewFirstMenuIcon = new HTMLDiv(locater);
        reviewFirstMenuIcon.setPage(page());
        return reviewFirstMenuIcon;
    }

    public HTMLDiv getConfirmDeletePersonalizedMenu() {
        confirmDeletePersonalizedMenu.setPage(page());
        return confirmDeletePersonalizedMenu;
    }

    public HTMLDiv getConfirmDeletePersMenuMsg() {
        confirmDeletePersMenuMsg.setPage(page());
        return confirmDeletePersMenuMsg;
    }

    public String personalizedMenuContent(String personalizedMenuName) {
        String text = getPersonalizedMenuContent(personalizedMenuName).getText();
        return text;
    }

    public void clickAddMenuIcon() {
        getAddNewMenu().waitForElementReady(20);
        getAddNewMenu().click();
    }
}
