package com.oracle.auto.testapp.web.pages.SettingsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by grcao on 7/21/2017.
 */

@Component
public class DeleteOfficialAccountPage extends TestAppPageBase {
    private HTMLDiv deleteBtn;
    private HTMLDiv alertWarning;

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setDeleteBtn(HTMLDiv deleteBtn) {
        this.deleteBtn = deleteBtn;
    }

    public HTMLDiv getDeleteBtn() {
        return deleteBtn;
    }

    public void setAlertWarning(HTMLDiv alertWarning){this.alertWarning = alertWarning;}

    public HTMLDiv getAlertWarning(){return alertWarning;}

    public boolean warningMsgIsVisibleOrNot(){
        int index = 60;
        boolean status = false;
        for (int i = 0; i < index; i++) {
            if (getAlertWarning().isReady()) {
                status = true;
                break;
            }
        }
        return status;
    }
}
