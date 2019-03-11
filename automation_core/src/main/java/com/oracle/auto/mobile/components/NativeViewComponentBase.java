package com.oracle.auto.mobile.components;

public class NativeViewComponentBase extends AppiumComponentBase {

    public NativeViewComponentBase( String locator) {
        super( locator);
    }

    public NativeViewComponentBase( String locator, int element_index) {
        super( locator, element_index);
    }

    @Override
    public void setContext() {
        super.getMobileDriverEx().setNativeContext();
    }
}
