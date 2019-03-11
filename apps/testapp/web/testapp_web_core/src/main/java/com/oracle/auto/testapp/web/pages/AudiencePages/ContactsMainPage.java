package com.oracle.auto.testapp.web.pages.AudiencePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import com.oracle.auto.web.comp.html.HTMLLabel;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by grace on 28/3/2017.
 */

@Component
public class ContactsMainPage extends TestAppPageBase {

    private HTMLDiv popUpMessage;
    private HTMLInput search;
    private HTMLDiv status;

    private HTMLDiv emailAddress = new HTMLDiv("xpath=//label[text()=\"autotest5357@blackhole.oracle.com\"]");
    private HTMLDiv serviceOpenID = new HTMLDiv("xpath=//label[text()=\"opKNfxHKfMtV7HJmYa3utyFXXTK8\"]");
    private HTMLDiv deleteContact = new HTMLDiv("xpath=//span[text()=\"Delete\"]");

    public HTMLDiv getDeleteContact() {
        deleteContact.setPage(page());
        return deleteContact;
    }

    public HTMLDiv getConfirmDelete() {
        confirmDelete.setPage(page());
        return confirmDelete;
    }

    private HTMLDiv confirmDelete = new HTMLDiv("//div[@id='warning-alert-pane-view-buttonOne']");

    public HTMLDiv getContactLocation(String text) {
        String textStr = String.format("xpath=//label[text()='%s']", text);
        HTMLDiv location = new HTMLDiv(textStr);
        location.setPage(page());
        return location;
    }

    public HTMLDiv getContactRow(String text) {
        String textStr = String.format("//div[@id='contact-overview-contacts-table']//label[text()='%s']/parent::*/parent::*]", text);
        HTMLDiv location = new HTMLDiv("BDD_jQuery(\".sc-table-row-view\")[0]");
        location.setPage(page());
        page.contextClick(textStr);
        return location;
    }

    public HTMLDiv getServiceOpenID() {
        serviceOpenID.setPage(page());
        return serviceOpenID;
    }

    @PostConstruct
    public void init() {
        // registerComp(search);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setStatus(HTMLDiv status) {
        this.status = status;
    }

    public HTMLDiv getStatus() {
        return status;
    }

    public HTMLDiv getPopUpMessage() {
        return popUpMessage;
    }

    public void setPopUpMessage(HTMLDiv popUpMessage) {
        this.popUpMessage = popUpMessage;
    }

    public void setSearch(HTMLInput search) {
        this.search = search;
    }

    public HTMLInput getSearch() {
        return search;
    }


}
