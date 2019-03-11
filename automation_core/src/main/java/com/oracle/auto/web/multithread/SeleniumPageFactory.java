package com.oracle.auto.web.multithread;

import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@SuppressWarnings("deprecation")
public class SeleniumPageFactory {
    private static Logger log = Logger.getLogger(SeleniumPageFactory.class);

    // private classes
    private static class ServersAddressNode {
        final String selenium_server;
        final String web_server;
        boolean used = false;

        ServersAddressNode(String selenium_server, String web_server) {
            this.selenium_server = selenium_server;
            this.web_server = web_server;
        }
    }

    private static class WebPageSeleniumCache {
        public final WebDriverSeleniumPageEx page;

        WebPageSeleniumCache(WebDriverSeleniumPageEx page) {
            this.page = page;
        }
    }

    private static class WebPageThreadCache {
        private Map<String, WebPageSeleniumCache> pageSeleniums = new HashMap<>();
        private Map<String, Boolean> resetFlags = new HashMap<>();
        private ServersAddressNode serverAddress = null;
        String lastBrowser = "";

        WebPageThreadCache(ServersAddressNode serverAddress) {
            this.serverAddress = serverAddress;
        }

        WebPageSeleniumCache getPageSelenium(String browser) {
            lastBrowser = browser;
            return pageSeleniums.get(browser);
        }

        WebPageSeleniumCache getLastPageSelenium() {
            return pageSeleniums.get(lastBrowser);
        }

        void setPageSelenium(String browser, WebPageSeleniumCache pageSelenium) {
            lastBrowser = browser;
            pageSeleniums.put(browser, pageSelenium);
        }

        ServersAddressNode getAddress() {
            return serverAddress;
        }

        void setResetFalg(String browser, boolean reset) {
            resetFlags.put(browser, reset);
        }

        boolean getResetFlag(String browser) {
            return resetFlags.get(browser) != null && resetFlags.get(browser);
        }

        String getLastBrowser() {
            return lastBrowser;
        }
    }

    private List<WebDriverSeleniumPageEx> seleniumsLog = new ArrayList<WebDriverSeleniumPageEx>();

    // singleton
    private static final Object object = new Object();
    private static SeleniumPageFactory instance = null;

    private SeleniumPageFactory() {
    }

    public static SeleniumPageFactory instance() {
        if (instance != null) return instance;
        synchronized (object) {
            if (instance == null)
                instance = new SeleniumPageFactory();
        }
        return instance;
    }

    // member
    private List<ServersAddressNode> servers = new ArrayList<>();
    private ThreadLocal<WebPageThreadCache> threadCache = new ThreadLocal<>();
    private String screenshotFolder;

    // methods
    public void registerSeleniumHub(String seleniumIP, int seleniumPort, String webURL) {
        String url = String.format("http://%s:%d/wd/hub", seleniumIP, seleniumPort);
        ServersAddressNode newOne = new ServersAddressNode(url, webURL);
        servers.add(newOne);
    }

    public int seleniumHubCount() {
        return servers.size();
    }

    // all browser type
    public boolean isSupportedBrowser(String browser) {
        return browser.contains("googlechrome")
                || browser.contains("firefox")
                || browser.contains("iexplore")
                || browser.contains("safari");
    }

    // stop all seleniums (note: only call it once and it's not thread safe. only call it after all stories are finished)
    void stopAllSeleniumSessions() {
        for (WebDriverSeleniumPageEx page : seleniumsLog) {
            try {
                page.stop();
            } catch (Exception ex) {
                log.error("try to close selenium instance, but failed due to error:", ex);
            }
        }
        seleniumsLog.clear();
    }

    // reset previous used browser, if no, do nothing
    public void resetBrowser() {
        WebPageThreadCache cache = threadCache.get();
        if (cache == null || cache.getLastBrowser().isEmpty()) return;
        resetBrowser(cache.getLastBrowser());
    }

    // reset the browser's WebPageSeleniumCache
    public void resetBrowser(String browser) {
        WebPageThreadCache cache = threadCache.get();
        if (cache == null) return;
        WebPageSeleniumCache pageSel = cache.getPageSelenium(browser);
        if (pageSel == null) return;

        pageSel.page.stop();
        seleniumsLog.remove(pageSel.page);  // remove from log list

        // clear thread id to have every instance browser has unique id (instead of thread level because some times browser is hung and make it fail to reuse the download folder)
        reset_thread_unique_id();

        cache.setPageSelenium(browser, null);
        // has reset flag, so that next time, when calling the page, if it was reset once, need to reset it again.
        // if it's marked as true, it means that it has been used. So next time, we it'll be used again, it'll be required to reset the selenium page.
        cache.setResetFalg(browser, true);
    }

