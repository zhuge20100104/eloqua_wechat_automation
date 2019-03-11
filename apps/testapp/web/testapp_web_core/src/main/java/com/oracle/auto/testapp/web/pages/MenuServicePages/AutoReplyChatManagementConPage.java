package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import com.oracle.auto.web.comp.html.HTMLTextArea;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 8/02/2017.
 */

@Component
public class AutoReplyChatManagementConPage extends TestAppPageBase {

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }
    private HTMLDiv formBox = new HTMLDiv("xpath=//div[@id='ojChoiceId_wx-registration-form-select']//a");
    private HTMLInput inputForm = new HTMLInput("BDD_jFindByAttr(\"input\",\"title\",\"Search field\")[0]");
    private HTMLDiv exceptedForm = new HTMLDiv("BDD_jQuery(\"#oj-listbox-results-wx-registration-form-select\")[0].jFind(BDD_jQuery(\".oj-listbox-result-label\"))[%d]");
    private HTMLDiv formLists = new HTMLDiv("BDD_jQuery(\"#oj-listbox-results-wx-registration-form-select\")[0].jFind(BDD_jQuery(\".oj-listbox-result-label\"))");
    private HTMLDiv loadBtn = new HTMLDiv("id=wx-registration-form-popup-load");

    private HTMLDiv twoMinsTimeOut = new HTMLDiv("BDD_jFindByAttr(\"label\",\"for\",\"wx-timeout-limit-120\")[0]");

    private HTMLTextArea errorMsgTimeOut = new HTMLTextArea("id=wx-timeout-error-text-area");
    private HTMLDiv openID = new HTMLDiv("BDD_jFindByAttr(\"input\",\"name\",\"wechatOpenIdRadio\")[4]");
    private HTMLDiv thanksMsgArrow = new HTMLDiv("BDD_jQuery(\"#oj-select-choice-wx-message-type-select\")[0].jFind(BDD_jQuery(\".oj-select-arrow\"))[0]");
    private HTMLDiv thanksMsgEdit = new HTMLDiv("xpath=//div[@id='wx-success-message']//a[@class='oj-margin-horizontal wx-pencil-icon']");

    private HTMLDiv submissionMsgArrow = new HTMLDiv("BDD_jQuery(\"#wx-fail-message\")[0].jFind(BDD_jQuery(\".oj-select-arrow\"))[0]");
    private HTMLDiv submissionMsgEdit = new HTMLDiv("xpath=//div[@id='wx-fail-message']//a[@class='oj-margin-horizontal wx-pencil-icon']");
    //private HTMLDiv richMediaMsgType = new HTMLDiv("xpath=//div[@id='oj-listbox-drop']//div[text()=\"Template Message\"]");

    private HTMLDiv chatErrorMessage = new HTMLDiv("xpath=//div[@class='oj-messaging-inline-container']");
    private HTMLDiv confirmReload = new HTMLDiv("xpath=//button[@id='wx-clear-question-del']");

    public HTMLDiv getConfirmReload() {
        confirmReload.setPage(page());
        return confirmReload;
    }

    public HTMLDiv getChatErrorMessage() {
        chatErrorMessage.setPage(page());
        return chatErrorMessage;
    }
    public HTMLDiv getMsgType(String type) {
        String locator = String.format("xpath=//div[@id='oj-listbox-drop']//div[text()='%s']", type);
        HTMLDiv msgType = new HTMLDiv(locator);
        msgType.setPage(page());
        return msgType;
    }

    public HTMLDiv getMinutesTimeOut(int seconds) {
        String locator = String.format("BDD_jFindByAttr(\"label\",\"for\",\"wx-timeout-limit-%s\")[0]",String.valueOf(seconds));
        HTMLDiv minutes = new HTMLDiv(locator);
        minutes.setPage(page());
        return minutes;
    }

    public HTMLDiv getThanksMsgEdit() {
        thanksMsgEdit.setPage(page());
        return thanksMsgEdit;
    }

    public HTMLDiv getSubmissionMsgEdit() {
        submissionMsgEdit.setPage(page());
        return submissionMsgEdit;
    }

    public HTMLDiv getOpenID() {
        openID.setPage(page());
        return openID;
    }

    public HTMLDiv getThanksMsgArrow() {
        thanksMsgArrow.setPage(page());
        return thanksMsgArrow;
    }

    public HTMLDiv getSubmissionMsgArrow() {
        submissionMsgArrow.setPage(page());
        return submissionMsgArrow;
    }

    public HTMLDiv getFormBox() {
        formBox.setPage(page());
        return formBox;
    }


    public HTMLInput getInputForm() {
        inputForm.setPage(page());
        return inputForm;
    }

    public HTMLDiv getExceptedForm() {
        exceptedForm.setPage(page());
        return exceptedForm;
    }

    public HTMLDiv getFormLists() {
        formLists.setPage(page());
        return formLists;
    }

    public int getFormIndex(String title) {
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
        exceptedForm.setPage(page());
        exceptedForm.setLocator(String.format(exceptedForm.getRawLocator(), index));
        return exceptedForm;
    }

    public HTMLDiv getLoadBtn() {
        loadBtn.setPage(page());
        return loadBtn;
    }



    public HTMLDiv getTwoMinsTimeOut() {
        twoMinsTimeOut.setPage(page());
        return twoMinsTimeOut;
    }


    public HTMLTextArea getErrorMsgTimeOut() {
        errorMsgTimeOut.setPage(page());
        return errorMsgTimeOut;
    }


    public HTMLDiv openIDRadio() {
        //BDD_jFindByAttr("input","name","wechatOpenIdRadio")[1]
        openID.setPage(page());
        return openID;

    }





    public int getMsgTypeIndex(String value) {
        switch (value) {
            case "Text Message":
                //  case "无":
                return 0;
            case "Rich Media Message":
                return 1;
            case "Template Message":
                // case "微信 ID":
                return 2;
            default:
                return -1;

        }
    }


    public void waitForSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
