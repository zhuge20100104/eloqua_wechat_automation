package com.oracle.auto.testapp.web.pages.SettingsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 7/3/2017.
 */

@Component
public class AppCloudCatalogPage extends TestAppPageBase {
    private HTMLInput searchInputText;
    private HTMLDiv searchInputBox;
    private HTMLDiv selectedApps;

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setSearchInputText(HTMLInput searchInputText) {
        this.searchInputText = searchInputText;
    }

    public HTMLInput getSearchInputText() {
        return searchInputText;
    }

    public void setSearchInputBox(HTMLDiv searchInputBox) {
        this.searchInputBox = searchInputBox;
    }

    public HTMLDiv getSearchInputBox() {
        return searchInputBox;
    }

    public void setSelectedApps(HTMLDiv selectedApps) {
        this.selectedApps = selectedApps;
    }

    public HTMLDiv getSelectedApps() {
        return selectedApps;
    }

    public void waitForSecond(int second){
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
