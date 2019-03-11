package com.oracle.auto.apps.commons;

import com.oracle.auto.web.comp.html.HTMLButton;
import com.oracle.auto.web.comp.html.HTMLParagraph;
import com.oracle.auto.web.comp.html.HTMLTextbox;
import com.oracle.auto.web.exceptions.FormValidationException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LoginPage extends AppCommonPageAdaptor {
    private static Logger log = Logger.getLogger(LoginPage.class);
    private HTMLTextbox userNameTxt;
    private HTMLTextbox passwordTxt;
    private HTMLButton submitBtn;
    private HTMLButton forgotPwdBtnLink;
    private HTMLParagraph errorMsg;
    private boolean allowSignout = true;

    public LoginPage(boolean allowSingOut) {
        super();
        this.allowSignout = allowSingOut;
    }

    @PostConstruct
    public void init() {
        registerComp(userNameTxt);
        registerComp(passwordTxt);
        registerComp(submitBtn);
        registerComp(forgotPwdBtnLink);
        injectPageToChildComponents(this);
        if (waitForPageReadyTimout(2)) return; // check if it's already in login page.
        if (allowSignout) {
            page.deleteAllVisibleCookies();
            String loginUrl = page.serverDefaultURL();
            page.open(loginUrl);
        }
        waitForPageReady();
    }

    public HTMLTextbox getUserNameTxt() {
        return userNameTxt;
    }

    @Autowired
    public void setUserNameTxt(HTMLTextbox userNameTxt) {
        this.userNameTxt = userNameTxt;
    }

    public HTMLTextbox getPasswordTxt() {
        return passwordTxt;
    }

    @Autowired
    public void setPasswordTxt(HTMLTextbox passwordTxt) {
        this.passwordTxt = passwordTxt;
    }

    public HTMLButton getSubmitBtn() {
        return submitBtn;
    }

    @Autowired
    public void setSubmitBtn(HTMLButton submitBtn) {
        this.submitBtn = submitBtn;
    }

    public HTMLButton getForgotPwdBtnLink() {
        return forgotPwdBtnLink;
    }

    @Autowired
    public void setForgotPwdBtnLink(HTMLButton forgotPwdBtnLink) {
        this.forgotPwdBtnLink = forgotPwdBtnLink;
    }

    public HTMLParagraph getErrorMsg() {
        return errorMsg;
    }

    @Autowired
    public void setErrorMsg(HTMLParagraph errorMsg) {
        this.errorMsg = errorMsg;
    }

    protected LoginPage typeUsername(String username) {
        userNameTxt.setValue(username);
        return this;
    }

    protected <T extends AppCommonPageBase> T submit(Class<T> pageClass) {
        if (!isValidForSubmit())
            throw new FormValidationException(page, getClass().getName(), "user name or password is empty. ");
        submitBtn.click();
        return pageAs(pageClass);
    }

    protected LoginPage typePassword(String password) {
        passwordTxt.setValue(password);
        return this;
    }

    protected boolean isValidForSubmit() {
        return !userNameTxt.getValue().equalsIgnoreCase("")
                && !passwordTxt.getValue().equalsIgnoreCase("");
    }

    public <T extends AppCommonPageBase> T loginAs(Class<T> pageClass, String username, String password) {
        page.getDriver().manage().window().maximize();
        typeUsername(username);
        typePassword(password);
        return submit(pageClass);
    }
}
