package com.oracle.auto.web.jbehave_ext;

import org.jbehave.core.model.StoryMaps;
import org.jbehave.core.reporters.ReportsCount;
import org.jbehave.core.reporters.ViewGenerator;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import static org.apache.commons.lang.StringUtils.*;
import static org.apache.commons.lang.WordUtils.capitalize;

public class CsvFreemarkerViewGenerator implements ViewGenerator {
    public static final String REPORTS_CSV_NAME = "reports.csv";
    public static final String STATS_FILE_EXT = ".stats";
    public static final String TOTALS_KEY = "(Totals)";
    public static final String CSV_SEPARATOR = ",";

    private final ViewGenerator delegate;

    public CsvFreemarkerViewGenerator(ViewGenerator delegate) {
        this.delegate = delegate;
    }

    public enum Stat {
        /* pending, notAllowed, examples, duration, -- these properties are not included in the report */
        scenarios, scenariosSuccessful, scenariosPending, scenariosFailed, scenariosNotAllowed, givenStories, givenStoryScenarios, givenStoryScenariosSuccessful, givenStoryScenariosPending, givenStoryScenariosFailed, givenStoryScenariosNotAllowed, steps, stepsSuccessful, stepsPending, stepsFailed, stepsNotPerformed, stepsIgnorable;
    }

    @Override
    public void generateMapsView(File outputDirectory, StoryMaps storyMaps, Properties viewResources) {
        delegate.generateMapsView(outputDirectory, storyMaps, viewResources);
    }

    @Override
    public void generateReportsView(File outputDirectory, List<String> formats, Properties viewResources) {
        delegate.generateReportsView(outputDirectory, formats, viewResources);

        generateCSVReportsView(outputDirectory, formats, viewResources);
    }

    @Override
    public ReportsCount getReportsCount() {
        return delegate.getReportsCount();
    }

    protected void generateCSVReportsView(File outputDirectory, List<String> formats, Properties viewResources) {
        Map<String, Map<Stat, Integer>> reportMap = readStatProperties(outputDirectory);
        writeCSVReports(outputDirectory, reportMap);
    }

    protected Map<String, Map<Stat, Integer>> readStatProperties(File outputDirectory) {
        Map<String, Map<Stat, Integer>> reportMap = new HashMap<>();

        if (outputDirectory == null || !outputDirectory.exists()) {
            return reportMap;
        }

        EnumMap<Stat, Integer> totalStatMap = newStatMap();

        String[] names = listStatFiles(outputDirectory);
        for (String name : names) {
            EnumMap<Stat, Integer> statMap = newStatMap();

            Properties properties = loadStatFile(outputDirectory, name);
            for (Entry<Object, Object> entry : properties.entrySet()) {
                try {
                    Stat stat = Stat.valueOf(String.valueOf(entry.getKey()));
                    if (stat != null) {
                        Integer number = Integer.valueOf(String.valueOf(entry.getValue()));
                        statMap.put(stat, number);
                        totalStatMap.put(stat, totalStatMap.get(stat) + number);
                    }
                } catch (IllegalArgumentException e) {
                    // ignore: does not contain this constant in the report
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            reportMap.put(name, statMap);
        }

        reportMap.put(TOTALS_KEY, totalStatMap);

        return reportMap;
    }

    protected void writeCSVReports(File outputDirectory, Map<String, Map<Stat, Integer>> reportMap) {
        File csvFile = new File(outputDirectory, REPORTS_CSV_NAME);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            List<Object> list = new ArrayList<>();
            list.add("Stories");
            for (Stat stat : Stat.values()) {
                list.add(capitalize(String.valueOf(stat)));
            }
            writeLine(bw, list);

            List<String> keys = sortedKeys(reportMap);
            for (String key : keys) {
                list = new ArrayList<>();
                list.add(convertToStoryName(key));
                Map<Stat, Integer> statMap = reportMap.get(key);
                for (Stat stat : Stat.values()) {
                    list.add(statMap.get(stat));
                }
                writeLine(bw, list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected List<String> sortedKeys(Map<String, Map<Stat, Integer>> reportMap) {
        List<String> keys = new ArrayList<>(reportMap.keySet());
        Collections.sort(keys, new Comparator<String>() {
            @Override
            public int compare(String name1, String name2) {
                return convertToStoryName(name1).compareTo(convertToStoryName(name2));
            }
        });
        return keys;
    }

    protected static void writeLine(BufferedWriter bw, List<Object> words) throws IOException {
        for (int i = 0; i < words.size(); i++) {
            bw.append(String.valueOf(words.get(i)));
            if (i < words.size() - 1) {
                bw.append(CSV_SEPARATOR);
            }
        }
        bw.newLine();
    }

    protected String[] listStatFiles(File outputDirectory) {
        return outputDirectory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(STATS_FILE_EXT) && !name.equals("BeforeStories.stats") && !name.equals("AfterStories.stats");
            }
        });
    }

    protected Properties loadStatFile(File outputDirectory, String name) {
        Properties p = new Properties();
        try (InputStream inputStream = new FileInputStream(new File(outputDirectory, name))) {
            p.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    protected EnumMap<Stat, Integer> newStatMap() {
        EnumMap<Stat, Integer> statMap = new EnumMap<>(Stat.class);
        for (Stat stat : Stat.values()) {
            statMap.put(stat, 0);
        }
        return statMap;
    }

    protected String convertToStoryName(String name) {
        if (name.equals(TOTALS_KEY)) {
            return name;
        }
        if (name.endsWith(STATS_FILE_EXT)) {
            name = substringBefore(name, STATS_FILE_EXT);
        }
        if (name.contains(".")) {
            name = substringAfter(name, ".");
        }
        name = replaceChars(name, '_', ' ');
        name = capitalize(name);
        return name;
    }
}