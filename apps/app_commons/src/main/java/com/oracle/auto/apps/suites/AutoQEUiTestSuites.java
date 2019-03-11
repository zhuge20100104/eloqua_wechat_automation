package com.oracle.auto.apps.suites;

import com.oracle.auto.web.jbehave_ext.NormalTestSuites;
import com.oracle.auto.web.utility.PropertyConfiger;

public class AutoQEUiTestSuites extends NormalTestSuites {

    @Override
    protected void InitConfig() {
        super.InitConfig();
        //RestAssuredServiceFactory.getInstance().registerClients((PropertyConfiger.instance().getEnvData("selenium.instance.count.per.node", 1)));
    }
}
