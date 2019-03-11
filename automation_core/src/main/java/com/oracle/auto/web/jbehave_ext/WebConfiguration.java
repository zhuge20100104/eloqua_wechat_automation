package com.oracle.auto.web.jbehave_ext;

import com.oracle.auto.mobile.driver.MobileDriverFactory;
import com.oracle.auto.web.jbehave_ext.test.TraderConverter;
import com.oracle.auto.web.multithread.SeleniumPageFactory;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.StoryLocation;
import org.jbehave.core.parsers.StoryParser;
import org.jbehave.core.parsers.TransformingStoryParser;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.ParameterControls;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.SilentStepMonitor;

import java.text.SimpleDateFormat;
import java.util.Properties;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.TXT;

public class WebConfiguration extends Configuration {
    private static Object object = new Object();
    private static WebConfiguration instance = null;

    public static WebConfiguration instance() {
        if (instance != null) return instance;
        synchronized (object) {
            if (instance == null)
                instance = new WebConfiguration();
        }
        return instance;
    }

    private final CrossReference xref = new CrossReference();

    private WebConfiguration() {
        StoryParser newStoryParser = new TransformingStoryParser(storyParser(),
                GlobalVariableTransformer.instance(),
                SplitExampleTransformer.instance(),// split is disabled.
                JsonTransformer.instance());

        Properties viewResources = new Properties();
        viewResources.put("decorateNonHtml", "true");
        viewResources.put("reports", PropertyConfiger.instance().getEnvData("report.summary.template", "ftl/jbehave-reports-with-totals.ftl"));
        this.useViewGenerator(new CsvFreemarkerViewGenerator(this.viewGenerator()))
                .useStepMonitor(new SilentStepMonitor())
                .useStepMonitor(xref.getStepMonitor())
                .useStepCollector(new StepCollectorRetryDecorator(stepCollector()))
                .useStoryParser(newStoryParser)
                .useParameterConverters(new ParameterConverters().addConverters(new JsonConverter(),
                        new MapConverter(),
                        new TraderConverter(),
                        new JsonListConverter(),
                        new ParameterConverters.EnumConverter()))
                .useParameterControls(new ParameterControls().useDelimiterNamedParameters(true))
                .useStoryReporterBuilder(new StoryReporterBuilder()
                        .withDefaultFormats()
                        .withFormats(CONSOLE, TXT, HtmlWebOutput.HTML_WEB)
                        .withFailureTrace(true)
                        .withFailureTraceCompression(false)
                        .withViewResources(viewResources)
                        .withCrossReference(xref)
                        .withReporters(new ConsoleTracker())
                );
    }

    public void splitExample(boolean enable) {
        SplitExampleTransformer.instance().setDefaultEnabled(enable);
    }

    public WebConfiguration setEmbeddableClass(Class<? extends Embeddable> embeddableClass) {
        this.useStoryLoader(new LoadFromClasspathUtf8(embeddableClass)).storyReporterBuilder().withCodeLocation(codeLocationFromClass(embeddableClass));

        // set screenshot location
        StoryLocation storyLocation = new StoryLocation(codeLocationFromClass(embeddableClass), "");
        FilePrintStreamFactory.ResolveToSimpleName resolver = new FilePrintStreamFactory.ResolveToSimpleName();
        String screenShotFolder = resolver.resolveDirectory(storyLocation, "jbehave");

        SeleniumPageFactory.instance().setScreeshotLocation(screenShotFolder);
        MobileDriverFactory.instance().setScreeshotLocation(screenShotFolder);
        return this;
    }
}