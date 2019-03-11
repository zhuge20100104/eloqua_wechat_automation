package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class CampaignChooserPage extends TestAppPageBase {
    private HTMLInput campaignName;

    private HTMLDiv saveBtn;
    private HTMLDiv errorMsgBox;

    public void setSaveBtn(HTMLDiv saveBtn) {
        this.saveBtn = saveBtn;
    }

    public HTMLDiv getSaveBtn() {
        return saveBtn;
    }

    public void setCampaignName(HTMLInput campaignName) {
        this.campaignName = campaignName;
    }

    public HTMLInput getCampaignName() {
        return campaignName;
    }

    public HTMLDiv getErrorMsgBox() {
        return errorMsgBox;
    }

    public void setErrorMsgBox(HTMLDiv errorMsgBox) {
        this.errorMsgBox = errorMsgBox;
    }


    @PostConstruct
    public void init() {
        //registerComp(topOptions);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

}
