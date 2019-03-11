package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLCheckbox;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLTextArea;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Grace on 5/23/2018.
 */

@Component
public class ContactInfoSyncConPage extends TestAppPageBase {

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    private HTMLCheckbox taggingFollowers = new HTMLCheckbox("id=synchronizeactiontag");
    private HTMLCheckbox removeTag = new HTMLCheckbox("id=removesynchronizeactiontag");
    private HTMLCheckbox updateStatus = new HTMLCheckbox("id=synchronizeactionsubscription");

    private HTMLDiv addTagIcon = new HTMLDiv("id=add-tag");
    private HTMLTextArea tagName = new HTMLTextArea("id=create-tag");
    private HTMLDiv saveBtn =   new HTMLDiv("xpath=//span[text()=\"Save\"]");
    private HTMLDiv submitBtn =   new HTMLDiv("xpath=//span[text()=\"Submit\"]");

    private HTMLDiv firstTagName =   new HTMLDiv("BDD_jQuery(\".wx-tag-radio-set\")[0]");
    private HTMLDiv errorMsg =   new HTMLDiv("id=flashMessages");

    public HTMLCheckbox getTaggingFollowers() {
        taggingFollowers.setPage(page());
        return taggingFollowers;
    }

    public HTMLCheckbox getRemoveTag() {
        removeTag.setPage(page());
        return removeTag;
    }

    public HTMLCheckbox getUpdateStatus() {
        updateStatus.setPage(page());
        return updateStatus;
    }

    public HTMLDiv getAddTagIcon() {
        addTagIcon.setPage(page());
        return addTagIcon;
    }

    public HTMLTextArea getTagName() {
        tagName.setPage(page());
        return tagName;
    }

    public HTMLDiv getTagName(String type) {
        String locator = String.format("xpath=//div[@id='querytagsarea']//label[contains(text(),'%s')]", type);
        HTMLDiv msgType = new HTMLDiv(locator);
        msgType.setPage(page());
        return msgType;
    }
    public HTMLDiv getSaveBtn() {
        saveBtn.setPage(page());
        return saveBtn;
    }

    public HTMLDiv getSubmitBtn() {
        submitBtn.setPage(page());
        return submitBtn;
    }

    public HTMLDiv getFirstTagName() {
        firstTagName.setPage(page());
        return firstTagName;
    }
    public HTMLDiv getErrorMsg() {
        errorMsg.setPage(page());
        return errorMsg;
    }

}
