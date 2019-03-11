package com.oracle.auto.testapp.web.components.homecomps;

import com.oracle.auto.web.comp.ComponentPanelBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

/**
 * Created by grace on 8/23/2017.
 */

@Component
public class Audience extends ComponentPanelBase {

    private HTMLDiv audience;
    private HTMLDiv contacts;


    public void setAudience(HTMLDiv audience) {
        this.audience = audience;
    }

    public HTMLDiv getAudience() {
        return audience;
    }

    public void setContacts(HTMLDiv contacts) {
        this.contacts = contacts;
    }

    public HTMLDiv getContacts() {
        return contacts;
    }

}
