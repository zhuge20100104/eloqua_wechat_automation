package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class SegConfigurePage extends TestAppPageBase {
    private HTMLDiv selectSegment;
    private HTMLDiv selectedItem;
    private HTMLInput selectSegmentInput;
    private HTMLDiv listedSegmentItems;
    private HTMLDiv exceptedSegment;


    private int getListedSegmentNumbers() {
        return getListedSegmentItems().getMethodProp(".size()").AsInt();
    }

    public HTMLDiv getExceptedSegment(int index) {
        exceptedSegment.setLocator(String.format(exceptedSegment.getRawLocator(), index));
        return exceptedSegment;
    }


    public List<String> getListedSegmentLists() {
        List<String> accountList = new ArrayList<>();
        int numbers = getListedSegmentNumbers();
        for (int i = 0; i < numbers; i++) {
            accountList.add(getExceptedSegment(i).getText());
        }
        return accountList;
    }

    public int getSegmentIndex(String SegmentName) {
        List<String> accounts = getListedSegmentLists();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).contentEquals(SegmentName))
                    //contains(SegmentName))
                return i;
        }
        return -1;
    }

    public void waitForSomeTime(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void setExceptedSegment(HTMLDiv exceptedSegment) {
        this.exceptedSegment = exceptedSegment;
    }

    public HTMLDiv getExceptedSegment() {
        return exceptedSegment;
    }

    public void setListedSegmentItems(HTMLDiv listedSegmentItems) {
        this.listedSegmentItems = listedSegmentItems;
    }

    public HTMLDiv getListedSegmentItems() {
        return listedSegmentItems;
    }

    public void setSelectSegmentInput(HTMLInput selectSegmentInput) {
        this.selectSegmentInput = selectSegmentInput;

    }

    public HTMLInput getSelectSegmentInput() {
        return selectSegmentInput;
    }


    public void setSelectSegment(HTMLDiv selectSegment) {
        this.selectSegment = selectSegment;
    }

    public HTMLDiv getSelectSegment() {
        return selectSegment;
    }

    public void setSelectedItem(HTMLDiv selectedItem) {
        this.selectedItem = selectedItem;
    }

    public HTMLDiv getSelectedItem() {
        return selectedItem;
    }

    @PostConstruct
    public void init() {
        registerComp(selectSegment);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

}
