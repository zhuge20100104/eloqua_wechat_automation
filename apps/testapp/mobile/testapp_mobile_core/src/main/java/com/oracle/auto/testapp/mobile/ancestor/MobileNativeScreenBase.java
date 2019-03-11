package com.oracle.auto.testapp.mobile.ancestor;

import com.oracle.auto.mobile.screen.ScreenObjectBase;


/**
 * Created by stepzhou on 7/25/2017.
 */
public abstract class MobileNativeScreenBase extends ScreenObjectBase{
    public MobileNativeScreenBase(){
        getMobileDriverEx().getDriver().context(getMobileDriverEx().getDriver().getContextHandles().toArray()[0].toString());
    }
}
