package com.oracle.auto.testapp.mobile.pages;

import com.oracle.auto.mobile.components.elements.webview.WebDiv;
import com.oracle.auto.testapp.mobile.ancestor.MobileWebScreenBase;

import javax.annotation.PostConstruct;

/**
 * Created by stepzhou on 7/25/2017.
 */

public class AuthorizeSuccessPage extends MobileWebScreenBase {
    private WebDiv authorizeSuccess;

    public void setAuthorizeSuccess(WebDiv authorizeSuccess) {
        this.authorizeSuccess = authorizeSuccess;
    }

    public WebDiv getAuthorizeSuccess() {
        return authorizeSuccess;
    }

    @PostConstruct
    public void init() {
        registerComp(authorizeSuccess);
        injectMobileDriverToChildComponents(this);
        waitForScreenReady();
    }

}
