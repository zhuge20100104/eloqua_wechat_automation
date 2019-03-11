package com.oracle.auto.testapp.tests.web.service.steps;

import com.oracle.auto.testapp.web.model.Filters;
import com.oracle.auto.testapp.web.pages.MenuServicePages.MenuManagementConPage;
import com.oracle.auto.testapp.web.pages.MenuServicePages.MenuManagementPage;
import com.oracle.auto.testapp.web.pages.MenuServicePages.MenuServicePage;
import com.oracle.auto.testapp.web.pages.MenuServicePages.ToolkitPage;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.utility.WebAssert;
import org.jbehave.core.annotations.Then;
import org.junit.Assert;


/**
 * Created by grcao on 6/13/2018.
 */

public class MenuManagementSteps {
    private TestAppPageFactory factory;

    public MenuManagementSteps() {
        factory = TestAppPageFactory.getInstance();
    }

    @Then("click menu management link")
    public void clickToolkitLinkServiceAccount() {
        MenuServicePage menuServicePage = factory.pageAs(MenuServicePage.class);
        menuServicePage.waitForSecond(5);
        menuServicePage.getMenuManagement().waitForElementReady(20);
        menuServicePage.getMenuManagement().click();
        menuServicePage.waitForSecond(5);
    }

    public void selectFilters(Filters filters) {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);

        if (filters.tag != null && filters.tag.length() != 0) {
            menuManagementConPage.selectTag(filters.tag);
        }

        if (filters.gender != null && filters.gender.length() != 0) {
            menuManagementConPage.selectGender(filters.gender);
        }

    }

    @Then("input text message content: $text, $field")
    public void insertTextMessage(String text, String field) {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.clickTextMessage();
        menuManagementConPage.inputTextField(text, field);
    }

    public void clickAddMenuIcon() {
        MenuManagementPage menuManagementPage = factory.pageAs(MenuManagementPage.class);
        menuManagementPage.clickAddMenuIcon();
    }

    public void inputMenuName(String menuName) {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.getAddMenuIcon().click();
        menuManagementConPage.getInputMenuName().setValue(menuName);
    }

    @Then("click icon to add first sub menu")
    public void clickAddFristSubMenuBtn() {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.page().waitForSecond(2);
        menuManagementConPage.getAddMenuOneSubMenuIcon().click();
        if (!menuManagementConPage.getSubMenuText().isPresent()) {
            menuManagementConPage.getAddMenuOneSubMenuIcon().click();
        }
    }

    @Then("click icon to add not first level sub menu")
    public void clickAddNotFristSubMenuBtn() {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.page().waitForSecond(2);
        menuManagementConPage.getAddMenuOneSubMenuIcon().click();
    }

