package com.oracle.auto.testapp.web.components.homecomps;

import com.oracle.auto.web.comp.ComponentPanelBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.junit.Assert;
import org.springframework.stereotype.Component;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class TopNavigator extends ComponentPanelBase {
    private HTMLDiv home;
    private Orchestration orchestrationMenu;

    //Assets Menu
    private Asset assetMenu;

    private Audience audienceMenu;
    private HTMLDiv analytice;
    private HTMLDiv settings;

    public TopNavigator(String locator) {
        setLocator(locator);
    }

    public void setAssetMenu(Asset assetMenu) {
        this.assetMenu = assetMenu;
    }

    public Asset getAssetMenu() {
        return assetMenu;
    }

    public void setAudienceMenu(Audience audienceMenu) {
        this.audienceMenu = audienceMenu;
    }

    public Audience getAudienceMenu() {
        return audienceMenu;
    }

    public void setSettings(HTMLDiv settings) {
        this.settings = settings;
    }

    public HTMLDiv getSettings() {
        return settings;
    }

    public void setOrchestrationMenu(Orchestration orchestration) {
        this.orchestrationMenu = orchestration;
    }

    public Orchestration getOrchestrationMenu() {
        return this.orchestrationMenu;
    }


}
