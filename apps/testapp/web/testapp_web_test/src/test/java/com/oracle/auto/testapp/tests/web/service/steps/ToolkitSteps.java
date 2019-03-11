package com.oracle.auto.testapp.tests.web.service.steps;

import com.oracle.auto.testapp.web.pages.MenuServicePages.*;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebAssert;
import org.jbehave.core.annotations.Then;
import org.junit.Assert;

import javax.xml.crypto.Data;
import java.util.Random;


/**
 * Created by grcao on 5/24/2018.
 */

public class ToolkitSteps {
    private TestAppPageFactory factory;

    public ToolkitSteps() {
        factory = TestAppPageFactory.getInstance();
    }

    @Then("click service account toolkit link")
    public void clickToolkitLinkServiceAccount() {
        MenuServicePage menuServicePage = factory.pageAs(MenuServicePage.class);
        menuServicePage.getToolkit().waitForElementReady(20);
        menuServicePage.getToolkit().click();
        menuServicePage.waitForSecond(5);
    }

    @Then("select a new tag then verify if selected: $tagName")
    public void clickWelcomeMessageLinkSubscriptionAccount(String tagName) {
        ToolkitPage toolkitPage = factory.pageAs(ToolkitPage.class);
        toolkitPage.getTagPreferenceEditBtn().click();
        toolkitPage.getEnableTag().clickToCheck();
        toolkitPage.addNewTag(tagName);

        toolkitPage.getTagItem(tagName).click();
        toolkitPage.getSaveTagPerferenceBtn().click();
        toolkitPage.page().waitForSecond(5);
        String result = toolkitPage.getSavedTagItem().getText();
        WebAssert.assertTrue("Failed to save the latest tag", toolkitPage.getSavedTagItem().getText().equals(tagName));

    }


    @Then("add duplicated tag and track error message")
    public void addDuplicatedTag() {
        ToolkitPage toolkitPage = factory.pageAs(ToolkitPage.class);
        toolkitPage.getTagPreferenceEditBtn().click();
        toolkitPage.getEnableTag().clickToCheck();
        String tagName  = toolkitPage.getFirstTagName().getText();
        toolkitPage.addNewTag(tagName);

        toolkitPage.getDuplicatedTagError().waitForElementReady(20);
        WebAssert.assertTrue("",toolkitPage.getDuplicatedTagError().getText().contains("Failed to create tag with duplicated tag name, please rename the new tag."));

    }


    @Then("in toolkit page verify if tag is existed: $tagName")
    public void verifyIfTagExisted (String tagName) {
        ToolkitPage toolkitPage = factory.pageAs(ToolkitPage.class);
        toolkitPage.getTagPreferenceEditBtn().click();
        toolkitPage.getEnableTag().clickToCheck();
        toolkitPage.getAddTagIcon().waitForElementEnabled(30);

        Assert.assertTrue("Tag is shown",toolkitPage.getTagItem(tagName).isReady());
    }


}
