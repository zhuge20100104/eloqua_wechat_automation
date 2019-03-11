package com.oracle.auto.web.pages.object;


import com.oracle.auto.web.comp.html.HTMLComponentBase;
import com.oracle.auto.web.comp.html.HTMLDropDownList;
import com.oracle.auto.web.comp.html.HTMLTextbox;

public class ReadOnlyContainer extends HTMLComponentBase {

    private String TEXT_FIELDS = ".jFind(BDD_jFindByAttr('input','type','text'))";
    private String DROP_DOWNS = ".jFind(BDD_jFindBySelector('select'))";


    public ReadOnlyContainer(String locator) {
        super(locator);
    }

    /**
     * check if there any visible text box input
     *
     * @return true if no HTML input displayed
     */
    public boolean hasVisibleTextBoxs() {
        String textFieldSelector = locator + TEXT_FIELDS + "[%s]";
        int count = getMethodProp(TEXT_FIELDS+".size()").AsInt();
        for (int i = 0; i < count; i++) {
            if (new HTMLTextbox(String.format(textFieldSelector, i)).isVisible())
                return true;
        }
        return false;
    }

    /**
     * check if there is any drop down selection
     *
     * @return true is no HTML selection displayed
     */
    public boolean hasVisibleDropDowns() {
        String textFieldSelector = locator + DROP_DOWNS + "[%s]";
        int count = getMethodProp(DROP_DOWNS+".size()").AsInt();
        for (int i = 0; i < count; i++) {
            if (new HTMLDropDownList(String.format(textFieldSelector, i)).isVisible())
                return true;
        }
        return false;
    }

}
