package com.oracle.auto.testapp.web.pages.SettingsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.testapp.web.components.settingcomps.PlatformExtensions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by grcao on 7/3/2017.
 */

@Component
public class SettingsPage extends TestAppPageBase {
    private PlatformExtensions platformExtensions;

    @PostConstruct
    public void init() {

        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setPlatformExtensions(PlatformExtensions platformExtensions) {
        this.platformExtensions = platformExtensions;
    }

    public PlatformExtensions getPlatformExtensions() {
        return platformExtensions;
    }


}
