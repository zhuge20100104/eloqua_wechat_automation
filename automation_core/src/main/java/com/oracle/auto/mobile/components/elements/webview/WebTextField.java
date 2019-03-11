package com.oracle.auto.mobile.components.elements.webview;

import com.oracle.auto.mobile.components.WebViewComponentBase;

public class WebTextField extends WebViewComponentBase {

    public WebTextField( String locator) {
        super(locator);
    }

    public void setValue(String value){
//        setContext();
//        waitForReady();
        super.setValue(value);
    }

    public String getValue(){
//        setContext();
//        waitForReady();
        return super.getValue();
    }


}
