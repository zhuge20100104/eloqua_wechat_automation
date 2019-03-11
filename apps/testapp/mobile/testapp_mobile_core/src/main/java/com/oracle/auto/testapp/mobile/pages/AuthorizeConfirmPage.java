package com.oracle.auto.testapp.mobile.pages;

import com.oracle.auto.mobile.components.elements.webview.WebDiv;
import com.oracle.auto.testapp.mobile.ancestor.MobileWebScreenBase;

import javax.annotation.PostConstruct;

/**
 * Created by stepzhou on 7/25/2017.
 */

public class AuthorizeConfirmPage extends MobileWebScreenBase {
    private WebDiv authorizeTexts;
    private WebDiv continueUse;

    public void setAuthorizeTexts(WebDiv authorizeTexts) {
        this.authorizeTexts = authorizeTexts;
    }

    public WebDiv getAuthorizeTexts() {
        return authorizeTexts;
    }

    public void setContinueUse(WebDiv continueUse) {
        this.continueUse = continueUse;
    }

    public WebDiv getContinueUse() {
        return continueUse;
    }

    @PostConstruct
    public void init() {
        registerComp(continueUse);
        injectMobileDriverToChildComponents(this);
        waitForScreenReady();
    }

}
