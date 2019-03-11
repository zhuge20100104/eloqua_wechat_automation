/**
 *
 */
package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IClickableComponent;
import org.springframework.stereotype.Component;

@Component
public class HTMLImg extends HTMLComponentBase implements IClickableComponent {

    public HTMLImg(String locator) {
        super(locator);
    }

    public HTMLImg() {
    }

    @Override
    public void click() {
        waitForReady();
        doClick();
    }

    public String getImageSrc() {
        waitForReady();
        return doGetAttribute("src");
    }
}
