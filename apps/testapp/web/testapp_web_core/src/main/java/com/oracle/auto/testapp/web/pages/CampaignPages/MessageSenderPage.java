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
public class MessageSenderPage extends TestAppPageBase {
    private HTMLDiv editBtn;

    public void setEditBtn(HTMLDiv editBtn) {
        this.editBtn = editBtn;
    }

    public HTMLDiv getEditBtn() {
        return editBtn;
    }

    @PostConstruct
    public void init() {
        registerComp(editBtn);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

}
