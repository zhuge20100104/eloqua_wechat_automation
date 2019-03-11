package com.oracle.auto.testapp.web.pages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLButton;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by grace on 2018/5/30
 */

@Component
public class WeChatPlatformHomePage extends TestAppPageBase {

    HTMLInput account = new HTMLInput("xpath=//input[contains(@name, \"account\")]\n");
    HTMLInput password= new HTMLInput("//input[contains(@name, \"password\")]");
    HTMLDiv loginBtn = new HTMLDiv("xpath=//a[@class='btn_login']");

    @PostConstruct
    public void init() {
        registerComp(account);
        injectPageToChildComponents(this);

        page.deleteAllVisibleCookies();
      //  String loginUrl = page.serverDefaultURL();
       page.open("https://mp.weixin.qq.com/");

//        String loginUrl = page.serverDefaultURL();
//        page.open(loginUrl);

        waitForPageReady();
    }

    public HTMLInput getAccount() {
        account.setPage(page());
        return account;
    }

    public HTMLInput getPassword() {
        password.setPage(page());
        return password;
    }

    public HTMLDiv getLoginBtn() {
        loginBtn.setPage(page());
        return loginBtn;
    }


}
