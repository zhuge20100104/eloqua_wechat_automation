package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class ActivateCampaignPage extends TestAppPageBase {
    private HTMLDiv activateBtn;
    private HTMLDiv activateConfirm;

    private HTMLDiv refreshBtn;
    private HTMLDiv countFlag;
    private HTMLDiv contentsEnteredFlag;


    public void setActivateConfirm(HTMLDiv activateConfirm) {
        this.activateConfirm = activateConfirm;
    }

    public HTMLDiv getActivateConfirm() {
        return activateConfirm;
    }

    public void setActivateBtn(HTMLDiv activateBtn) {
        this.activateBtn = activateBtn;
    }

    public HTMLDiv getActivateBtn() {
        return activateBtn;
    }

    public void setCountFlag(HTMLDiv countFlag) {
        this.countFlag = countFlag;
    }

    public HTMLDiv getCountFlag() {
        return countFlag;
    }

    public void setContentsEnteredFlag(HTMLDiv contentsEnteredFlag) {
        this.contentsEnteredFlag = contentsEnteredFlag;
    }

    public HTMLDiv getContentsEnteredFlag() {
        return contentsEnteredFlag;
    }

    public void setRefreshBtn(HTMLDiv refreshBtn) {
        this.refreshBtn = refreshBtn;
    }

    public HTMLDiv getRefreshBtn() {
        return refreshBtn;
    }


    @PostConstruct
    public void init() {
        //registerComp(topOptions);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

}
