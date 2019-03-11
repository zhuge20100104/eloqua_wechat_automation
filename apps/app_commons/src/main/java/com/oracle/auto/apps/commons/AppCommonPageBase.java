package com.oracle.auto.apps.commons;

import com.google.gson.annotations.Expose;
import com.oracle.auto.web.pages.object.PageObjectBase;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

abstract public class AppCommonPageBase<E extends AppCommonPageBase<?>> extends PageObjectBase<E> {

    private static Logger log = Logger.getLogger(AppCommonPageBase.class);

    protected List<String> lastClosedErrorMessages = new ArrayList<String>();

    @Expose
    protected boolean ignoreError = false;

    protected AppCommonPageBase() {
        this(false);
    }

    protected AppCommonPageBase(boolean ignoreError) {
        super();
        super.setDefaultPageObject(AppCommonPageAdaptor.class);
        this.ignoreError = PropertyConfiger.instance().getEnvData("hotfix." + getClass().getSimpleName() + ".ignore.error.when.enter.page", ignoreError);
    }

}