package com.oracle.auto.testapp.tests.web.service.steps;

import com.oracle.auto.testapp.mobile.pages.LandingPage;
import com.oracle.auto.testapp.web.model.Message;
import com.oracle.auto.testapp.web.pages.HomePage;
import com.oracle.auto.testapp.web.pages.MenuServicePages.*;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebAssert;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;


/**
 * Created by grcao on 7/24/2017.
 */

public class WelcomeMessageSteps {
    private TestAppPageFactory factory;

    public WelcomeMessageSteps() {
        factory = TestAppPageFactory.getInstance();
    }

    @Then("click service account welcome message link")
    public void clickWelcomeMessageLinkServiceAccount() {
        MenuServicePage menuServicePage = factory.pageAs(MenuServicePage.class);
        menuServicePage.getWelcomeMsgLinkServiceAccount().waitForElementReady(20);
        menuServicePage.getWelcomeMsgLinkServiceAccount().click();
        menuServicePage.waitForSecond(5);
    }

    @Then("click subscription account welcome message link")
    public void clickWelcomeMessageLinkSubscriptionAccount() {
        MenuServicePage menuServicePage = factory.pageAs(MenuServicePage.class);
        menuServicePage.getWelcomeMsgLinkSubscriptionAccount().waitForElementReady(20);
        menuServicePage.getWelcomeMsgLinkSubscriptionAccount().click();
        menuServicePage.waitForSecond(5);
    }

    @Then("click edit button in welcome message page")
    public void clickEditWelcomeMsgBtn() {
        WelcomeMessagePage welcomeMessagePage = factory.pageAs(WelcomeMessagePage.class);
        welcomeMessagePage.getEditBtn().click();
        welcomeMessagePage.page().waitForShort();
    }

    @Then("enable the Welcome Message page")
    public void enableWelcomeMessage() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        if (welcomeMessageConPage.getDisableStatus().isReady()) {
            welcomeMessageConPage.getWelcomeMsgEnableBtn().click();
        }
    }

    @Then("disable the Welcome Message page")
    public void disableWelcomeMessage() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        if (welcomeMessageConPage.getEnableStatus().isReady()) {
            WebAssert.assertFalse(welcomeMessageConPage.getDisableStatus().isReady());
            welcomeMessageConPage.getWelcomeMsgEnableBtn().click();
        }
    }

    @Then("verify the text and rich media button is disappear")
    public void verifyTextAndRichMediaBtnDisappear() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        WebAssert.assertTrue(welcomeMessageConPage.getDisableStatus().isReady());
    }

    @Then("select text type")
    public void selectConfigurationText() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        welcomeMessageConPage.getTextTypeBtn().click();
    }

    @Then("select rich media type")
    public void selectConfigurationRichMedia() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        welcomeMessageConPage.getRichMediaTypeBtn().click();
    }

    @Then("select one rich media item for the welcome message")
    public void selectOneRichMedia() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        welcomeMessageConPage.getRandomRichMediaNews().click();
    }

    @Then("select the first rich media item for the welcome message")
    public void selectFirstRichMedia() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        welcomeMessageConPage.getFirstRichMediaItem().click();
    }

    @Then("verify if rich media item is selected")
    public void verifyIfRichMediaSelected() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        WebAssert.assertTrue(welcomeMessageConPage.getMediaNewsSelected().isReady());
    }

    @Then("apply the welcome message settings")
    public void applyWelcomeMessageSettings() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        welcomeMessageConPage.getSaveBtn().click();
        welcomeMessageConPage.page().waitForShort();
    }

    @Then("clear text configuration")
    public void cleanConfigurationText() {
//        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
//        welcomeMessageConPage.getConfigurationArea().doClearText();

        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.waitForSecond(2);
        String addContentScript = "var element=CKEDITOR.instances['editor-welcome-message'].document.$.getElementsByClassName('cke_editable')[0];element.innerHTML='';";
        messagesConPage.page().executeScriptEx(addContentScript);
        messagesConPage.waitForSecond(2);
    }


    @Then("input Link with URL: $URL and text: $text")
    public void inputURLandText(String URL, String text){
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        welcomeMessageConPage.getTrackConfig().click();
        welcomeMessageConPage.getDisplayText().setValue(text);
        welcomeMessageConPage.getEmbeddedURL().setValue(URL);
        welcomeMessageConPage.getOKBtn().click();
    }

    @Then("click configuration link button")
    public void clickConLinkButton() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        welcomeMessageConPage.getTrackConfig().click();
    }

    @Then("input display text $text")
    public void inputDisplayText(String text) {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        welcomeMessageConPage.getDisplayText().setValue(text);
    }

    @Then("input embedded url $url")
    public void inputEmbeddedURL(String contentURL) {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        if (contentURL.contains("LandingPage")) {
            MessagesSteps messagesSteps = new MessagesSteps();
            welcomeMessageConPage.getEmbeddedURL().setValue(messagesSteps.getLandingPageURL(contentURL));
        } else {
            welcomeMessageConPage.getEmbeddedURL().setValue(contentURL);
        }
    }

    @Then("click OK button for Link page")
    public void clickLinkPageOKBtn() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        welcomeMessageConPage.getOKBtn().click();
    }

    public String getURLwithLandingPageLink(String text) {
        String textContent = "";
        if (text.contains("%service.account.JS.landing.page%")) {
            textContent = text.replace("%service.account.JS.landing.page%", PropertyConfiger.instance().getEnvData("%service.account.JS.landing.page%", ""));
        } else if (text.contains("SubscriptionAccountLandingPage")) {
            textContent = text.replace("SubscriptionAccountLandingPage",PropertyConfiger.instance().getEnvData("%service.account.JS.landing.page%", ""));

        } else {

        }
        return textContent;
    }

    @Then("input text configuration in welcome message content page: $text")
    public void inputConfigurationText(String text) {
//        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
//        welcomeMessageConPage.getConfigurationArea().click();
//        welcomeMessageConPage.getConfigurationArea().insertValue(text);
//        welcomeMessageConPage.getConfigurationArea().insertLineBreak();

        MessagesConPage messagesConPage = factory.pageAs(MessagesConPage.class);
        messagesConPage.waitForSecond(2);
        String addContentScript = "var para=document.createElement('p');var node=document.createTextNode('" + text + "');para.appendChild(node);var element=CKEDITOR.instances['editor-welcome-message'].document.$.getElementsByClassName('cke_editable')[0];element.appendChild(para)";
        messagesConPage.page().executeScriptEx(addContentScript);
        messagesConPage.waitForSecond(2);
    }

    @Then("click save and close btn to save welcome msg")
    public void saveWelcomeMsg() {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        welcomeMessageConPage.getSaveBtn().waitForElementReady(10);
        welcomeMessageConPage.getSaveBtn().click();
        welcomeMessageConPage.waitForSecond(10);
    }

    @Then("the welcome message is $text")
    public void verifyWelcomeContent(String text) {
        WelcomeMessageConPage welcomeMessageConPage = factory.pageAs(WelcomeMessageConPage.class);
        String current = welcomeMessageConPage.getWelcomeContent().getText();
        WebAssert.assertTrue("Current message is: " + current + ", but excepted is: " + text, current.contains(text));
    }

}
