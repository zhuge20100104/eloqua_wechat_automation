package com.oracle.auto.testapp.web.pages.AssetsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grcao on 3/26/2017.
 */

@Component
public class RegistrationServiceLandingPage extends TestAppPageBase {

    private HTMLDiv formBodyLink;

    private HTMLDiv formBox;
    private HTMLInput formInputBox;
    private HTMLDiv exceptedForm;
    private HTMLDiv formLists;

    private HTMLDiv openIDArrow;
    private HTMLDiv exceptedFormContent;
    private HTMLDiv formContents;

    private HTMLDiv saveBtn;
    private HTMLDiv closeBtn;
    private HTMLValueDisplay successfulMsg;



    public HTMLDiv getFormBodyLink() {
        return formBodyLink;
    }

    public void setFormBodyLink(HTMLDiv formBodyLink) {
        this.formBodyLink = formBodyLink;
    }

    public HTMLDiv getFormBox() {
        return formBox;
    }

    public void setFormBox(HTMLDiv formBox) {
        this.formBox = formBox;
    }

    public HTMLInput getFormInputBox() {
        return formInputBox;
    }

    public void setFormInputBox(HTMLInput formInputBox) {
        this.formInputBox = formInputBox;
    }

    public HTMLDiv getExceptedForm() {
        return exceptedForm;
    }

    public void setExceptedForm(HTMLDiv exceptedForm) {
        this.exceptedForm = exceptedForm;
    }

    public HTMLDiv getFormLists() {
        return formLists;
    }

    public void setFormLists(HTMLDiv formLists) {
        this.formLists = formLists;
    }

    public int getExceptedFormIndex(String title) {
        int numbers = getFormLists().getMethodProp(".size()").AsInt();
        int index = -1;
        for (int j = 0; j < numbers; j++) {
            if (getExceptedForm(j).getText().equals(title)) {
                index = j;
                break;
            }
        }
        return index;
    }

    public HTMLDiv getExceptedForm(int index) {
        exceptedForm.setLocator(String.format(exceptedForm.getRawLocator(), index));
        return exceptedForm;
    }


    public HTMLDiv getOpenIDArrow() {
        return openIDArrow;
    }

    public void setOpenIDArrow(HTMLDiv openIDArrow) {
        this.openIDArrow = openIDArrow;
    }

    public HTMLDiv getExceptedFormContent() {
        return exceptedFormContent;
    }

    public void setExceptedFormContent(HTMLDiv exceptedFormContent) {
        this.exceptedFormContent = exceptedFormContent;
    }

    public HTMLDiv getFormContents() {
        return formContents;
    }

    public void setFormContents(HTMLDiv formContents) {
        this.formContents = formContents;
    }

    public int getExceptedFormContentIndex(String content) {
        int numbers = getFormContents().getMethodProp(".size()").AsInt();
        int index = -1;
        for (int j = 0; j < numbers; j++) {
            if (getExceptedFormContent(j).getText().contains(content)) {
                index = j;
                break;
            }
        }
        return index;
    }

    public HTMLDiv getExceptedFormContent(int index) {
        exceptedFormContent.setLocator(String.format(exceptedFormContent.getRawLocator(), index));
        return exceptedFormContent;
    }

    public HTMLDiv getSaveBtn() {
        return saveBtn;
    }

    public void setSaveBtn(HTMLDiv saveBtn) {
        this.saveBtn = saveBtn;
    }

    public void setCloseBtn(HTMLDiv closeBtn) {
        this.closeBtn = closeBtn;
    }

    public HTMLDiv getCloseBtn() {
        return closeBtn;
    }

    public void setSuccessfulMsg(HTMLValueDisplay successfulMsg) {
        this.successfulMsg = successfulMsg;
    }

    public HTMLValueDisplay getSuccessfulMsg() {
        return successfulMsg;
    }

    public boolean successfulMsgIsVisibleOrNot() {
        int index = 30 * 60;
        boolean status = false;
        for (int i = 0; i < index; i++) {
            if (getSuccessfulMsg().isReady()) {

                status = true;
                break;
            } else {
                page().waitMilliSecond();
            }
        }

        return status;
    }



    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }


}
