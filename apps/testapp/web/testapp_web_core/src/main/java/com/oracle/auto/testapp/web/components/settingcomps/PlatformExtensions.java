package com.oracle.auto.testapp.web.components.settingcomps;

import com.oracle.auto.web.comp.ComponentPanelBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

/**
 * Created by stepzhou on 7/3/2017.
 */

@Component
public class PlatformExtensions extends ComponentPanelBase {
private HTMLDiv apps;

public void setApps(HTMLDiv apps){
    this.apps = apps;
}

public HTMLDiv getApps(){
    return apps;
}





}
