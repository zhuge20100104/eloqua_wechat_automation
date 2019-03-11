package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.testapp.web.components.campaignscomps.LeftNavigator;
import com.oracle.auto.testapp.web.components.campaignscomps.RecentAccessed;
import com.oracle.auto.testapp.web.components.campaignscomps.TopOptions;
import com.oracle.auto.testapp.web.components.campaignscomps.UsefulLinks;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class CampaignEditorPage extends TestAppPageBase {
    private HTMLDiv listedCampaignItems;
    private HTMLDiv exceptedCampaignItem;

    private HTMLDiv output;
    private HTMLDiv input;

    private HTMLDiv listedDrawerItems;
    private HTMLDiv exceptedDrawerItem;
//    private HTMLDiv editorSegMember;
//    private HTMLDiv editorMesSender;

    private HTMLDiv rightMenu;

    private HTMLDiv saveBtn;
    private HTMLDiv activateBtn;

    private HTMLDiv scrollComp;


    public void setListedCampaignItems(HTMLDiv listedCampaignItems) {
        this.listedCampaignItems = listedCampaignItems;
    }

    public HTMLDiv getListedCampaignItems() {
        return listedCampaignItems;
    }

    public void setExceptedCampaignItem(HTMLDiv exceptedCampaignItem) {
        this.exceptedCampaignItem = exceptedCampaignItem;
    }

    public HTMLDiv getExceptedCampaignItem() {
        return exceptedCampaignItem;
    }


    public int getObjectIndex(String value) {
        List<String> items = getListsObjectItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public List<String> getListsObjectItems() {
        List<String> itemList = new ArrayList<>();
        int numbers = getListedCampaignItems().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            itemList.add(getExceptedObject(i).getText());
        }
        return itemList;
    }

    public HTMLDiv getExceptedObject(int index) {
        exceptedCampaignItem.setLocator(String.format(exceptedCampaignItem.getRawLocator(), index));
        return exceptedCampaignItem;
    }

    public void setActivateBtn(HTMLDiv activateBtn) {
        this.activateBtn = activateBtn;
    }

    public HTMLDiv getActivateBtn() {
        return activateBtn;
    }

    public void setSaveBtn(HTMLDiv saveBtn) {
        this.saveBtn = saveBtn;
    }

    public HTMLDiv getSaveBtn() {
        return saveBtn;
    }

    public void setRightMenu(HTMLDiv rightMenu) {
        this.rightMenu = rightMenu;
    }

    public HTMLDiv getRightMenu() {
        return rightMenu;
    }
//
//    public void setEditorMesSender(HTMLDiv editorMesSender) {
//        this.editorMesSender = editorMesSender;
//    }
//
//    public HTMLDiv getEditorMesSender() {
//        return editorMesSender;
//    }
//
//    public void setEditorSegMember(HTMLDiv editorSegMember) {
//        this.editorSegMember = editorSegMember;
//    }
//
//    public HTMLDiv getEditorSegMember() {
//        return this.editorSegMember;
//    }

    public void setListedDrawerItems(HTMLDiv listedDrawerItems) {
        this.listedDrawerItems = listedDrawerItems;
    }

    public HTMLDiv getListedDrawerItems() {
        return this.listedDrawerItems;
    }

    public void setExceptedDrawerItem(HTMLDiv exceptedDrawerItem) {
        this.exceptedDrawerItem = exceptedDrawerItem;
    }

    public HTMLDiv getExceptedDrawerItem() {
        return this.exceptedDrawerItem;
    }

//    public void dragAndDropSegment(int index) {
//        getExceptedDrawerItem().dragAndDropTo(getExceptedDrawerItem(index), 680, 250);
//        page().waitForShort();
//    }
//
//    public void dragAndDropWeChatSender(int index) {
//        getExceptedDrawerItem().dragAndDropTo(getExceptedDrawerItem(index), 720, 450);
//        page().waitForShort();
//    }

    public void dragAndDropItems(int index, int xOfferSet, int yOfferSet) {
        getExceptedDrawerItem().dragAndDropTo(getExceptedDrawerItem(index), xOfferSet, yOfferSet);
        page().waitForShort();
    }

    public int getDrawerItemIndex(String value) {
        List<String> items = getListsDrawerItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public List<String> getListsDrawerItems() {
        List<String> itemList = new ArrayList<>();
        int numbers = getListedDrawerItems().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            //  itemList.add(getExceptedDrawerItem(i).getText());
            itemList.add(getExceptedDrawerItem(i).getAttribute("data-name"));
        }
        return itemList;
    }

    public HTMLDiv getExceptedDrawerItem(int index) {
        exceptedDrawerItem.setLocator(String.format(exceptedDrawerItem.getRawLocator(), index));
        return exceptedDrawerItem;
    }


    public void setOutput(HTMLDiv output) {
        this.output = output;
    }

    public HTMLDiv getOutput() {
        return output;
    }

    public void setInput(HTMLDiv input) {
        this.input = input;
    }

    public HTMLDiv getInput() {
        return input;
    }


    public void setScrollComp(HTMLDiv scrollComp) {
        this.scrollComp = scrollComp;
    }

    private HTMLDiv getScrollComp() {
        return scrollComp;
    }

    public void connSegMemMesSender() {
        getOutput().dragDrop(output, input);
        page().waitForShort();
    }

    public void scrollToBottom() {
        String locator = getScrollComp().getLocator();
        String scrollCondition = "var comp=" + locator + ";function isScroll(e){return e.scrollTop<(e.scrollHeight-e.clientHeight)};return isScroll(comp)";
        String scrollTopScript = "var comp=" + locator + ";function scroll_to(e){while(e.scrollTop<(e.scrollHeight-e.clientHeight)){e.scrollTop+=1000;}};scroll_to(comp) ";
        while (page().executeScriptEx(scrollCondition).AsBool()) {
            page().executeScriptEx(scrollTopScript);
            page().waitForShort();
        }
    }

    @PostConstruct
    public void init() {
//        registerComp(segmentMember);
//        registerComp(messageSender);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

}