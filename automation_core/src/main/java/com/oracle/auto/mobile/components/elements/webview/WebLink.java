package com.oracle.auto.mobile.components.elements.webview;

import com.oracle.auto.mobile.components.WebViewComponentBase;
import org.springframework.stereotype.Component;

@Component
public class WebLink extends WebViewComponentBase {

    public WebLink(String locator) {
        super(locator);
    }

    public void click(){
        waitForReady();
        super.click();
    }

    public void singleFingerTap(){
        waitForReady();
        super.singleFingerTap();
    }

}
