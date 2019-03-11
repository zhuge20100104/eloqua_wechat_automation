package com.oracle.auto.testapp.web.pages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLButton;
import com.oracle.auto.web.comp.html.HTMLInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by stepzhou on 6/30/2017.
 */
@Component
public class LoginPage extends TestAppPageBase {
    private HTMLInput companyName;
    private HTMLInput userName;
    private HTMLInput password;

    private HTMLButton loginBtn;

    @PostConstruct
    public void init() {
        registerComp(companyName);
        registerComp(userName);
        registerComp(password);
        injectPageToChildComponents(this);

        //page.deleteAllVisibleCookies();
        String loginUrl = page.serverDefaultURL();
        page.open(loginUrl);

        waitForPageReady();
    }

    public void setLoginBtn(HTMLButton loginBtn){
        this.loginBtn = loginBtn;
    }

    public HTMLButton getLoginBtn(){
        return loginBtn;
    }

    public void setCompanyName(HTMLInput companyName) {
        this.companyName = companyName;
    }

    public HTMLInput getCompanyName() {
        return this.companyName;
    }

    public void setUserName(HTMLInput userName) {
        this.userName = userName;
    }

    public HTMLInput getUserName() {
        return this.userName;
    }

    public void setPassword(HTMLInput password) {
        this.password = password;
    }

    public HTMLInput getPassword() {
        return this.password;
    }
}