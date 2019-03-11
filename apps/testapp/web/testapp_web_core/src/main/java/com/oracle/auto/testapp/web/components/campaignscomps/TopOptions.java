package com.oracle.auto.testapp.web.components.campaignscomps;

import com.oracle.auto.web.comp.ComponentPanelBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class TopOptions extends ComponentPanelBase {
    private HTMLDiv createMSCampaign;
    private HTMLDiv createSECampaign;
    private HTMLDiv uploadExtActivities;
    private HTMLDiv reportDashboards;

    public void setCreateMSCampaign(HTMLDiv createMSCampaign) {
        this.createMSCampaign = createMSCampaign;
    }

    public HTMLDiv getCreateMSCampaign() {
        return createMSCampaign;
    }
}
