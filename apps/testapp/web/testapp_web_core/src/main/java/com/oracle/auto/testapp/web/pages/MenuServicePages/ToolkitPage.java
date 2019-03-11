package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLCheckbox;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLTextArea;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 5/24/2018.
 */

@Component
public class ToolkitPage extends TestAppPageBase {

    private HTMLDiv tagPreferenceEditBtn = new HTMLDiv("//span[@id='oj-collapsible-tag-preference-header']/a[contains(@class,'wx-font-icon')]");
    private HTMLDiv openIDEditBtn = new HTMLDiv("//div[@id='super-openid']//a[contains(@class,'wx-font-icon')]");
    private HTMLCheckbox enableTag = new HTMLCheckbox("id=enableAutoTag");

    private HTMLDiv addTagIcon = new HTMLDiv("id=add-tag");
    private HTMLTextArea tagName = new HTMLTextArea("id=create-tag");
    private HTMLDiv saveNewTagBtn = new HTMLDiv("xpath=//div[@id='oj-collapsible-tag-preference-content']//span[text()=\"Save\"]");
    //  private HTMLDiv tagItem = new HTMLDiv("xpath=//div[@id='tag-preference']//label[contains(text(),'%s')]");
    private HTMLDiv saveTagPerferenceBtn = new HTMLDiv("xpath=//div[@id='tag-preference']//span[text()=\"Save\"]");
    private HTMLDiv savedTagItem = new HTMLDiv("xpath=//span[@id='oj-collapsible-tag-preference-header']/span[contains(@class,\"wx-font-blue\")]");

    private HTMLDiv firstTagName = new HTMLDiv("BDD_jQuery(\".wx-toolkit-tag-radio-set\")[0]");
    private HTMLDiv duplicatedTagError = new HTMLDiv("id=tag-preference-error");

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }


    public HTMLDiv getTagPreferenceEditBtn() {
        tagPreferenceEditBtn.setPage(page());
        return tagPreferenceEditBtn;
    }

    public HTMLDiv getOpenIDEditBtn() {
        openIDEditBtn.setPage(page());
        return openIDEditBtn;
    }

    public HTMLDiv getAddTagIcon() {
        addTagIcon.setPage(page());
        return addTagIcon;
    }

    public HTMLTextArea getTagName() {
        tagName.setPage(page());
        return tagName;
    }

    public HTMLDiv getsaveNewTagBtn() {
        saveNewTagBtn.setPage(page());
        return saveNewTagBtn;
    }

    public HTMLCheckbox getEnableTag() {
        enableTag.setPage(page());
        return enableTag;
    }

//    public HTMLDiv getTagItem() {
//        tagItem.setPage(page());
//        return tagItem;
//    }

    public HTMLDiv getTagItem(String tagName) {
        String locator = String.format("xpath=//div[@id='tag-preference']//label[contains(text(),'%s')]", tagName);
        //div[@id='tag-preference']//label[contains(text(),"asdfasdf1234")]
        HTMLDiv tagItem = new HTMLDiv(locator);
        tagItem.setPage(page());
        return tagItem;
    }

    public HTMLDiv getSaveTagPerferenceBtn() {
        saveTagPerferenceBtn.setPage(page());
        return saveTagPerferenceBtn;
    }

    public HTMLDiv getSavedTagItem() {
        savedTagItem.setPage(page());
        return savedTagItem;
    }

    public HTMLDiv getFirstTagName() {
        firstTagName.setPage(page());
        return firstTagName;
    }

    public HTMLDiv getDuplicatedTagError() {
        duplicatedTagError.setPage(page());
        return duplicatedTagError;
    }

    public void waitForSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addNewTag(String tagName) {
        getAddTagIcon().click();
        getTagName().setValue(tagName);
        getsaveNewTagBtn().click();
    }
}
