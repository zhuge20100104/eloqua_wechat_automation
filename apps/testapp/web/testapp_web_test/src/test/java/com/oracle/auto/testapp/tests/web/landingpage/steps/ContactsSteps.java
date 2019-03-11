package com.oracle.auto.testapp.tests.web.landingpage.steps;

import com.oracle.auto.testapp.web.pages.AudiencePages.AudienceMainPage;
import com.oracle.auto.testapp.web.pages.AudiencePages.ContactsMainPage;
import com.oracle.auto.testapp.web.pages.CampaignPages.*;
import com.oracle.auto.testapp.web.pages.HomePage;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.pages.WebDriverEx;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebAssert;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Created by grace on 8/21/2017.
 */

public class ContactsSteps {
    private TestAppPageFactory factory;

    public ContactsSteps() {
        factory = TestAppPageFactory.getInstance();
    }

    @When("move cursor to audience")
    public void moveCursorToAudience() {
        HomePage eloquaHomePage = factory.pageAs(HomePage.class);
        int index = 0;
        while ((!eloquaHomePage.getHomeTopNavigator().getAudienceMenu().getAudience().isReady()) && (index < 60)) {
            eloquaHomePage.page().waitForSecond(2);
            index++;
        }
        eloquaHomePage.getHomeTopNavigator().getAudienceMenu().getAudience().moveToElement();
    }

    @Then("click the contacts button")
    public void clickContactButton() {
        HomePage eloquaHomePage = factory.pageAs(HomePage.class);
        eloquaHomePage.getHomeTopNavigator().getAudienceMenu().getContacts().click();

        ContactsMainPage contactsMainPage = factory.pageAs(ContactsMainPage.class);
        contactsMainPage.page().waitForShort();
        if (contactsMainPage.getPopUpMessage().isReady()) {
            contactsMainPage.getPopUpMessage().click();
        }
    }

    @Then("input contact mail: $mail")
    public void inputContactMail(String mail) {
        ContactsMainPage contactsMainPage = factory.pageAs(ContactsMainPage.class);
        contactsMainPage.page().waitForSecond(2);
        contactsMainPage.getSearch().setValue(mail);
        contactsMainPage.page().waitForSecond(5);
    }

    @Then("the fist name should be $firstName")
    public void verifyFirstName(String firstName) {
        WebDriverEx.waitFor(6);
        AudienceMainPage audienceMainPage = factory.pageAs(AudienceMainPage.class);
        WebAssert.assertTrue("The first name is not correct.", audienceMainPage.getFirstName().getTextContent().equalsIgnoreCase(firstName));
    }

    @Then("the status should be: $message")
    public void showStatusMessage(String message) {
        ContactsMainPage contactsMainPage = factory.pageAs(ContactsMainPage.class);
        WebDriverEx.waitFor(6);
        WebAssert.assertTrue("The status message is not correct.", contactsMainPage.getStatus().getText().contains(message));
    }

    @Then("check the email: $emailAddress and openID: $openID")
    public void checkEmailandOpenID(String emailAddress, String openID){
        ContactsMainPage contactsMainPage = factory.pageAs(ContactsMainPage.class);
        WebAssert.assertTrue("Email Address doesn't appear",contactsMainPage.getContactLocation(emailAddress).isPresent());
        WebAssert.assertTrue("OpenID doesn't appear",contactsMainPage.getContactLocation(openID).isPresent());
//        contactsMainPage.getContactRow(emailAddress).contextClick();
//        contactsMainPage.getDeleteContact().click();
//        contactsMainPage.getConfirmDelete().click();
    }

}