//    public void inputFirstSubMenuName(String subMenuName) {
//        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
//        menuManagementConPage.page().waitForSecond(2);
//        menuManagementConPage.getAddMenuOneSubMenuIcon().click();
//        menuManagementConPage.getAddMenuOneSubMenuIcon().click();
//        inputSubMenuName(subMenuName);
//    }
//
//    public void inputOtherSubMenuName(String subMenuName) {
//        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
//        menuManagementConPage.page().waitForSecond(2);
//        menuManagementConPage.getAddMenuOneSubMenuIcon().click();
//        inputSubMenuName(subMenuName);
//    }

    public void inputSubMenuName(String subMenuName) {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.getInputMenuName().setValue(subMenuName);
    }

    @Then("click personalized menu Publish button")
    public void publishMenu() {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.getSaveAndPublishBtn().click();
        menuManagementConPage.page().waitForSecond(5);
    }

    @Then("verify the clear first level menu message appears")
    public void verifyClearFirstLevelMenu() {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        String currentMsg = menuManagementConPage.getClearFristLevelMenuMsg().getText();
        WebAssert.assertTrue("Current message is: " + currentMsg, currentMsg.equals("After adding the sub-menu item, it will clear the content of the first level menu. Confirm to continue to add sub-menu item?"));
        menuManagementConPage.getConfirmClearFristLevelMenuMsg().click();
    }

    @Then("verify filters in Menu Management page: $filter, $menuName")
    public void verifyFilters(Filters filters, String menuName) {
        MenuManagementPage menuManagementPage = factory.pageAs(MenuManagementPage.class);
        menuManagementPage.getPersonalizedMenu(menuName).waitForElementReady(20);
        String getPersonalizedMenuContent = menuManagementPage.personalizedMenuContent(menuName);
        System.out.println("*******************text is:" + getPersonalizedMenuContent + " ****************");
        if (filters.tag != null && filters.tag.length() != 0) {
            WebAssert.assertTrue(getPersonalizedMenuContent.contains("WeChat Tags: " + filters.tag));
        }

        if (filters.gender != null && filters.gender.length() != 0) {
            WebAssert.assertTrue(getPersonalizedMenuContent.contains("Gender: " + filters.gender));
        }

    }

    @Then("publish Text Message Personalized Menu: $filters, $menuName, $text, $field")
    public void publishOneTextPersonalizedMenu(Filters filters, String menuName, String text, String field) {
        addOneTextPersonalizedMenu(filters, menuName, text, field);
        publishMenu();
    }

    @Then("publish Web Page Personalized Menu: $filters, $menuName, $URL, $identifier")
    public void publishOneWebPagePersonalizedMenu(Filters filters, String menuName, String URL, String identifier) {
        addOneWebPagePersonalizedMenu(filters, menuName, URL, identifier);
        publishMenu();
    }

    @Then("add Level One Personalized Menu: $filters, $menuName")
    public void addLevelOnePersonalizedMenu(Filters filters, String menuName) {
        clickAddMenuIcon();
        selectFilters(filters);
        inputMenuName(menuName);
    }

    @Then("add Personalized Menu Text Message: $filters, $menuName, $text, $field")
    public void addOneTextPersonalizedMenu(Filters filters, String menuName, String text, String field) {
        addLevelOnePersonalizedMenu(filters, menuName);
        insertTextMessage(text, field);
    }

    @Then("add Personalized Menu Web Page Content: $filters, $menuName, $URL, $identifier")
    public void addOneWebPagePersonalizedMenu(Filters filters, String menuName, String URL, String identifier) {
        addLevelOnePersonalizedMenu(filters, menuName);
     //   clickWebPageRadio();
        inputWebPageURLandIdentifier(URL, identifier);
    }

    @Then("publish Sub Personalized Menu with: $number Text Message: $filters, $menuName, $text, $field, $subMenuName")
    public void publishSubMenuTextPersMenu(int number, Filters filters, String menuName, String text, String field, String subMenuName) {
        //number is the sub menu count, value is from 1 to 5.
        clickAddMenuIcon();
        selectFilters(filters);
        inputMenuName(menuName);
        addSubMenuTextPersMenu(number, text, field, subMenuName);
        publishMenu();
    }

    @Then("input $subMenuName with Text Message: $text, $field")
    public void inputTextSubMenuContent(String subMenuName,String text, String field){
        inputSubMenuName(subMenuName);
        insertTextMessage(text, field);
    }

    @Then("add Sub Personalized Menu with: $number Text Message: $text, $field, $subMenuName")
    public void addSubMenuTextPersMenu(int number, String text, String field, String subMenuName) {
        //number is the sub menu count, value is from 1 to 5.
        int preName = 1;
        while (number > 0) {
            //First should click twice in automation, scripts issue
            if (preName == 1) {
                clickAddFristSubMenuBtn();
            } else {
                clickAddNotFristSubMenuBtn();
            }
            inputTextSubMenuContent(preName + subMenuName, text, field);
//            inputSubMenuName(preName + subMenuName);
//            insertTextMessage(text, field);
            number--;
            preName++;
        }

    }



    @Then("delete personalized menu: $menuName and verify if delete successfully")
    public void deleteFirstPersonalizedMenu(String menuName) {
        MenuManagementPage menuManagementPage = factory.pageAs(MenuManagementPage.class);
        menuManagementPage.getPersonalizedMenu(menuName).waitForElementReady(20);
        menuManagementPage.getPersonalizedMenu(menuName).click();
        menuManagementPage.getDeleteFirstMenuIcon(menuName).click();
        WebAssert.assertTrue(menuManagementPage.getConfirmDeletePersMenuMsg().getText().equals("Confirm to delete this personalized menu?"));
        menuManagementPage.getConfirmDeletePersonalizedMenu().click();
        menuManagementPage.page().waitForSecond(10);
        WebAssert.assertFalse("Failed to delete: " + menuName, menuManagementPage.getPersonalizedMenu(menuName).isPresent());

    }

    @Then("review Sub Personalized Menu with: $number Text Message: $filters, $menuName, $subMenuName")
    public void reviewSubMenuTextMsg(int number, Filters filters, String menuName, String subMenuName) {
        MenuManagementPage menuManagementPage = factory.pageAs(MenuManagementPage.class);
        menuManagementPage.getPersonalizedMenu(menuName).click();
        menuManagementPage.getReviewFirstMenuIcon(menuName).click();
        reviewFilters(filters);
        reviewMenuOneName(menuName);
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        int preName = 1;
        while (number > 0) {
            menuManagementConPage.getMenuOneSubMName(preName + subMenuName).click();
            reviewTextMsgAttribute();
            number--;
            preName++;
        }
        clickBackReviewBtn();
    }

    @Then("review one menu with text message content in Personalized Menu page: $filters, $menuName")
    public void reviewOneMenuTextMsg(Filters filters, String menuName) {
        MenuManagementPage menuManagementPage = factory.pageAs(MenuManagementPage.class);
        menuManagementPage.getPersonalizedMenu(menuName).click();
        menuManagementPage.getReviewFirstMenuIcon(menuName).click();
        reviewFilters(filters);
        reviewMenuOneName(menuName);
        reviewTextMsgAttribute();
        clickBackReviewBtn();
    }

    public void reviewMenuOneName(String menuName) {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        WebAssert.assertTrue(menuManagementConPage.getSavedMenuOneName().getText().equals(menuName));

    }

    public void reviewTextMsgAttribute() {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        WebAssert.assertTrue(menuManagementConPage.getTextMsgAreaAttribute().contains("disabled: readOnly"));

    }

    public void reviewFilters(Filters filters) {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.page().waitForSecond(10);
        menuManagementConPage.getFilterTag().waitForElementReady(20);
        if (filters.tag != null && filters.tag.length() != 0) {
            String currentTag = menuManagementConPage.getFilterTag().getText();
            WebAssert.assertTrue("Current Tag is: " + currentTag, currentTag.equals(filters.tag));
        }

        if (filters.gender != null && filters.gender.length() != 0) {
            String currentGender = menuManagementConPage.getFilterGender().getText();
            WebAssert.assertTrue("Current gender is: " + currentGender, currentGender.equals(filters.gender));
        }

    }

    public void clickBackReviewBtn() {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.clickBackBtn();
        menuManagementConPage.page().waitForSecond(5);
    }

    @Then("delete personalized first level menu: $menuMane")
    public void deleteFirstLevelOneMenu(String menuName) {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.getFirstLevelOneMenu(menuName).click();
        confirmDeleteMenuandVerifyMsg(menuName);
    }

    @Then("delete personalized sub menu in first level menu list: $subMenuMane")
    public void deleteFirstLevelOneSubMenu(String subMenuName) {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.getFirstLevelSubMenu(subMenuName).click();
        confirmDeleteMenuandVerifyMsg(subMenuName);
    }


    public void confirmDeleteMenuandVerifyMsg(String name) {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.getDeleteMenuIcon().click();
        WebAssert.assertTrue(menuManagementConPage.getConfirmDeleteMenuMessage().getText().equals("The content configured for the menu item will be deleted if deleting the menu item \"" + name + "\"."));
        menuManagementConPage.getConfirmDeleteMenuIcon().click();
        menuManagementConPage.page().waitForSecond(2);
    }

    @Then("verify at least one menu message")
    public void atLeastOneMenuErrorMsg() {
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        WebAssert.assertTrue(menuManagementConPage.getAddAtLeastOneMsg().getText().equals("Please add at least one menu item."));
    }

    public void clickWebPageRadio(){
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.clickWebPageRadio();
    }

    public void inputWebPageURLandIdentifier(String URL, String identifier){
        MenuManagementConPage menuManagementConPage = factory.pageAs(MenuManagementConPage.class);
        menuManagementConPage.getWebPageURL().setValue(URL);
        menuManagementConPage.getIdentifierArrow().click();
        menuManagementConPage.getIdentifier(identifier).click();
    }
}
