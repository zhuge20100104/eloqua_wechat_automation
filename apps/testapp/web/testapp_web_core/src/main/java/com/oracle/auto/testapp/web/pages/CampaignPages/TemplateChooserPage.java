package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.testapp.web.components.campaignscomps.LeftNavigator;
import com.oracle.auto.testapp.web.components.campaignscomps.RecentAccessed;
import com.oracle.auto.testapp.web.components.campaignscomps.TopOptions;
import com.oracle.auto.testapp.web.components.campaignscomps.UsefulLinks;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class TemplateChooserPage extends TestAppPageBase {
    private HTMLDiv blankCampaign;
    private HTMLDiv choose;

    public void setChoose(HTMLDiv choose) {
        this.choose = choose;
    }

    public HTMLDiv getChoose() {
        return choose;
    }

    public void setBlankCampaign(HTMLDiv blankCampaign) {
        this.blankCampaign = blankCampaign;
    }

    public HTMLDiv getBlankCampaign() {
        return blankCampaign;
    }

    @PostConstruct
    public void init() {
        registerComp(blankCampaign);
        injectPageToChildComponents(this);
        waitForPageReady();
    }
}