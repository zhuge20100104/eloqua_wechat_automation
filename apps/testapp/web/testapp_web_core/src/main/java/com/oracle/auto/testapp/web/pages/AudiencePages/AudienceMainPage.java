package com.oracle.auto.testapp.web.pages.AudiencePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import com.oracle.auto.web.comp.html.HTMLLabel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by stepzhou on 8/16/2017.
 */

@Component
public class AudienceMainPage extends TestAppPageBase {

    private HTMLDiv newBtn;
    private HTMLInput fieldDetails;
    private HTMLDiv exceptedField;
    private HTMLDiv listedFields;
    private HTMLDiv emailAddress;
    private HTMLDiv createBtn;

    private HTMLLabel firstName;

    public void setFirstName(HTMLLabel firstName){
        this.firstName = firstName;
    }

    public HTMLLabel getFirstName(){
        return firstName;
    }

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setNewBtn(HTMLDiv newBtn) {
        this.newBtn = newBtn;
    }

    public HTMLDiv getNewBtn() {
        return newBtn;
    }

    public void setFieldDetails(HTMLInput fieldDetails) {
        this.fieldDetails = fieldDetails;
    }

    public HTMLInput getFieldDetails() {
        return fieldDetails;
    }

    public void setExceptedField(HTMLDiv exceptedField) {
        this.exceptedField = exceptedField;
    }

    public HTMLDiv getExceptedField() {
        return exceptedField;
    }

    public void setListedFields(HTMLDiv listedFields) {
        this.listedFields = listedFields;
    }

    public HTMLDiv getListedFields() {
        return listedFields;
    }


    public void setEmailAddress(HTMLDiv emailAddress) {
        this.emailAddress = emailAddress;
    }

    public HTMLDiv getEmailAddress() {
        return emailAddress;
    }

    public void setCreateBtn(HTMLDiv createBtn) {
        this.createBtn = createBtn;
    }

    public HTMLDiv getCreateBtn() {
        return createBtn;
    }
}
