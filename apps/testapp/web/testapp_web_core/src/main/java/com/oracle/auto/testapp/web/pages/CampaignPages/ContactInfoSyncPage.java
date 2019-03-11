package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by grace on 5/23/2018.
 */

@Component
public class ContactInfoSyncPage extends TestAppPageBase {
    private HTMLDiv editBtn = new HTMLDiv("BDD_jQuery(\".content\")[0].jFind(BDD_jQuery(\".sc-view\"))[0].jFind(BDD_jQuery(\".icon\"))[0]");


    public HTMLDiv getEditBtn() {
        editBtn.setPage(page());
        return editBtn;
    }

    @PostConstruct
    public void init() {
        registerComp(editBtn);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

}
