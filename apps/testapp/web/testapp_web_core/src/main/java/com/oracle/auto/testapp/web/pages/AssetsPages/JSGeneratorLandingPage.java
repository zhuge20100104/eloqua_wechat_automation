package com.oracle.auto.testapp.web.pages.AssetsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import com.oracle.auto.web.comp.html.HTMLValueDisplay;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by grcao on 3/30/2017.
 */

@Component
public class JSGeneratorLandingPage extends TestAppPageBase {

    private HTMLDiv openIDArrow;
    private HTMLDiv nickNameArrow;
    private HTMLDiv exceptedFormContent;
    private HTMLDiv formContentLists;

    private HTMLDiv saveBtn;
    private HTMLDiv closeBtn;
    private HTMLValueDisplay successfulMsg;


    public HTMLDiv getOpenIDArrow() {
        return openIDArrow;
    }

    public void setOpenIDArrow(HTMLDiv openIDArrow) {
        this.openIDArrow = openIDArrow;
    }

    public HTMLDiv getNickNameArrow() {
        return nickNameArrow;
    }

    public void setNickNameArrow(HTMLDiv nickNameArrow) {
        this.nickNameArrow = nickNameArrow;
    }

    public HTMLDiv getExceptedFormContent() {
        return exceptedFormContent;
    }

    public void setExceptedFormContent(HTMLDiv exceptedFormContent) {
        this.exceptedFormContent = exceptedFormContent;
    }

    public HTMLDiv getFormContentLists() {
        return formContentLists;
    }

    public void setFormContentLists(HTMLDiv formContentLists) {
        this.formContentLists = formContentLists;
    }

    public int getExceptedFormContentIndex(String content) {
        int numbers = getFormContentLists().getMethodProp(".size()").AsInt();
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
