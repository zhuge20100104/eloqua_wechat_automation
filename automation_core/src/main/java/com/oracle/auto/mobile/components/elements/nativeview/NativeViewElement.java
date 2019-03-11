package com.oracle.auto.mobile.components.elements.nativeview;

import com.oracle.auto.mobile.components.NativeViewComponentBase;

public class NativeViewElement extends NativeViewComponentBase {

    public NativeViewElement(String locator) {
        super(locator);
    }

    public NativeViewElement(String locator, int element_index) {
        super(locator, element_index);
    }

    public void moveTo(int x, int y) {
        super.moveTo(x, y);
    }


}