    // get last web page of this thread
    public WebDriverSeleniumPageEx getLastPage() {
        WebPageThreadCache cache = threadCache.get();
        if (cache == null) return null;
        return cache.getLastPageSelenium().page;
    }

    // get web page of browser, if empty, create it, if reset flag is marked, reset it first.
    public WebDriverSeleniumPageEx openPage(String browser) {
        WebPageThreadCache cache = threadCache.get();
        String browserLanguage = PropertyConfiger.instance().getEnvData("default.browser.locale", "en-us");
        if (cache == null) {
            ServersAddressNode address = getUnusedServerAddress();
            if (address == null)
                throw new RuntimeException("cannot find any unused selenium server.");

            cache = new WebPageThreadCache(address);
        }

        // check reset flag
        // When this flag is marked as true, it means that this thread has a selenium page before and this selenium page is required to reset.
        if (cache.getResetFlag(browser)) {
            resetBrowser(browser);
            // recover as unreset
            // means that the selenium page in this thread is not reset yet.
            cache.setResetFalg(browser, false);
        }

        WebPageSeleniumCache pageSel = cache.getPageSelenium(browser);
        if (pageSel == null) {
            reset_thread_unique_id();

            StringBuilder downloadFolderLocal = new StringBuilder();
            StringBuilder downloadFolderRemote = new StringBuilder();
            prepareUniqueBrowserDownloadFolder(browser, downloadFolderLocal, downloadFolderRemote);
            WebDriver webDriver = createWebdriver(cache.getAddress(), browser, downloadFolderRemote.toString(), browserLanguage);
            if (webDriver == null)
                throw new RuntimeException("fail to create webDriver instance for webDriver server = " + cache.getAddress().selenium_server + "  web server = " + cache.getAddress().web_server + " browser=" + browser);
            WebDriverSeleniumPageEx page = new WebDriverSeleniumPageEx(webDriver, browser, cache.getAddress().web_server, screenshotFolder, downloadFolderLocal.toString(), downloadFolderRemote.toString());
            page.setUserDataFolder(this.getBrowserUserDataFolderLocal(browser)); // set user data for later clean up usage
            page.setUniqueDownloadUserFolder(!browser.contains("iexplore")); // ie has no unique folde. others have
            seleniumsLog.add(page); //add to log
            pageSel = new WebPageSeleniumCache(page);
            cache.setPageSelenium(browser, pageSel);
        }
        driverMap.put(storyNameLocal.get(), pageSel);
        threadCache.set(cache);
        return pageSel.page;
    }

    private ThreadLocal<String> storyNameLocal = new ThreadLocal<String>() {
        @Override
        public String initialValue() {
            return "unknown";
        }
    };

    private Map<String, WebPageSeleniumCache> driverMap = Collections.synchronizedMap(new LinkedHashMap<>());

    public void setLoadedStory(String story) {
        storyNameLocal.set(story);
    }

    public WebDriverSeleniumPageEx getPage(String story) {
        WebDriverSeleniumPageEx page = driverMap.get(story).page;
        int count = 0;
        while (count <= 360 && page == null) {
            WebDriverSeleniumPageEx.waitFor(5);
            count++;
        }
        return page;
    }

    public boolean isStoryLoaded(String story) {
        return driverMap.keySet().contains(story);
    }

    public void removePage(String story) {
        driverMap.remove(story);
    }


