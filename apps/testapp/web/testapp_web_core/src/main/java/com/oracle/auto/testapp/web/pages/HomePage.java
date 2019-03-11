package com.oracle.auto.testapp.web.pages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.testapp.web.components.homecomps.TopNavigator;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class HomePage extends TestAppPageBase {
    private TopNavigator homeTopNavigator;
    private HTMLDiv homeLogo;
    private HTMLDiv cloudAppBtn;
    private HTMLDiv cloudAPPCloseBtn;




    @PostConstruct
    public void init() {
        registerComp(homeTopNavigator);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setHomeTopNavigator(TopNavigator homeTopNavigator) {
        this.homeTopNavigator = homeTopNavigator;
    }

    public TopNavigator getHomeTopNavigator() {
        return this.homeTopNavigator;
    }

    public void setCloudAppBtn(HTMLDiv cloudAppBtn) {
        this.cloudAppBtn = cloudAppBtn;
    }

    public HTMLDiv getCloudAppBtn() {
        return cloudAppBtn;
    }

    public HTMLDiv getCloudAPPCloseBtn() {
        return cloudAPPCloseBtn;
    }

    public void setCloudAPPCloseBtn(HTMLDiv cloudAPPCloseBtn) {
        this.cloudAPPCloseBtn = cloudAPPCloseBtn;
    }

    public void setHomeLogo(HTMLDiv homeLogo) {
        this.homeLogo = homeLogo;
    }

    public HTMLDiv getHomeLogo() {
        return homeLogo;
    }
}
