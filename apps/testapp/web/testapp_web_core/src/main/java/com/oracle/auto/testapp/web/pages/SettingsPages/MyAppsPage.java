package com.oracle.auto.testapp.web.pages.SettingsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by grcao on 7/3/2017.
 */

@Component
public class MyAppsPage extends TestAppPageBase {
    private HTMLDiv configLink;

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setConfigLink (HTMLDiv configLink){
        this.configLink = configLink;
    }

    public HTMLDiv getConfigLink() {
        return configLink;
    }

}
