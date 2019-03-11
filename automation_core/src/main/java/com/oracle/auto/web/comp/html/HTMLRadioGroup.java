package com.oracle.auto.web.comp.html;

import com.oracle.auto.web.comp.interfaces.IClickable;
import com.oracle.auto.web.exceptions.ComponentSetPropertyException;
import org.springframework.stereotype.Component;

@Component
public class HTMLRadioGroup extends HTMLComponentBase implements IClickable {

    public HTMLRadioGroup(String locator) {
        super(locator);
    }

    // value
    public String getValue() {
        return getMethodProp(".filter(':checked').val()").str();
    }

    public String getFirstValue() {
        return getMethodProp(".value").str();
    }

    public String getValueAtIndex(int index) {
        return getMethodProp(String.format(".eq(%d).val()", index)).str();
    }


    public void setValue(String value) {
        setNoReturnMethodProp(String.format(".val(['%s'])", value));
        if (!getValue().equals(value))
            throw new ComponentSetPropertyException(page, locator, "set value = " + value, "the radio box doesn't have the check box with this value");
    }

    @Override
    public void click() {
        waitForReady();
        doClick();
    }

    public boolean isRadioBtnSelected() {
        waitForReady();
        return page.isSelected(getIdLocator());
    }
}