    private WebDriver createRemoteWebDriver(String nodeURL, String browser, String remoteDownloadFolder, String browserLanguage) throws MalformedURLException {
        try {
            return new RemoteWebDriver(new URL(nodeURL), makeDesiredCapabilities(browser, remoteDownloadFolder, browserLanguage));
        } catch (WebDriverException ex) {
            // for firefox driver, sometimes, the port is locked by others, then need to change the port and make a new one again
            if (browser.contains("firefox") && ex.getMessage().contains("Unable to bind to locking port")) {
                log.warn("fail to create firefox driver with locked port errors. will sleep and retry once" + ex);
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    //
                }

                return new RemoteWebDriver(new URL(nodeURL), makeDesiredCapabilities(browser, remoteDownloadFolder, browserLanguage));
            } else {
                throw ex;
            }

        }
    }

    private WebDriver createWebdriver(ServersAddressNode address, String browser, String remoteDownloadFolder, String browserLanguage) {
        log.debug("start to get selenium instance: " + address.selenium_server);
        try {
            WebDriver dr = createRemoteWebDriver(address.selenium_server, browser, remoteDownloadFolder, browserLanguage);
            dr.manage().timeouts().implicitlyWait(PropertyConfiger.instance().getEnvData("webdriver.timeout.search", 20), TimeUnit.SECONDS);
            dr.manage().timeouts().setScriptTimeout(PropertyConfiger.instance().getEnvData("webdriver.timeout.script", 20), TimeUnit.SECONDS);

            // safari doesn't support setting time out yet
            if (!browser.contains("safari"))
                dr.manage().timeouts().pageLoadTimeout(PropertyConfiger.instance().getEnvData("webdriver.timeout.page.load", 60), TimeUnit.SECONDS);

            if (browser.contains("safari"))
                dr.get(address.web_server);

            return dr;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    // multiple threads safe
    private ServersAddressNode getUnusedServerAddress() {
        log.info("try to get one unused selenium node");
        synchronized (this) {
            for (ServersAddressNode node : servers) {
                if (!node.used) {
                    log.info("get one unused selenium node: " + node.selenium_server);
                    node.used = true;
                    return node;
                }
            }
            return null;
        }
    }

    // download folder = ${browser.home}\download\thread-%d\chrome\
    // clear the files inside every time.
    private String prepareEmptyFolder(String folder) {
        File dir = new File(folder);
        if (!dir.exists())
            dir.mkdirs();
        else {
            try {
                FileUtils.cleanDirectory(new File(folder));
            } catch (IOException e) {
                log.warn("failed to clean up folder: " + folder, e);
            }
        }

        return folder;
    }

    private ThreadLocal<Integer> threadIdCache = new ThreadLocal<>();

    private void reset_thread_unique_id() {
        threadIdCache.set(null);
    }

    private int get_thread_unique_id() {
        // prepare thread unique id
        // use random number instead, because in java, it always reuse thread id every time re-launch. and if the browser is not closed, the folder is not clearable)
        int local_thread_id = threadIdCache.get() == null ? ThreadLocalRandom.current().nextInt(10000) : threadIdCache.get();
        if (threadIdCache.get() == null) threadIdCache.set(local_thread_id);
        return local_thread_id;
    }

    // prepare chrome user data folder
    private void prepareBrowserUserData(String browser, String download_folder) {
        // prepare download folder
        String preferenceFolder = getBrowserUserDataFolderLocal(browser);
        prepareEmptyFolder(preferenceFolder); // delete content in the folder firstly
        prepareEmptyFolder(preferenceFolder + File.separator + "Default");  // prepare sub folder
        String prefFile = preferenceFolder + File.separator + "Default" + File.separator + "Preferences";

        // copy profile and replace magic code with real download folder
        String template = PropertyConfiger.instance().getEnvData("chrome.user.data.pref", "");

        String download_magic = PropertyConfiger.instance().getEnvData("chrome.pref.download.dir.magic", "%DOWNLOAD.DIR.MAGIC.CODE%");

        String download_folder_update = download_folder.replace("\\", "\\\\");

        File file = new File(template);
        File dest = new File(prefFile);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(dest));
            String line = reader.readLine();
            while (line != null) {
                line = line.replace(download_magic, download_folder_update);
                writer.write(line + "\n");
                line = reader.readLine();
            }
            writer.flush();
            reader.close();
            writer.close();
        } catch (Exception e) {
            log.warn("failed to prepare chrome user data folder", e);
        }

    }

    static String getBrowserDownloadRoot() {
        return getBrowserHomeOptionLocal() + File.separator + "browser-download";
    }

    static String getBrowserProfileRoot() {
        return getBrowserHomeOptionLocal() + File.separator + "browser-user-data";
    }

    private static String getBrowserHomeOptionLocal() {
        String ret = PropertyConfiger.instance().getEnvData("browser.home.local", "");
        if (ret.isEmpty())
            ret = PropertyConfiger.instance().getEnvData("project.home", "");

        return ret;
    }

    private static String getBrowserHomeOptionRemote() {
        String ret = PropertyConfiger.instance().getEnvData("browser.home.remote", "");
        if (ret.isEmpty())
            ret = getBrowserHomeOptionLocal(); // use local instead

        return ret;
    }

    // download folder
    private void prepareUniqueBrowserDownloadFolder(String browser, StringBuilder localDownloadFolder, StringBuilder remoteDownloadFolder) {
        if (browser.contains("iexplore")) {
            String folder_template = "%s" + File.separator + "browser-download" + File.separator + "%s";
            String download_folder = String.format(folder_template, getBrowserHomeOptionLocal(), browser);
            File dir = new File(download_folder);
            if (!dir.exists())
                dir.mkdirs();

            localDownloadFolder.append(download_folder);

            String remoteFolder = String.format(folder_template, getBrowserHomeOptionRemote(), browser);
            remoteDownloadFolder.append(remoteFolder);
        }

        if (browser.contains("googlechrome") || browser.contains("firefox")) {
            String folder_template = "%s" + File.separator + "browser-download" + File.separator + "%s-%d";
            String download_folder = String.format(folder_template, getBrowserHomeOptionLocal(), browser, get_thread_unique_id());
            prepareEmptyFolder(download_folder); // empty download folder

            localDownloadFolder.append(download_folder);

            // for remote node, if it's non-windows, use / as seperator instead
            String remoteFolder = String.format(folder_template, getBrowserHomeOptionRemote(), browser, get_thread_unique_id());

            if (browser.contains("mac") || browser.contains("ipad") || browser.contains("linux"))
                remoteFolder = remoteFolder.replace("\\", "//");

            remoteDownloadFolder.append(remoteFolder);
        }


    }

    // user folder folder
    private String getBrowserUserDataFolderLocal(String browser) {
        return String.format("%s" + File.separator + "browser-user-data" + File.separator + "%s-%d", getBrowserHomeOptionLocal(), browser, get_thread_unique_id());
    }

    // remotely
    private String getBrowserUserDataFolderRemote(String browser) {
        String remoteFolder = String.format("%s" + File.separator + "browser-user-data" + File.separator + "%s-%d", getBrowserHomeOptionRemote(), browser, get_thread_unique_id());
        if (browser.contains("mac") || browser.contains("ipad") || browser.contains("linux"))
            remoteFolder = remoteFolder.replace("\\", "//");

        return remoteFolder;
    }

