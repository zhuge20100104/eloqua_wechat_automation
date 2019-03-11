package com.oracle.auto.testapp.web.ancestor;

import com.oracle.auto.apps.commons.AppCommonPageAdaptor;
import com.oracle.auto.apps.commons.AppCommonPageBase;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public abstract class TestAppPageBase<T extends TestAppPageBase<?>> extends AppCommonPageBase<T> {
    protected TestAppPageBase() {
        this(false);
    }

    public TestAppPageBase(boolean ignoreError) {
        super();
        super.setDefaultPageObject(AppCommonPageAdaptor.class);
    }

    @PostConstruct
    public void init() {
        //registerComp(globalNavBar);
        injectPageToChildComponents(this);
        waitForPageReady();
    }
}
