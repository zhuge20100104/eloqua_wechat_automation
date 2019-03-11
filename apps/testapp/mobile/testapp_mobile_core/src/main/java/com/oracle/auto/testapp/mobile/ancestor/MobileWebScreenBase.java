package com.oracle.auto.testapp.mobile.ancestor;

import com.oracle.auto.mobile.driver.MobileDriverEx;
import com.oracle.auto.mobile.exceptions.MobileExceptionBase;
import com.oracle.auto.mobile.screen.ScreenObjectBase;
import com.oracle.auto.web.utility.PropertyConfiger;

/**
 * Created by stepzhou on 7/25/2017.
 */
public abstract class MobileWebScreenBase extends ScreenObjectBase {

    public MobileWebScreenBase() {
        contextSwitch();
    }

    private void contextSwitch() {
        String context = "WEBVIEW_com.tencent.mm:tools";
        int waitUnit = 0;
        MobileDriverEx.waitFor(3);
        while (!getMobileDriverEx().getDriver().getContextHandles().contains(context)) {
            if (waitUnit > PropertyConfiger.instance().getEnvData("mobile.contexts.switch.wait.timeout", 30))
                throw new MobileExceptionBase(getMobileDriverEx(), "Contexts switch failed: " + getClass().getSimpleName());
            waitUnit++;
            MobileDriverEx.waitFor(1);
        }
        //MobileDriverEx.waitFor(3);
        getMobileDriverEx().getDriver().context(context);
    }
}