//    private int getRandonPortForFirefoxDriver() {
//    	return 7600 + ThreadLocalRandom.current().nextInt(55000);
//    }

    private DesiredCapabilities makeDesiredCapabilities(String browser, String remoteDownloadFolder, String browserLanguage) {
        DesiredCapabilities desiredCapabilities = null;
        if (browser.contains("firefox")) {
            desiredCapabilities = DesiredCapabilities.firefox();
            FirefoxProfile fp = new FirefoxProfile();
            fp.setPreference("browser.download.folderList", 2);
            fp.setPreference("browser.download.dir", remoteDownloadFolder);
            fp.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/jpg,image/png,image/gif,text/plain,application/txt,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf,application/vnd.ms-excel.sheet.macroenabled.12");
            fp.setPreference("browser.helperApps.alwaysAsk.force", false);
            fp.setPreference("browser.download.manager.showWhenStarting", false);

            fp.setPreference("webdriver_accept_untrusted_certs", true);
            fp.setPreference("webdriver_assume_untrusted_issuer", true);
            if (!StringUtils.isEmpty(browserLanguage))
                fp.setPreference("intl.accept_languages", browserLanguage);

            //fp.setPreference(FirefoxProfile.PORT_PREFERENCE, getRandonPortForFirefoxDriver());

            desiredCapabilities.setCapability(FirefoxDriver.PROFILE, fp);
        } else if (browser.contains("googlechrome")) {
            desiredCapabilities = DesiredCapabilities.chrome();
            desiredCapabilities.setCapability("ignore-certificate", true);

            prepareBrowserUserData(browser, remoteDownloadFolder);
            String userDataFolder = getBrowserUserDataFolderRemote(browser);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("user-data-dir=" + userDataFolder);
            String locale = PropertyConfiger.instance().getEnvData("default.browser.locale", "");
            if (!locale.isEmpty())
                options.addArguments("--lang=" + locale);

            //disable browser pop up disable extensions dialog
            options.addArguments("chrome.switches", "--disable-extensions");
            //System.setProperty("webdriver.chrome.driver",(System.getProperty("user.dir") + "//src//test//resources//chromedriver_new.exe"));

            //options.addArguments("chrome.switches","--enable-automatic-password-saving");

            desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
            log.info("After setCapability");
        } else if (browser.contains("iexplore")) {
            desiredCapabilities = DesiredCapabilities.internetExplorer();
            desiredCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            desiredCapabilities.setCapability("ignoreProtectedModeSettings", true);
            desiredCapabilities.setCapability("ignoreZoomSetting", true);
            desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

        } else if (browser.toLowerCase().contains("safari")) {
            desiredCapabilities = DesiredCapabilities.safari();
            FirefoxProfile fp = new FirefoxProfile();
            fp.setPreference("browser.download.folderList", 2);
            fp.setPreference("browser.download.dir", remoteDownloadFolder);
            fp.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/jpg,image/png,image/gif,text/plain,application/txt,text/csv,text/xml,application/xml,application/vnd.ms-excel,application/x-excel,application/x-msexcel,application/excel,application/pdf");
            fp.setPreference("browser.helperApps.alwaysAsk.force", false);
            fp.setPreference("browser.download.manager.showWhenStarting", false);
            desiredCapabilities.setCapability(FirefoxDriver.PROFILE, fp);
        }

        if (desiredCapabilities == null)
            throw new WebDriverException("not implemented capability for browser [" + browser + "]");

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);


        desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
        desiredCapabilities.setJavascriptEnabled(true);
        desiredCapabilities.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
        return desiredCapabilities;
    }

    public void setScreeshotLocation(String screenshotFolder) {
        this.screenshotFolder = screenshotFolder;
    }

    public String getScreeshotLocation() {
        return this.screenshotFolder;
    }
}