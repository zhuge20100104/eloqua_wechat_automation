package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.testapp.web.components.campaignscomps.LeftNavigator;
import com.oracle.auto.testapp.web.components.campaignscomps.RecentAccessed;
import com.oracle.auto.testapp.web.components.campaignscomps.TopOptions;
import com.oracle.auto.testapp.web.components.campaignscomps.UsefulLinks;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class CampaignsPage extends TestAppPageBase {
    private LeftNavigator leftNavigator;
    private TopOptions topOptions;
    private RecentAccessed recentAccessed;
    private UsefulLinks usefulLinks;

    @PostConstruct
    public void init() {
        registerComp(topOptions);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setTopOptions(TopOptions topOptions) {
        this.topOptions = topOptions;
    }

    public TopOptions getTopOptions() {
        return topOptions;
    }

    public LeftNavigator getLeftNavigator() {
        return leftNavigator;
    }

    public void setLeftNavigator(LeftNavigator leftNavigator) {
        this.leftNavigator = leftNavigator;
    }
}
