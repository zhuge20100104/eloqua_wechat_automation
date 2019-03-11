package com.oracle.auto.web.jbehave_ext;

import org.jbehave.core.model.*;
import org.jbehave.core.reporters.StoryReporter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class ConsoleTracker implements StoryReporter {
    String currentStory;
    String currentScenario;
    final String timeFormat = "HH:mm:ss dd-MM-yyyy";

    @Override
    public void storyNotAllowed(Story story, String s) {

    }

    @Override
    public void storyCancelled(Story story, StoryDuration storyDuration) {

    }

    @Override
    public void beforeStory(Story story, boolean b) {
        currentStory = story.getName();
    }

    @Override
    public void afterStory(boolean b) {
    }

    @Override
    public void narrative(Narrative narrative) {

    }

    @Override
    public void lifecyle(Lifecycle lifecycle) {

    }

    @Override
    public void scenarioNotAllowed(Scenario scenario, String s) {

    }

    @Override
    public void beforeScenario(String s) {
        currentScenario = s;
        if (currentScenario.isEmpty())
            currentScenario = "This is a Scenario here with no Steps under it...";
        System.out.println("Start Time: " + getCurrentTime() + "" +
                "\nStory: " + currentStory + "" +
                "\nScenario: " + currentScenario);
    }

    @Override
    public void scenarioMeta(Meta meta) {

    }

    @Override
    public void afterScenario() {
        System.out.println("End Time: " + getCurrentTime() + "" +
                "\nStory: " + currentStory + "" +
                "\nScenario: " + currentScenario);
    }

    @Override
    public void givenStories(GivenStories givenStories) {

    }

    @Override
    public void givenStories(List<String> list) {

    }

    @Override
    public void beforeExamples(List<String> list, ExamplesTable examplesTable) {

    }

    @Override
    public void example(Map<String, String> map) {

    }

    @Override
    public void afterExamples() {

    }

    @Override
    public void beforeStep(String s) {
    }

    @Override
    public void successful(String s) {
    }

    @Override
    public void ignorable(String s) {

    }

    @Override
    public void pending(String s) {
    }

    @Override
    public void notPerformed(String s) {
    }

    @Override
    public void failed(String s, Throwable throwable) {
    }

    @Override
    public void failedOutcomes(String s, OutcomesTable outcomesTable) {

    }

    @Override
    public void restarted(String s, Throwable throwable) {

    }

    @Override
    public void dryRun() {

    }

    @Override
    public void pendingMethods(List<String> list) {

    }

    private String getCurrentTime() {
        return new SimpleDateFormat(timeFormat).format(Calendar.getInstance().getTime());
    }
}
