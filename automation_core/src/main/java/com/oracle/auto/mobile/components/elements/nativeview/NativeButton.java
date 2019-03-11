package com.oracle.auto.mobile.components.elements.nativeview;


import com.oracle.auto.mobile.components.NativeViewComponentBase;

public class NativeButton extends NativeViewComponentBase {

    public NativeButton(String locator) {
        super( locator);
    }

    public NativeButton(String locator, int element_index) {
        super( locator, element_index);
    }

    public void singleFingerTap(){
        super.singleFingerTap();
    }

    public void doubleFingerTap(){
        super.doubleFingerTap();
    }
}
