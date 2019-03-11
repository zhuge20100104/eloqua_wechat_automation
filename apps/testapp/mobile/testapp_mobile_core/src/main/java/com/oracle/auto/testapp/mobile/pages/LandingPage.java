package com.oracle.auto.testapp.mobile.pages;

import com.oracle.auto.mobile.components.elements.webview.WebButton;
import com.oracle.auto.mobile.components.elements.webview.WebTextField;
import com.oracle.auto.mobile.driver.MobileDriverEx;
import com.oracle.auto.mobile.exceptions.MobileExceptionBase;
import com.oracle.auto.testapp.mobile.ancestor.MobileScreenBase;
import com.oracle.auto.testapp.mobile.ancestor.MobileWebScreenBase;
import com.oracle.auto.web.utility.PropertyConfiger;

import javax.annotation.PostConstruct;

/**
 * Created by stepzhou on 7/25/2017.
 */

public class LandingPage extends MobileWebScreenBase {
    private WebTextField firstName;
    private WebTextField lastName;
    private WebTextField emailAddress;

    private WebButton submitBtn;

    public void setFirstName(WebTextField firstName) {
        this.firstName = firstName;
    }

    public WebTextField getFirstName() {
        return firstName;
    }

    public void setLastName(WebTextField lastName) {
        this.lastName = lastName;
    }

    public WebTextField getLastName() {
        return lastName;
    }

    public void setEmailAddress(WebTextField emailAddress) {
        this.emailAddress = emailAddress;
    }

    public WebTextField getEmailAddress() {
        return emailAddress;
    }

    public void setSubmitBtn(WebButton submitBtn) {
        this.submitBtn = submitBtn;
    }

    public WebButton getSubmitBtn() {
        return submitBtn;
    }

    @PostConstruct
    public void init() {
        //registerComp(firstName);
        //registerComp(lastName);
        registerComp(emailAddress);
        registerComp(submitBtn);
        injectMobileDriverToChildComponents(this);
        waitForScreenReady();
    }

}
