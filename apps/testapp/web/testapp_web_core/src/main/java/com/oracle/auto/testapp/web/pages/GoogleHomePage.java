package com.oracle.auto.testapp.web.pages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLButton;
import com.oracle.auto.web.comp.html.HTMLInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by zhous5 on 2017/6/12.
 */

@Component
public class GoogleHomePage extends TestAppPageBase {
    HTMLInput inputField;
    HTMLButton searchBtn;

    public GoogleHomePage() {
    }

    @PostConstruct
    public void init() {
        registerComp(inputField);
        injectPageToChildComponents(this);

        page.deleteAllVisibleCookies();
        String loginUrl = page.serverDefaultURL();
        page.open(loginUrl);

        waitForPageReady();
    }

    public void setInputField(HTMLInput inputField) {
        this.inputField = inputField;
    }

    public HTMLInput getInputField() {
        return this.inputField;
    }

    public void setSearchBtn(HTMLButton searchBtn) {
        this.searchBtn = searchBtn;
    }

    public HTMLButton getSearchBtn() {
        return searchBtn;
    }
}
