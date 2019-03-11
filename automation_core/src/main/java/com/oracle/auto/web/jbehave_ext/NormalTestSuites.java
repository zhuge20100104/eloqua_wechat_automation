package com.oracle.auto.web.jbehave_ext;

import com.oracle.auto.commons.exceptions.snapshots.TakeSanpshotHandler;
import com.oracle.auto.mobile.driver.MobileDriverFactory;
import com.oracle.auto.web.exceptions.WebExceptionBase;
import com.oracle.auto.web.multithread.AllStoriesSeleniumWebDriverSteps;
import com.oracle.auto.web.multithread.SeleniumPageFactory;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.ResourceLoaderCross;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.jbehave.core.ConfigurableEmbedder;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.Embedder.RunningStoriesFailed;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.Test;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

public class NormalTestSuites extends ConfigurableEmbedder {
    private static Logger log = Logger.getLogger(NormalTestSuites.class);

    protected void InitConfig() {
        // register resource loader cross
        ResourceLoaderCross.instance().registerModuleChains(getClass(), NormalTestSuites.class);

        // load setting at the first time
        PropertyConfiger.instance().setClassLoader(getClass());
        PropertyConfiger.instance().loadProperties(settingFilePaths());

        // load data
        PropertyConfiger.instance().loadProperties(dataFilePaths());
        addCustomValues();

        // load snapshot hanlder
        WebExceptionBase.setTakenSnapshotHandler(this.takenSnapshotHanlder());

        // server URL
        String defaultURL = getConsoleUrl();
        Assert.assertFalse("**error** get web console is empty", defaultURL.isEmpty());

        // selenium nodes (seperated with ';')
        String defaultSeleniumNodes = PropertyConfiger.instance().getEnvData("selenium.node.list", "localhost;");  //localhost;10.32.168.245;

        // selenium instance count on per node
        int seleniumCountOnEachNode = PropertyConfiger.instance().getEnvData("selenium.instance.count.per.node", 1);
        registerSeleniumNodesShareLoad(defaultSeleniumNodes, 4001, seleniumCountOnEachNode, defaultURL);

        // dry run or not, default - false
        WebConfiguration.instance().doDryRun(PropertyConfiger.instance().getEnvData("drill.run", false));

        String devices = PropertyConfiger.instance().getEnvData("devices", "");
        registerAppiumServerNodes("127.0.0.1",devices,4730);
    }

    protected String getConsoleUrl() {
        return PropertyConfiger.instance().getEnvData("web.console.url", "");
    }

    private Exception lastExcetion = null;

    @Test
    public void run() throws Throwable {
        Embedder embedder = configuredEmbedder();

        try {
            if (!PropertyConfiger.instance().getEnvData("ignore.story.list.and.use.failed", false)) {
                embedder.runStoriesAsPaths(storyPaths());
            } else {
                runfailed();
            }
        } catch (RunningStoriesFailed ex) {
            lastExcetion = ex;
            log.warn("there's failed stories after once execution: " + ex.getMessage());
        }

        // re-run
        int max_retry_count = PropertyConfiger.instance().getEnvData("overall.retry.count.when.failure", 0);
        int retried = 0;
        while (retried++ < max_retry_count) {
            log.info("trying to run all failed stories. retry count = " + retried + ", max retry count = " + max_retry_count);
            if (runfailed()) {
                lastExcetion = null; //set it as empty because it's pass all
                break;
            }
        }

        if (lastExcetion != null)
            throw lastExcetion; // throw the exception to make the running failed in JUnit/TestNG
    }

    // return false if there's still failure
    public boolean runfailed() throws Throwable {
        try {
            if (!PropertyConfiger.instance().getEnvData("mobile.test.suite", false)) {
                InitConfig();
            } else {
                MobileDriverFactory.instance().resetServers();
                MobileDriverFactory.instance().stopExistingAppiumSessions();
            }
            configuredEmbedder().runStoriesAsPaths(getFailedStoryPaths());
            return true;
        } catch (RunningStoriesFailed ex) {
            log.warn("there's failed stories after once overal re-run execution: " + ex.getMessage());
            lastExcetion = ex;
            return false;
        }
    }

