package com.oracle.auto.testapp.web.components.homecomps;

import com.oracle.auto.web.comp.ComponentPanelBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

/**
 * Created by grace on 8/23/2017.
 */

@Component
public class Asset extends ComponentPanelBase {
    private HTMLDiv assets;
    private HTMLDiv emails;
    private HTMLDiv landingpages;

    public void setAssets(HTMLDiv assets) {
        this.assets = assets;
    }

    public HTMLDiv getAssets() {
        return assets;
    }

    public void setEmails(HTMLDiv emails) {
        this.emails = emails;
    }

    public HTMLDiv getEmails() {
        return emails;
    }

    public void setLandingpages(HTMLDiv landingpages) {
        this.landingpages = landingpages;
    }

    public HTMLDiv getLandingpages() {
        return landingpages;
    }
}
