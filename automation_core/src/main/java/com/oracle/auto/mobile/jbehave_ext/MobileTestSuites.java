package com.oracle.auto.mobile.jbehave_ext;

import com.oracle.auto.mobile.driver.MobileDriverFactory;
import com.oracle.auto.mobile.exceptions.MobileExceptionBase;
import com.oracle.auto.web.jbehave_ext.NormalTestSuites;
import com.oracle.auto.web.jbehave_ext.WebConfiguration;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.ResourceLoaderCross;
import junit.framework.Assert;
import org.apache.log4j.Logger;

import java.util.List;

public class MobileTestSuites extends NormalTestSuites {

    private static Logger log = Logger.getLogger(MobileTestSuites.class);

    @Override
    protected void InitConfig() {
        // register resource loader cross
        ResourceLoaderCross.instance().registerModuleChains(getClass(), MobileTestSuites.class);

        // load setting at the first time
        PropertyConfiger.instance().setClassLoader(getClass());
        PropertyConfiger.instance().loadProperties(settingFilePaths());

        // load data
        PropertyConfiger.instance().loadProperties(dataFilePaths());
        addCustomValues();

        // load snapshot hanlder
        MobileExceptionBase.setTakenSnapshotHandler(this.takenSnapshotHanlder());

        // devices list (seperated with ';')
        String deviceConfigurations = PropertyConfiger.instance().getEnvData("mobile.device.configurations", "");
        Assert.assertFalse("**error** no devices configured", deviceConfigurations.isEmpty());

        // selenium instance count on per node
        registerAppiumNodesWithLoadSharing(deviceConfigurations);

        // dry run or not, default - false
        WebConfiguration.instance().doDryRun(PropertyConfiger.instance().getEnvData("drill.run", false));

    }

    @Override
    protected void addAddtionalStep(List<Object> objList) {
        objList.add(new AfterStoriesAppiumDriverSteps());
    }

    @Override
    protected int threadCount() {
        // use selenium nodes as thread count by default.
        int thread_count = MobileDriverFactory.instance().appiumHubCount();
        Assert.assertTrue(String.format("**error** thread count [] is larger than selenium nodes [].", thread_count, MobileDriverFactory.instance()), thread_count >= 1 && thread_count <= MobileDriverFactory.instance().appiumHubCount());

        // set thread count into system property
        System.setProperty("bdd.thread.count", String.valueOf(thread_count));
        return thread_count;
    }

    private void registerAppiumNodesWithLoadSharing(String deviceConfigurations) {
        String[] configurations = deviceConfigurations.split(";");

        for (String configuration : configurations) {
            if (configuration.trim().isEmpty()) continue;

            String[] configData = configuration.split(":");

            if (configData[0].trim().isEmpty() || configData[1].trim().isEmpty() || configData[2].trim().isEmpty())
                continue;
            MobileDriverFactory.instance().registerAppiumHub(configData[0], Integer.parseInt(configData[1]), configData[2]);
            log.info("Registered Appium Node with: \n" +
                    "Node: " + configData[0] + "\n" +
                    "Device: " + configData[2] + "\n" +
                    "Port: " + Integer.parseInt(configData[1]) + "\n"
            );
        }
    }

}