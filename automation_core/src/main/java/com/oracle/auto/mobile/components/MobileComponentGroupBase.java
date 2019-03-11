package com.oracle.auto.mobile.components;


import com.oracle.auto.mobile.components.interfaces.IMobileComponent;
import com.oracle.auto.mobile.components.interfaces.IMobileComponentGroup;

import java.util.SortedMap;
import java.util.TreeMap;

public class MobileComponentGroupBase extends MobileComponentBase implements IMobileComponentGroup {

    private SortedMap<String, IMobileComponent> compList = new TreeMap<>();
    private int nameStart = 10000;

    public MobileComponentGroupBase() { super(); }

    @Override
    public void registerComp(IMobileComponent... comps) {
        for (IMobileComponent comp : comps)
            compList.put("Component " + (nameStart++), comp);
    }

    @Override
    public boolean isVisible() {
        for (IMobileComponent comp : compList.values()) {
            if (!super.isVisible()) {
                doSetLastError(comp.getLastError());
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isPresent() {
        for (IMobileComponent comp : compList.values()) {
            if (!super.isPresent()) {
                doSetLastError(comp.getLastError());
                return false;
            }
        }
        return true;
    }

    protected String setRelativeLocator(String locator){ return getLocator()+locator; }
}
