package com.oracle.auto.testapp.web.components.homecomps;

import com.oracle.auto.web.comp.ComponentPanelBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class Orchestration extends ComponentPanelBase {
    private HTMLDiv orchestration;
    private HTMLDiv campaigns;
    private HTMLDiv programs;
    private HTMLDiv tools;


    public void setOrchestration(HTMLDiv orchestration) {
        this.orchestration = orchestration;
    }

    public HTMLDiv getOrchestration() {
        return orchestration;
    }

    public void setCampaigns(HTMLDiv campaigns) {
        this.campaigns = campaigns;
    }

    public HTMLDiv getCampaigns() {
        return campaigns;
    }

}
