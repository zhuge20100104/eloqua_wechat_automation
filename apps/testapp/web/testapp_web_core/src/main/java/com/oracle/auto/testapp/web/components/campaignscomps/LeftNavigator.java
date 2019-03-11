package com.oracle.auto.testapp.web.components.campaignscomps;

import com.oracle.auto.web.comp.ComponentPanelBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class LeftNavigator extends ComponentPanelBase {

    private HTMLInput searchInputBox;
    private HTMLDiv exceptedCampaing;
    private HTMLDiv listedCampaigns;

    public HTMLInput getSearchInputBox() {
        return searchInputBox;
    }

    public void setSearchInputBox(HTMLInput searchInputBox) {
        this.searchInputBox = searchInputBox;
    }

    public HTMLDiv getExceptedCampaing() {
        return exceptedCampaing;
    }

    public void setExceptedCampaing(HTMLDiv exceptedCampaing) {
        this.exceptedCampaing = exceptedCampaing;
    }


    public HTMLDiv getListedCampaigns() {
        return listedCampaigns;
    }

    public void setListedCampaigns(HTMLDiv listedCampaigns) {
        this.listedCampaigns = listedCampaigns;
    }

    public List<String> getCampaignLists() {
        List<String> campaignList = new ArrayList<>();
        int numbers = getListedCampaigns().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            campaignList.add(getExceptedCampaign(i).getText());
        }
        return campaignList;
    }

    public int getCampaignIndex(String value) {
        List<String> campaigns = getCampaignLists();
        for (int i = 0; i < campaigns.size(); i++) {
            if (campaigns.get(i).equals(value))
                return i;
        }
        return -1;
    }

    public HTMLDiv getExceptedCampaign(int index) {
        exceptedCampaing.setLocator(String.format(exceptedCampaing.getRawLocator(), index));
        return exceptedCampaing;
    }


}
