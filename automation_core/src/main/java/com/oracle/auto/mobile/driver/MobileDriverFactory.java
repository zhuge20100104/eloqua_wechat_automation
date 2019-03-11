package com.oracle.auto.mobile.driver;

import com.oracle.auto.web.utility.PropertyConfiger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;
import org.apache.log4j.Logger;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MobileDriverFactory {

    private static Logger log = Logger.getLogger(MobileDriverFactory.class);
    private static PropertyConfiger properties = PropertyConfiger.instance();

    private static class ServersAddressNode {
        final String appium_server;
        final String device_name;
        String host;
        int port;
        boolean used = false;

        ServersAddressNode(String appium_server, String device_name) {
            this.appium_server = appium_server;
            this.device_name = device_name;
        }

        void setHost(String host) { this.host = host; }

        String getHost() { return host; }

        void setPort(int port) { this.port = port; }

        int getPort() { return port; }

        public String getDeviceName() { return device_name; }

        public void setUsed(Boolean isUsed){
            this.used = isUsed;
        }
    }

    public void resetServers(){
        for (ServersAddressNode server : servers)
            server.setUsed(false);
    }

    private List<MobileDriverExCache> mobileDriverExCacheList = new ArrayList<>();

    public void stopExistingAppiumSessions(){
        for (MobileDriverExCache mobileDriverExCache : mobileDriverExCacheList){
            mobileDriverExCache.mobileDriverEx.stop();
            mobileDriverExCache.mobileDriverEx.stopAppiumServer();
        }
    }

    private static class MobileDriverExCache {
        final MobileDriverEx mobileDriverEx;

        MobileDriverExCache(MobileDriverEx mobileDriverEx) {
            this.mobileDriverEx = mobileDriverEx;
        }
    }

    private static class DriverSessionThreadCache {
        private Map<String, MobileDriverExCache> mobileDrivers = new HashMap<>();
        private Map<String, Boolean> resetFlags = new HashMap<>();
        private ServersAddressNode serverAddress = null;
        String deviceName;

        DriverSessionThreadCache(ServersAddressNode serverAddress) { this.serverAddress = serverAddress; }

        MobileDriverExCache getMobileDriver() {
            this.deviceName = serverAddress.device_name;
            return mobileDrivers.get(deviceName);
        }

        MobileDriverExCache getLastMobileDriver() { return mobileDrivers.get(deviceName); }

        void setMobileDriver(MobileDriverExCache mobileDriverExCache) {
            this.deviceName = serverAddress.device_name;
            mobileDrivers.put(deviceName, mobileDriverExCache);
        }

        ServersAddressNode getAddress() { return serverAddress; }

        void setResetFalg(boolean reset) { resetFlags.put(serverAddress.device_name, reset); }

        boolean getResetFlag() { return resetFlags.get(serverAddress.device_name) != null && resetFlags.get(serverAddress.device_name); }
    }

    private List<MobileDriverEx> appiumLog = new ArrayList<>();

    private static final Object object = new Object();
    private static MobileDriverFactory instance = null;

    private MobileDriverFactory() { }

    public static MobileDriverFactory instance() {
        if (instance != null) return instance;
        synchronized (object) {
            if (instance == null)
                instance = new MobileDriverFactory();
        }
        return instance;
    }

    private List<ServersAddressNode> servers = new ArrayList<>();
    private ThreadLocal<DriverSessionThreadCache> threadCache = new ThreadLocal<>();
    private String screenshotFolder;

    public void registerAppiumHub(String appiumNodeIp, int appiumNodePort, String device_name) {
        String url = String.format("http://%s:%d/wd/hub", appiumNodeIp, appiumNodePort);
        ServersAddressNode serverNode = new ServersAddressNode(url, device_name);
        serverNode.setHost(appiumNodeIp);
        serverNode.setPort(appiumNodePort);
        servers.add(serverNode);
    }

    public int appiumHubCount() {  return servers.size(); }

    // stop all appium sessions (note: only call it once and it's not thread safe. only call it after all stories' finished)
    public void stopAllAppiumSessions() {
        for (MobileDriverEx mobileDriverEx : appiumLog) {
            try {
                mobileDriverEx.stop();
            } catch (Exception ex) {
                log.error("try to close appium instance, but failed due to error:", ex);
            }
        }
        appiumLog.clear();
    }

    // reset the app's MobileDriverExCache
    public void resetApp() {
        DriverSessionThreadCache cache = threadCache.get();
        if (cache == null) return;
        MobileDriverExCache mobileDriverExCache = cache.getMobileDriver();
        if (mobileDriverExCache == null) return;

        mobileDriverExCache.mobileDriverEx.stop();
        appiumLog.remove(mobileDriverExCache.mobileDriverEx);  // remove from log list

        cache.setMobileDriver(null);
        cache.setResetFalg(true); // has reset flag, so that next time, when calling the session, if it was reset once, need to reset it again.
    }

    // get last driver session of this thread
    public MobileDriverEx getLastMobileDriverSession() {
        DriverSessionThreadCache cache = threadCache.get();
        if (cache == null) return null;
        return cache.getLastMobileDriver().mobileDriverEx;
    }

    public String getDeviceName(){
        return threadCache.get().getAddress().getDeviceName();
    }

    // launch app, if empty, create it, if reset flag is marked, reset it first.
    public MobileDriverEx launchApp() {
        DriverSessionThreadCache cache = threadCache.get();
        if (cache == null) {
            ServersAddressNode address = getUnusedServerAddress();
            if (address == null)
                throw new RuntimeException("Cannot find any unused appium server.");
            cache = new DriverSessionThreadCache(address);
        }

        // check reset flag
//        if (cache.getResetFlag()) {
//            resetApp();
//            cache.setResetFalg(false);  // recover as unreset
//        }

        MobileDriverExCache mobileDriverExCache = cache.getMobileDriver();

        if (mobileDriverExCache != null) {
            try {
                cache.getMobileDriver().mobileDriverEx.stop();// quit the appium driver
            }catch (Exception e){
                log.debug("Failed to stop the appium." + e.getMessage());
            }
            cache.getMobileDriver().mobileDriverEx.stopAppiumServer();// stop the appium server
        }

        //if (mobileDriverExCache == null) {
        //For each android driver, create a support appium server.
        AppiumDriverLocalService appiumServer = createAppiumServer(cache.getAddress().getHost(), cache.getAddress().getPort());

        AppiumDriver webDriver = createAppiumDriver(cache.getAddress(), cache.getAddress().device_name);
        if (webDriver == null)
            throw new RuntimeException("Fail to create driver instance for appium server = " + cache.getAddress().appium_server + " device name=" + cache.getAddress().device_name);
        MobileDriverEx mobileDriverEx = new MobileDriverEx(webDriver, screenshotFolder);

        // Bind Appium server to the Android driver
        mobileDriverEx.setAppiumServer(appiumServer);

        appiumLog.add(mobileDriverEx); //add to log
        mobileDriverExCache = new MobileDriverExCache(mobileDriverEx);

        mobileDriverExCacheList.add(mobileDriverExCache);

        cache.setMobileDriver(mobileDriverExCache);
        //}

        threadCache.set(cache);
        MobileDriverEx.waitFor(properties.getEnvData("app.launch.timeout", 10));
        return mobileDriverExCache.mobileDriverEx;
    }

    private AppiumDriverLocalService createAppiumServer(String host, int port) {
        AppiumDriverLocalService appiumDriverLocalService = AppiumDriverLocalService.buildService(
                new AppiumServiceBuilder()
                        .usingDriverExecutable(new File(properties.getEnvData("nodePath", "")))
                        .withAppiumJS(new File(properties.getEnvData("appiumJSPath", "")))
                        .withIPAddress(host)
                        .usingPort(port)
                        .withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, String.valueOf(port + 1)));
        appiumDriverLocalService.start();
        return appiumDriverLocalService;
    }

    private AppiumDriver createAppiumDriver(ServersAddressNode address, String deviceName) {
        String appType = properties.getEnvData("mobile.appType", "").toLowerCase();
        log.debug("Start server to get appium instance: " + address.appium_server);
        AppiumDriver dr = null;
        try {
            if (appType.contains("android"))
                dr = new AndroidDriver(new URL(address.appium_server), makeDesiredCapabilities(deviceName, appType));
            else if (appType.contains("ipad") || appType.contains("iphone"))
                dr = new IOSDriver(new URL(address.appium_server), makeDesiredCapabilities(deviceName, appType));

            if (dr != null)
                dr.manage().timeouts().implicitlyWait(properties.getEnvData("implicitly_wait_timeout", 30), TimeUnit.SECONDS);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        }

        return dr;
    }

    // multiple threads safe
    private ServersAddressNode getUnusedServerAddress() {
        log.info("Getting un-used appium node...");
        synchronized (this) {
            for (ServersAddressNode node : servers) {
                if (!node.used) {
                    log.info("Using appium node: " + node.appium_server);
                    node.used = true;
                    return node;
                }
            }
            return null;
        }
    }

    private DesiredCapabilities makeDesiredCapabilities(String deviceName, String appType) {
        DesiredCapabilities desiredCapabilities = null;

        if (appType.contains("android")) {
            desiredCapabilities = DesiredCapabilities.android();
        } else if (appType.contains("iphone")) {
            desiredCapabilities = DesiredCapabilities.iphone();
        } else if (appType.contains("ipad")) {
            desiredCapabilities = DesiredCapabilities.ipad();
        }

        assert desiredCapabilities != null;
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, deviceName);
        desiredCapabilities.setCapability("deviceName", deviceName);
        desiredCapabilities.setCapability("automationName", properties.getEnvData("automationName", ""));
        desiredCapabilities.setCapability("platformName", properties.getEnvData("platformName", ""));
        desiredCapabilities.setCapability("platformVersion", properties.getEnvData("platformVersion", ""));
        desiredCapabilities.setCapability("noReset", properties.getEnvData("noReset", true));
        desiredCapabilities.setCapability("fullReset", properties.getEnvData("fullReset", ""));
        desiredCapabilities.setCapability("sessionOverride", properties.getEnvData("sessionOverride", true));
        desiredCapabilities.setCapability("unicodeKeyboard", properties.getEnvData("unicodeKeyboard", true));
        desiredCapabilities.setCapability("resetKeyboard", properties.getEnvData("resetKeyboard", true));
        desiredCapabilities.setCapability("appPackage", properties.getEnvData("app_package", ""));
        desiredCapabilities.setCapability("appActivity", properties.getEnvData("app_activity", ""));

        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600);
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_READY_TIMEOUT, "10");
        //desiredCapabilities.setCapability(MobileCapabilityType.LAUNCH_TIMEOUT,"1200000");

        if (appType.contains("android")) {
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("androidProcess", properties.getEnvData("androidProcess", ""));
            desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }

        return desiredCapabilities;
    }

    public void setScreeshotLocation(String screenshotFolder) { this.screenshotFolder = screenshotFolder; }

    public String getScreeshotLocation() { return this.screenshotFolder; }
}