    // return failed count
    public List<String> getFailedStoryPaths() {
        // generate failed story list from output folder
        List<String> sbFailedStories = new ArrayList<String>();
        File outputDirectory = configuration().storyReporterBuilder().outputDirectory();

        if (!outputDirectory.exists()) return sbFailedStories;

        File[] files = outputDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                String name = file.getName();
                return name.endsWith(".stats");
            }
        });

        for (File file : files) {
            Properties p = new Properties();
            try (InputStream inputStream = new FileInputStream(file)) {
                p.load(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (p.get("scenariosFailed") != null && Integer.parseInt(p.get("scenariosFailed").toString()) > 0) {
                String storyName = file.getName().substring(0, file.getName().length() - ".stats".length()).replace(".", "/") + ".story";
                sbFailedStories.add(storyName);
            }
        }

        log.info("get failed story list (count = " + sbFailedStories.size() + "), files: " + sbFailedStories);

        /*
          If this is a web test suite, do nothing.
          If this is a mobile test suite, remove the web-failed story list.
          */
        if (PropertyConfiger.instance().getEnvData("mobile.test.suite", false)) {
            List<String> sbFailedStories_mobile = new ArrayList<>();
            for (String failedStory : sbFailedStories) {
                if (failedStory.endsWith("_WM.story"))
                    sbFailedStories_mobile.add(failedStory);
            }
            return sbFailedStories_mobile;
        }

        return sbFailedStories;
    }

    private List<String> handlePathList(String filePath) {
        String[] list = filePath.split(",");
        ArrayList<String> results = new ArrayList<String>();
        for (String file : list) {
            file = file.trim();
            if (file.isEmpty()) continue;
            file = "**/" + file;
            results.add(file);
        }

        return results;
    }

    protected TakeSanpshotHandler takenSnapshotHanlder() {
        return null;
    }

    protected String storyList() {
        return PropertyConfiger.instance().getEnvData("story.list", "**/*.story");
    }

    protected String excludeStoryList() {
        return PropertyConfiger.instance().getEnvData("story.list.exclude", "");
    }

    protected String dataFileList() {
        return PropertyConfiger.instance().getEnvData("test.data.file.list", "**/*.data.properties");
    }

    protected String settingFileList() {
        // this setting can only take effect by being configured via system environment.
        return PropertyConfiger.instance().getEnvData("test.setting.file", "**/*.setting.properties");
    }

    protected int timeoutInSeconds() {
        int timeout = PropertyConfiger.instance().getEnvData("timeout.in.second", 3600);

        return timeout;
    }

    protected List<String> metaFilters() {
        // use poc test suites by default
        String acceptanceSuites = "groovy: (type ==~ '.*poc.*') ";

        String filter = PropertyConfiger.instance().getEnvData("meta.filter", acceptanceSuites);
        String all_case = "all";  // magic string
        if (filter.equals(all_case)) filter = "";  //change "ALL_CASE" to "" to take effect
        return asList(filter);
    }

    protected int threadCount() {
        // use selenium nodes as thread count by default.
        int thread_count = SeleniumPageFactory.instance().seleniumHubCount();

        Assert.assertTrue(String.format("**error** thread count [%d] is larger than selenium nodes [%s].", thread_count, SeleniumPageFactory.instance()),
                thread_count >= 1 && thread_count <= SeleniumPageFactory.instance().seleniumHubCount());

        // set thread count into system property
        System.setProperty("bdd.thread.count", String.valueOf(thread_count));

        return thread_count;
    }

    protected String stepJavaList() {
        return "**/steps/**/*.class";
    }

    protected void addAddtionalStep(List<Object> objList) {
        if (PropertyConfiger.instance().getEnvData("debug.keep.browser.open.when.finish", false)) {
            log.warn("keep browser open for debug.");
            return;
        }

        objList.add(new AllStoriesSeleniumWebDriverSteps());
    }

    public NormalTestSuites() {
        InitConfig();

        Embedder embedder = configuredEmbedder();
        embedder.useMetaFilters(metaFilters());  // use meta filter
        embedder.embedderControls()
                .useThreads(threadCount())
                .doVerboseFailures(true)
                .useStoryTimeoutInSecs(timeoutInSeconds())  // 2 hours
                .doIgnoreFailureInStories(true)
                .doIgnoreFailureInView(false);
    }

    @Override
    public Configuration configuration() {
        return WebConfiguration.instance().setEmbeddableClass(this.getClass());
    }

    // get java steps files
    @SuppressWarnings("unchecked")
    private List<String> javaStepPaths() {
        Reflections reflections = new Reflections("com.oracle");
        List<Class<? extends ConfigurableEmbedder>> classes = new ArrayList<Class<? extends ConfigurableEmbedder>>();
        classes.addAll(reflections.getSubTypesOf(ConfigurableEmbedder.class));
        List<String> ret = new ArrayList<String>();
        for (Class<? extends ConfigurableEmbedder> cls : classes) {
            List<String> lst = new StoryFinder().findClassNames(codeLocationFromClass(cls).getFile(), handlePathList(stepJavaList()), null);
            ret.addAll(lst);
        }

        return ret;
    }

    // get property files
    protected List<String> dataFilePaths() {
        return new StoryFinder().findClassNames(codeLocationFromClass(this.getClass()).getFile(), handlePathList(dataFileList()), null);
    }

    // get property files
    protected List<String> settingFilePaths() {
        return new StoryFinder().findClassNames(codeLocationFromClass(this.getClass()).getFile(), handlePathList(settingFileList()), null);
    }

    private Object createStepObj(String className) {
        try {
            Class<?> classObj = Class.forName(className);
            Constructor<?> constructor = classObj.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception ex) {
            log.info("[Step Factory] fail to init step object to " + className);
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        List<Object> objList = new ArrayList<>();
        List<String> stepFiles = javaStepPaths();
        for (String file : stepFiles) {
            file = file.replace('/', '.').replace(".class", "");
            objList.add(createStepObj(file));
        }

        addAddtionalStep(objList);

        for (Object obj : objList)
            log.debug("java steps added: " + obj.getClass().getName());

        return new InstanceStepsFactory(configuration(), objList);
    }

    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(), handlePathList(storyList()), handlePathList(excludeStoryList()));
    }

    protected List<String> storyPaths(String includeList, String excludeList) {
        return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(), handlePathList(includeList), handlePathList(excludeList));
    }

    protected void addCustomValues() {
        // add %RAND_NUM% support
        RandomUnitTransformer randTranformer = new RandomUnitTransformer();
        ThreadStoryRandUnitTransformer thread_story_rand_transformer = new ThreadStoryRandUnitTransformer();
        StoryRandUnitTransformer fixed_story_rand_transformer = new StoryRandUnitTransformer();
        randTranformer.setRandonKey(PropertyConfiger.instance().getEnvData("random.number.magic", "RAND_NUM"));
        thread_story_rand_transformer.setRandonKey(PropertyConfiger.instance().getEnvData("random.number.magic.thread.story.level", "TS_RAND_NUM"));
        fixed_story_rand_transformer.setRandonKey(PropertyConfiger.instance().getEnvData("random.number.magic.fixed.story.level", "FS_RAND_NUM"));
        randTranformer.setUpLimit(10000);
        GlobalVariableTransformer.instance().addUnitTranformer(randTranformer.getRandonKey(), randTranformer);
        GlobalVariableTransformer.instance().addUnitTranformer(thread_story_rand_transformer.getRandonKey(), thread_story_rand_transformer);
        GlobalVariableTransformer.instance().addUnitTranformer(fixed_story_rand_transformer.getRandonKey(), fixed_story_rand_transformer);
    }


    protected void registerSeleniumNodes(String list, int portStart, int portRange, String beaconConsole) {
        String[] nodes = list.split(";");
        for (String node : nodes) {
            if (node.trim().isEmpty()) continue;
            log.info("get configured selenium node: " + node);
            for (int port = portStart; port <= portStart + portRange - 1; ++port)
                SeleniumPageFactory.instance().registerSeleniumHub(node, port, beaconConsole);
        }
    }

    protected void registerSeleniumNodesShareLoad(String list, int portStart, int portRange, String beaconConsole) {
        String[] nodes = list.split(";");
        for (int port = portStart; port <= portStart + portRange - 1; ++port) {
            for (String node : nodes) {
                if (node.trim().isEmpty()) continue;
                SeleniumPageFactory.instance().registerSeleniumHub(node, port, beaconConsole);
                log.info("get configured selenium node: " + node);
            }
        }
    }

    protected void registerAppiumServerNodes(String host, String deviceList, int portStart) {
        String[] devices = deviceList.split(",");
        for (String device : devices) {
            MobileDriverFactory.instance().registerAppiumHub(host, portStart, device);
            portStart += 10;
        }
    }
}